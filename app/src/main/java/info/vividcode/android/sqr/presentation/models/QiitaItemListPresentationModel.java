package info.vividcode.android.sqr.presentation.models;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import info.vividcode.android.sqr.application.QiitaItemListLoader;
import info.vividcode.android.sqr.dto.QiitaItem;
import rx.Observable;
import rx.Scheduler;
import rx.SingleSubscriber;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Qiita の投稿一覧を管理するプレゼンテーションモデル。
 */
public class QiitaItemListPresentationModel {

    private final QiitaItemListLoader mQiitaItemListLoader;

    private final ObservableList<QiitaItem> mQiitaItems = new ObservableArrayList<>();

    private final Scheduler mMainScheduler;
    private final Scheduler mIoScheduler;

    /**
     *  現在のページ番号。
     *  最初のページが 1。 0 はまだ 1 ページも読み込んでいない状態。
     */
    private int mCurrentPage = 0;

    /**
     * 読み込み状態の変化を送出するための subject。
     */
    private final BehaviorSubject<LoadingState> mQiitaItemsLoadingStateBehaviorSubject =
            BehaviorSubject.create(LoadingState.NOT_STARTED_YET);

    /**
     * 次ページの有無の変化を送出するための subject。
     */
    private final BehaviorSubject<NextPageExistence> mQiitaItemsHasNextPageBehaviorSubject =
            BehaviorSubject.create(NextPageExistence.UNKNOWN);

    @Inject
    public QiitaItemListPresentationModel(
            QiitaItemListLoader qiitaItemListLoader,
            @Named("main") Scheduler mainScheduler,
            @Named("io") Scheduler ioScheduler) {
        mQiitaItemListLoader = qiitaItemListLoader;
        mMainScheduler = mainScheduler;
        mIoScheduler = ioScheduler;
    }

    public ObservableList<QiitaItem> getObservableQiitaItemList() {
        return mQiitaItems;
    }

    public Observable<LoadingState> getQiitaItemsLoadingStateChangeObservable() {
        return mQiitaItemsLoadingStateBehaviorSubject;
    }

    public Observable<NextPageExistence> getQiitaItemsNextPageExistenceChangeObservable() {
        return mQiitaItemsHasNextPageBehaviorSubject;
    }

    public void requestToLoadNextPage() {
        // TODO : Scheduler に乗せて Subscription に値を送出するのをいい感じに書く。
        // 今はやり方がわからないのでとりあえずこうしてる。
        // メモ : スケジューラに乗せて非同期に呼ばないと RecyclerView.Adapter の onViewAttachedToWindow
        // メソッドからこのメソッドが呼ばれたときに RecyclerView の方でエラーになる。
        // それは呼び出し側で何とかすべき問題な気もするが、subject への値の送出は全て main scheduler に
        // 乗せるという方針にして、ここでも main scheduler に乗せる。
        Observable.just(0).observeOn(mMainScheduler).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                mQiitaItemsLoadingStateBehaviorSubject.onNext(LoadingState.LOADING);
            }
        });
        final int loadingPage = mCurrentPage + 1;
        mQiitaItemListLoader.getQiitaItemList(loadingPage)
                .subscribeOn(mIoScheduler)
                .observeOn(mMainScheduler)
                .subscribe(new SingleSubscriber<List<QiitaItem>>() {
                    @Override
                    public void onSuccess(List<QiitaItem> value) {
                        mQiitaItemsLoadingStateBehaviorSubject.onNext(LoadingState.SUCCESS);
                        mCurrentPage = loadingPage;
                        // TODO : レスポンスヘッダを見て次ページの有無を確認する。
                        // とりあえずはレスポンスの配列が空の場合と 5 ページ目まで読み込んだ場合は次ページはないことにする。
                        if (value.size() == 0 || mCurrentPage >= 5) {
                            mQiitaItemsHasNextPageBehaviorSubject.onNext(NextPageExistence.NOT_EXIST);
                        } else {
                            mQiitaItemsHasNextPageBehaviorSubject.onNext(NextPageExistence.UNKNOWN);
                        }
                        mQiitaItems.addAll(value);
                    }
                    @Override
                    public void onError(Throwable error) {
                        // TODO : エラーは Crashlytics などで記録するようにする？

                        // TODO : エラーの内容に応じてメッセージを変える。
                        // (API の利用制限に引っかかった場合にはその旨をユーザーに伝えるなど。)
                        mQiitaItemsLoadingStateBehaviorSubject.onNext(LoadingState.ERROR);
                    }
                });
    }

    public interface InstanceStateSaver {
        void save(List<QiitaItem> items, int currentPage, NextPageExistence nextPageExistence);
    }

    public void saveInstanceState(InstanceStateSaver saver) {
        saver.save(mQiitaItems, mCurrentPage, mQiitaItemsHasNextPageBehaviorSubject.getValue());
    }

    public void restoreInstanceState(List<QiitaItem> items, int currentPage, NextPageExistence nextPageExistence) {
        mQiitaItems.clear();
        mQiitaItems.addAll(items);
        mCurrentPage = currentPage;
        mQiitaItemsHasNextPageBehaviorSubject.onNext(nextPageExistence);
    }

}
