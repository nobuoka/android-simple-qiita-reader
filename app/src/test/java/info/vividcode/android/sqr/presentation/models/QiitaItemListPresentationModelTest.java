package info.vividcode.android.sqr.presentation.models;

import android.databinding.ObservableList;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import info.vividcode.android.sqr.application.QiitaItemListLoader;
import info.vividcode.android.sqr.dto.QiitaItem;
import info.vividcode.android.sqr.dto.Tag;
import info.vividcode.android.sqr.dto.User;
import rx.Scheduler;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class QiitaItemListPresentationModelTest {

    /**
     * テスト用に {@link TestQiitaItemListLoader} が返すオブジェクト。
     * テスト用のデータの生成方法はいい感じにしたいけどとりあえず。
     */
    private static final QiitaItem mResponseQiitaItem = new QiitaItem(
            "test-id",
            "test-title",
            "body",
            "renderedBody",
            false,
            "2016-04-01T00:00:00+09:00",
            "2016-04-01T00:00:00+09:00",
            false,
            "http://example.com/test-1",
            Collections.<Tag>emptyList(),
            new User("test-user", "test-taro", "http://exampl.com/profile-image", "", "")
    );

    /**
     * テスト用の {@link QiitaItemListLoader} の実装。
     */
    public static class TestQiitaItemListLoader implements QiitaItemListLoader {
        /**
         * レスポンスを返す処理の待ち合わせのためと、レスポンスの内容を決めるための deque。
         * 0 が入れられると成功レスポンスを返し、それ以外の値が入れられるとエラーを返す。
         * 空の場合はレスポンスを返すのを待つ。
         */
        public final BlockingDeque<Integer> responseWaiter = new LinkedBlockingDeque<>();
        @Override
        public Single<List<QiitaItem>> getQiitaItemList() {
            return Single.create(new Single.OnSubscribe<List<QiitaItem>>() {
                @Override
                public void call(SingleSubscriber<? super List<QiitaItem>> singleSubscriber) {
                    try {
                        int responseType = responseWaiter.poll();
                        if (responseType != 0) throw new RuntimeException("テスト用のエラー");
                        singleSubscriber.onSuccess(singletonList(mResponseQiitaItem));
                    } catch (Throwable e) {
                        singleSubscriber.onError(e);
                    }
                }
            });
        }
    }

    @Test
    public void requestToLoadItems_usual() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            Scheduler s = Schedulers.from(executor);
            TestQiitaItemListLoader testLoader = new TestQiitaItemListLoader();
            QiitaItemListPresentationModel model = new QiitaItemListPresentationModel(
                    testLoader, s, s);

            ObservableList<QiitaItem> qiitaItemList = model.getObservableQiitaItemList();

            // 読み込み状態の変更を購読して deque に格納する。
            final LinkedBlockingDeque<LoadingState> deq = new LinkedBlockingDeque<>();
            model.getQiitaItemsLoadingStateChangeObservable().doOnNext(new Action1<LoadingState>() {
                @Override public void call(LoadingState loadingState) {
                    deq.add(loadingState);
                }
            }).subscribe();

            assertEquals("購読したら直後に NOT_STARTED_YET 状態が流れてくる。",
                    LoadingState.NOT_STARTED_YET, deq.poll(2, TimeUnit.SECONDS));
            assertEquals("読み込みを 1 回もしていないとリストは空の状態。",
                    emptyList(),
                    qiitaItemList);

            model.requestToLoadItems();
            assertEquals("読み込みのリクエストを行うと LOADING 状態。",
                    LoadingState.LOADING, deq.poll(2, TimeUnit.SECONDS));
            testLoader.responseWaiter.add(0);
            assertEquals("読み込みが終了すると SUCCESS 状態。",
                    LoadingState.SUCCESS, deq.poll(2, TimeUnit.SECONDS));
            assertEquals("読み込みが完了すると、結果がリストの中に格納される。",
                    singletonList(mResponseQiitaItem),
                    qiitaItemList);

            assertArrayEquals("読み込み状態変更のイベントはほかに発生していない。",
                    new Object[0], deq.toArray());


            model.requestToLoadItems();
            assertEquals("再度読み込みのリクエストを行うと LOADING 状態。",
                    LoadingState.LOADING, deq.poll(2, TimeUnit.SECONDS));
            testLoader.responseWaiter.add(0);
            assertEquals("読み込みが終了すると SUCCESS 状態。",
                    LoadingState.SUCCESS, deq.poll(2, TimeUnit.SECONDS));
            assertEquals("再度読み込みが実行されるとリストの内容が変更されている。",
                    Arrays.asList(mResponseQiitaItem, mResponseQiitaItem),
                    qiitaItemList);

            assertArrayEquals("読み込み状態変更のイベントはほかに発生していない。",
                    new Object[0], deq.toArray());

            assertArrayEquals("空になっている。", new Object[0], deq.toArray());
            model.requestToLoadItems();
            assertEquals("再度読み込みのリクエストを行うと LOADING 状態。",
                    LoadingState.LOADING, deq.poll(2, TimeUnit.SECONDS));
            testLoader.responseWaiter.add(1); // エラーを返すように。
            assertEquals("エラーが返されるので ERROR 状態。",
                    LoadingState.ERROR, deq.poll(2, TimeUnit.SECONDS));
            assertEquals("再度読み込みが実行されてもエラーが返ってくるとリストに変更はない。",
                    Arrays.asList(mResponseQiitaItem, mResponseQiitaItem),
                    qiitaItemList);

            assertArrayEquals("読み込み状態変更のイベントはほかに発生していない。",
                    new Object[0], deq.toArray());
        } finally {
            executor.shutdown();
        }
    }

}
