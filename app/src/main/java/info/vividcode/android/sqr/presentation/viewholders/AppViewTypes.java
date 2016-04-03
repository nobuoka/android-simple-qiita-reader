package info.vividcode.android.sqr.presentation.viewholders;

import info.vividcode.android.cra.ViewHolderFactoryRegistry;
import info.vividcode.android.cra.ViewType;

public class AppViewTypes extends ViewHolderFactoryRegistry {

    public static final AppViewTypes INSTANCE = new AppViewTypes();

    public ViewType<QiitaItemViewHolder> qiitaItem = register(QiitaItemViewHolder.FACTORY);

    public ViewType<NextPageControlViewHolder> nextPageControl = register(NextPageControlViewHolder.FACTORY);

}
