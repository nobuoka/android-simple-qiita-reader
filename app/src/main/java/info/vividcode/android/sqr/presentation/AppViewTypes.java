package info.vividcode.android.sqr.presentation;

import info.vividcode.android.cra.ViewHolderFactoryRegistry;
import info.vividcode.android.cra.ViewType;
import info.vividcode.android.sqr.presentation.viewholders.NextPageControlViewHolder;
import info.vividcode.android.sqr.presentation.viewholders.QiitaItemViewHolder;

public class AppViewTypes extends ViewHolderFactoryRegistry {

    public static final AppViewTypes INSTANCE = new AppViewTypes();

    public ViewType<QiitaItemViewHolder> qiitaItem = register(QiitaItemViewHolder.FACTORY);

    public ViewType<NextPageControlViewHolder> nextPageControl = register(NextPageControlViewHolder.FACTORY);

}
