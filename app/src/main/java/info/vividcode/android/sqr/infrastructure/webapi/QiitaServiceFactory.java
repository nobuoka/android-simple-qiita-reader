package info.vividcode.android.sqr.infrastructure.webapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QiitaServiceFactory {

    public static final QiitaServiceFactory INSTANCE = new QiitaServiceFactory();

    public QiitaService createQiitaService(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QiitaService.class);
    }

}
