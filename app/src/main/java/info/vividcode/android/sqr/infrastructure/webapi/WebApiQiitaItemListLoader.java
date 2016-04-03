package info.vividcode.android.sqr.infrastructure.webapi;

import java.util.List;

import javax.inject.Inject;

import info.vividcode.android.sqr.application.QiitaItemListLoader;
import info.vividcode.android.sqr.dto.QiitaItem;
import retrofit2.Call;
import retrofit2.Callback;
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
                mQiitaService.getItems().enqueue(new Callback<List<QiitaItem>>() {
                    @Override
                    public void onResponse(Call<List<QiitaItem>> call, Response<List<QiitaItem>> response) {
                        if (response.code() == 200) {
                            singleSubscriber.onSuccess(response.body());
                        } else {
                            singleSubscriber.onError(new RuntimeException("Response code : " + response.code()));
                        }
                    }
                    @Override
                    public void onFailure(Call<List<QiitaItem>> call, Throwable t) {
                        singleSubscriber.onError(t);
                    }
                });
            }
        });
    }

}
