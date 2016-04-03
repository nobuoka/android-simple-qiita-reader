package info.vividcode.android.sqr.presentation.presenters;

import info.vividcode.android.sqr.presentation.models.LoadingState;
import info.vividcode.android.sqr.presentation.models.NextPageExistence;

public final class NextPageControlInfo {

    public final LoadingState loadingState;
    public final NextPageExistence nextPageExistence;

    public NextPageControlInfo(LoadingState ls, NextPageExistence npe) {
        loadingState = ls;
        nextPageExistence = npe;
    }

    public NextPageControlViewType viewType() {
        return (nextPageExistence == NextPageExistence.NOT_EXIST && loadingState != LoadingState.LOADING ? NextPageControlViewType.NONE :
                loadingState == LoadingState.ERROR ? NextPageControlViewType.ERROR :
                NextPageControlViewType.PROGRESS);
    }

    public boolean doShowErrorMessage() {
        return viewType() == NextPageControlViewType.ERROR;
    }

    public boolean doShowProgress() {
        return viewType() == NextPageControlViewType.PROGRESS;
    }

    public boolean doLoadAutomatically() {
        return loadingState != LoadingState.LOADING &&
                loadingState != LoadingState.ERROR &&
                nextPageExistence != NextPageExistence.NOT_EXIST;
    }

}
