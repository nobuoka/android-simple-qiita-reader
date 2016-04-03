package info.vividcode.android.sqr.infrastructure.webapi;

import java.util.List;

import javax.inject.Inject;

import info.vividcode.android.sqr.application.QiitaItemListLoader;
import info.vividcode.android.sqr.dto.QiitaItem;
import retrofit2.Response;
import rx.Single;
import rx.SingleSubscriber;

public class WebApiQiitaItemListLoader implements QiitaItemListLoader {

    private final QiitaService mQiitaService;

    @Inject
    public WebApiQiitaItemListLoader(QiitaService qiitaService) {
        mQiitaService = qiitaService;
    }

    @Override
    public Single<List<QiitaItem>> getQiitaItemList(final int page) {
        return Single.create(new Single.OnSubscribe<List<QiitaItem>>() {
            @Override
            public void call(final SingleSubscriber<? super List<QiitaItem>> singleSubscriber) {
                try {
                    // TODO : ここで時間がかかっているので調査が必要。
                    // HTTP のレスポンスが返ってきてから時間がかかっているので、JSON のパースなどで時間がかかってそう？
                    Response<List<QiitaItem>> response = mQiitaService.getItems(page).execute();
                    if (response.code() == 200) {
                        singleSubscriber.onSuccess(response.body());
                    } else {
                        singleSubscriber.onError(new RuntimeException("Response code : " + response.code()));
                    }
                } catch (Throwable e) {
                    singleSubscriber.onError(e);
                }
            }
        });
    }

}
