package info.vividcode.android.sqr.presentation.presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import info.vividcode.android.cra.Binder;
import info.vividcode.android.cra.Component;
import info.vividcode.android.cra.ComponentsRecyclerAdapter;
import info.vividcode.android.cra.FixedViewTypeBinderPairProvider;
import info.vividcode.android.cra.components.ComponentSeries;
import info.vividcode.android.cra.components.ObservableListReferenceComponent;
import info.vividcode.android.sqr.QiitaItemDetailActivity;
import info.vividcode.android.sqr.dto.QiitaItem;
import info.vividcode.android.sqr.presentation.viewholders.AppViewTypes;
import info.vividcode.android.sqr.presentation.viewholders.NextPageControlViewHolder;
import info.vividcode.android.sqr.presentation.viewholders.QiitaItemViewHolder;

public class QiitaItemListAdapter extends ComponentsRecyclerAdapter {

    private static final AppViewTypes VIEW_TYPES = AppViewTypes.INSTANCE;

    /**
     * Qiita の投稿一覧のリストを扱うコンポーネント。
     */
    private final ObservableListReferenceComponent<QiitaItem> mQiitaItemListComponent =
            new ObservableListReferenceComponent<>(new FixedViewTypeBinderPairProvider<>(VIEW_TYPES.qiitaItem, new Binder<QiitaItemViewHolder, QiitaItem>() {
                @Override
                public void bindViewHolder(QiitaItemViewHolder holder, Component<QiitaItem> component, int positionInComponent) {
                    final QiitaItem item = component.getItem(positionInComponent);
                    holder.binding.setItem(item);
                    holder.binding.setHandler(new OnItemClickHandler() {
                        @Override
                        public void onClick(View view) {
                            Context ctx = view.getContext();
                            ctx.startActivity(QiitaItemDetailActivity.createIntent(ctx, item));
                        }
                    });
                }
            }));

    /**
     * 次ページ読み込みの表示を扱うコンポーネント。
     */
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
