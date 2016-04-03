package info.vividcode.android.sqr.presentation;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import info.vividcode.android.cra.Binder;
import info.vividcode.android.cra.Component;
import info.vividcode.android.cra.ComponentsRecyclerAdapter;
import info.vividcode.android.cra.FixedViewTypeBinderPairProvider;
import info.vividcode.android.cra.components.ComponentSeries;
import info.vividcode.android.cra.components.ObservableListReferenceComponent;
import info.vividcode.android.sqr.dto.QiitaItem;
import info.vividcode.android.sqr.presentation.presenters.NextPageControlComponent;
import info.vividcode.android.sqr.presentation.presenters.NextPageControlInfo;
import info.vividcode.android.sqr.presentation.viewholders.NextPageControlViewHolder;
import info.vividcode.android.sqr.presentation.viewholders.QiitaItemViewHolder;

public class QiitaItemListAdapter extends ComponentsRecyclerAdapter {

    private static final AppViewTypes VIEW_TYPES = AppViewTypes.INSTANCE;

    private final ObservableListReferenceComponent<QiitaItem> mQiitaItemListComponent =
            new ObservableListReferenceComponent<>(new FixedViewTypeBinderPairProvider<>(VIEW_TYPES.qiitaItem, new Binder<QiitaItemViewHolder, QiitaItem>() {
                @Override
                public void bindViewHolder(QiitaItemViewHolder holder, Component<QiitaItem> component, int positionInComponent) {
                    holder.binding.setItem(component.getItem(positionInComponent));
                    holder.binding.setHandler(new OnItemClickHandler() {
                        @Override
                        public void onClick(View item) {
                            Log.d("xx", "clicked!!!!");
                        }
                    });
                }
            }));
    private final NextPageControlComponent mQiitaItemListNextPageControlComponent =
            new NextPageControlComponent(new FixedViewTypeBinderPairProvider<>(VIEW_TYPES.nextPageControl, new Binder<NextPageControlViewHolder, NextPageControlInfo>() {
                @Override
                public void bindViewHolder(NextPageControlViewHolder holder, Component<NextPageControlInfo> component, int positionInComponent) {
                    holder.binding.setInfo(component.getItem(positionInComponent));
                }
            }));

    public QiitaItemListAdapter() {
        super(VIEW_TYPES);

        ComponentSeries cs = new ComponentSeries();
        cs.addChildComponent(mQiitaItemListComponent);
        cs.addChildComponent(mQiitaItemListNextPageControlComponent);
        setComponent(cs);
    }

    public ObservableListReferenceComponent<QiitaItem> getQiitaItemListReferenceComponent() {
        return mQiitaItemListComponent;
    }

    public NextPageControlComponent getNextPageControlComponent() {
        return mQiitaItemListNextPageControlComponent;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof NextPageControlViewHolder) {
            mQiitaItemListNextPageControlComponent.onRequestNextPage();
        }
    }

}
