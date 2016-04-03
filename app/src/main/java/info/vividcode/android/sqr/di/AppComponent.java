package info.vividcode.android.sqr.di;

import dagger.Component;
import info.vividcode.android.sqr.presentation.models.QiitaItemListPresentationModel;

@Component(modules = {PresentationModule.class, QiitaWebApiModule.class})
public interface AppComponent {

    QiitaItemListPresentationModel qiitaItemListPresentationModel();

}
