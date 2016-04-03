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
    public Single<List<QiitaItem>> getQiitaItemList() {
        return Single.create(new Single.OnSubscribe<List<QiitaItem>>() {
            @Override
            public void call(final SingleSubscriber<? super List<QiitaItem>> singleSubscriber) {
                try {
                    Response<List<QiitaItem>> response = mQiitaService.getItems().execute();
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
