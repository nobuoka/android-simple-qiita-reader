package info.vividcode.android.sqr.presentation;

import info.vividcode.android.cra.Binder;
import info.vividcode.android.cra.Component;
import info.vividcode.android.cra.ComponentsRecyclerAdapter;
import info.vividcode.android.cra.FixedViewTypeBinderPairProvider;
import info.vividcode.android.cra.components.ObservableListReferenceComponent;
import info.vividcode.android.sqr.dto.QiitaItem;
import info.vividcode.android.sqr.presentation.viewholders.QiitaItemViewHolder;

public class QiitaItemListAdapter extends ComponentsRecyclerAdapter {

    private static final AppViewTypes VIEW_TYPES = AppViewTypes.INSTANCE;

    private final ObservableListReferenceComponent<QiitaItem> mQiitaItemListComponent =
            new ObservableListReferenceComponent<>(new FixedViewTypeBinderPairProvider<>(VIEW_TYPES.qiitaItem, new Binder<QiitaItemViewHolder, QiitaItem>() {
                @Override
                public void bindViewHolder(QiitaItemViewHolder holder, Component<QiitaItem> component, int positionInComponent) {
                    holder.binding.setItem(component.getItem(positionInComponent));
                }
            }));

    public QiitaItemListAdapter() {
        super(VIEW_TYPES);
        setComponent(mQiitaItemListComponent);
    }

    public ObservableListReferenceComponent<QiitaItem> getQiitaItemListReferenceComponent() {
        return mQiitaItemListComponent;
    }

}
