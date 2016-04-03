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
import rx.subjects.BehaviorSubject;

/**
 * Qiita の投稿一覧を管理するプレゼンテーションモデル。
 */
public class QiitaItemListPresentationModel {

    private final QiitaItemListLoader mQiitaItemListLoader;

    private final ObservableList<QiitaItem> mQiitaItems = new ObservableArrayList<>();

    private final Scheduler mMainScheduler;
    private final Scheduler mIoScheduler;

    private final BehaviorSubject<LoadingState> mQiitaItemsLoadingStateBehaviorSubject =
            BehaviorSubject.create(LoadingState.NOT_STARTED_YET);

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

    public void requestToLoadItems() {
        mQiitaItemsLoadingStateBehaviorSubject.onNext(LoadingState.LOADING);
        mQiitaItemListLoader.getQiitaItemList()
                .subscribeOn(mIoScheduler)
                .observeOn(mMainScheduler)
                .subscribe(new SingleSubscriber<List<QiitaItem>>() {
                    @Override
                    public void onSuccess(List<QiitaItem> value) {
                        mQiitaItems.addAll(value);
                        mQiitaItemsLoadingStateBehaviorSubject.onNext(LoadingState.SUCCESS);
                    }
                    @Override
                    public void onError(Throwable error) {
                        // TODO : エラーは Crashlytics などで記録するようにする？

                        mQiitaItemsLoadingStateBehaviorSubject.onNext(LoadingState.ERROR);
                    }
                });
    }

}
