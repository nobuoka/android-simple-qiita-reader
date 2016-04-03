package info.vividcode.android.sqr.presentation.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import info.vividcode.android.cra.ViewTypeBinderPairProvider;
import info.vividcode.android.cra.components.AbstractLeafComponent;
import info.vividcode.android.sqr.presentation.models.LoadingState;
import info.vividcode.android.sqr.presentation.models.NextPageExistence;
import info.vividcode.android.sqr.presentation.models.QiitaItemListPresentationModel;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func2;

/**
 * 次ページ読み込みのための項目を扱う {@link info.vividcode.android.cra.Component}。
 * TODO : 現在は {@link QiitaItemListPresentationModel} 専用になっているが、もっと汎用的にする。
 */
public final class NextPageControlComponent extends AbstractLeafComponent<NextPageControlInfo> {

    public NextPageControlComponent(
            @NonNull ViewTypeBinderPairProvider<NextPageControlInfo> viewTypeBinderPairProvider) {
        super(viewTypeBinderPairProvider);
    }

    @Nullable
    private Subscription mSubscription = null;

    @Nullable
    private NextPageControlInfo mInfo = null;

    @Nullable
    private QiitaItemListPresentationModel mModel;

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
        mModel = model;
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

    public void onRequestNextPage() {
        if (mModel == null) return;
        if (mInfo == null) return;

        if (mInfo.doLoadAutomatically()) mModel.requestToLoadItems();
    }

}
