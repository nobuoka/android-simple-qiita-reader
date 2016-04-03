package info.vividcode.android.sqr.di;

import dagger.Module;
import dagger.Provides;
import info.vividcode.android.sqr.application.QiitaItemListLoader;
import info.vividcode.android.sqr.infrastructure.webapi.QiitaService;
import info.vividcode.android.sqr.infrastructure.webapi.QiitaServiceFactory;
import info.vividcode.android.sqr.infrastructure.webapi.WebApiQiitaItemListLoader;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class QiitaWebApiModule {

    private final String mQiitaServiceBaseUrl;

    public static QiitaWebApiModule createFormalInstance() {
        return new QiitaWebApiModule("https://qiita.com");
    }

    public QiitaWebApiModule(String qiitaServiceBaseUrl) {
        mQiitaServiceBaseUrl = qiitaServiceBaseUrl;
    }

    @Provides
    public OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // TODO : デバッグ時のみログ出力するように。
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                .build();
    }

    @Provides
    public QiitaService provideQiitaService(OkHttpClient client) {
        return QiitaServiceFactory.INSTANCE.createQiitaService(client, mQiitaServiceBaseUrl);
    }

    @Provides
    public static QiitaItemListLoader provideQiitaItemListLoader(WebApiQiitaItemListLoader loader) {
        return loader;
    }

}
