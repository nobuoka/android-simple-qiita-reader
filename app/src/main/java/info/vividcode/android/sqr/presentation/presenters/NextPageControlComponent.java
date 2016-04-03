package info.vividcode.android.sqr.presentation.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import info.vividcode.android.cra.ViewTypeBinderPairProvider;
import info.vividcode.android.cra.components.AbstractLeafComponent;
import info.vividcode.android.sqr.presentation.models.LoadingState;
import info.vividcode.android.sqr.presentation.models.NextPageExistence;
import info.vividcode.android.sqr.presentation.models.QiitaItemListPresentationModel;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func2;

public final class NextPageControlComponent extends AbstractLeafComponent<NextPageControlInfo> {

    public NextPageControlComponent(
            @NonNull ViewTypeBinderPairProvider<NextPageControlInfo> viewTypeBinderPairProvider) {
        super(viewTypeBinderPairProvider);
    }

    @Nullable
    private Subscription mSubscription = null;

    @Nullable
    private NextPageControlInfo mInfo = null;

    @Override
    public int getItemCount() {
        return (mInfo != null && mInfo.viewType() != NextPageControlViewType.NONE ? 1 : 0);
    }

    @Override
    public NextPageControlInfo getItem(int positionInThisComponent) {
        return mInfo;
    }

    private void updateNextPageControlInfo(NextPageControlInfo info) {
        if (getItemCount() == 1) getObservable().notifyItemRangeRemoved(0, 1);
        mInfo = info;
        if (getItemCount() == 1) getObservable().notifyItemRangeInserted(0, 1);
    }

    public void subscribeToModel(QiitaItemListPresentationModel model) {
        mSubscription = Observable.combineLatest(
                model.getQiitaItemsLoadingStateChangeObservable(),
                model.getQiitaItemsNextPageExistenceChangeObservable(),
                new Func2<LoadingState, NextPageExistence, NextPageControlInfo>() {
                    @Override
                    public NextPageControlInfo call(LoadingState loadingState, NextPageExistence nextPageExistence) {
                        return new NextPageControlInfo(loadingState, nextPageExistence);
                    }
                }
        ).subscribe(new Action1<NextPageControlInfo>() {
            @Override
            public void call(NextPageControlInfo nextPageControlInfo) {
                updateNextPageControlInfo(nextPageControlInfo);
            }
        });
    }

    public void unsubscribeToModel() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }

}
