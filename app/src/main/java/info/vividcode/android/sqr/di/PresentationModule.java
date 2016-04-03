package info.vividcode.android.sqr.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class PresentationModule {

    @Provides
    @Named("main")
    public static Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Named("io")
    public static Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

}
