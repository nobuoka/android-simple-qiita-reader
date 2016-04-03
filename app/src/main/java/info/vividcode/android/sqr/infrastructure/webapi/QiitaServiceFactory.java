package info.vividcode.android.sqr.infrastructure.webapi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QiitaServiceFactory {

    public static final QiitaServiceFactory INSTANCE = new QiitaServiceFactory();

    public QiitaService createQiitaService(OkHttpClient client, String baseUrl) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QiitaService.class);
    }

}
