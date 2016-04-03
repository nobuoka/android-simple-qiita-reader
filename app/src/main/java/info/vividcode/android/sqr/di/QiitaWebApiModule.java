package info.vividcode.android.sqr.di;

import dagger.Module;
import dagger.Provides;
import info.vividcode.android.sqr.application.QiitaItemListLoader;
import info.vividcode.android.sqr.infrastructure.webapi.QiitaService;
import info.vividcode.android.sqr.infrastructure.webapi.QiitaServiceFactory;
import info.vividcode.android.sqr.infrastructure.webapi.WebApiQiitaItemListLoader;

@Module
public class QiitaWebApiModule {

    private final String mQiitaServiceBaseUrl;

    public QiitaWebApiModule(String qiitaServiceBaseUrl) {
        mQiitaServiceBaseUrl = qiitaServiceBaseUrl;
    }

    @Provides
    public QiitaService provideQiitaService() {
        return QiitaServiceFactory.INSTANCE.createQiitaService(mQiitaServiceBaseUrl);
    }

    @Provides
    public static QiitaItemListLoader provideQiitaItemListLoader(WebApiQiitaItemListLoader loader) {
        return loader;
    }

}
