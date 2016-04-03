package info.vividcode.android.sqr.presentation.presenters;

import info.vividcode.android.sqr.presentation.models.LoadingState;
import info.vividcode.android.sqr.presentation.models.NextPageExistence;

/**
 * リスト中での次ページ読み込みの表示に関する情報を保持するクラス。
 */
public final class NextPageControlInfo {

    public final LoadingState loadingState;
    public final NextPageExistence nextPageExistence;

    public NextPageControlInfo(LoadingState ls, NextPageExistence npe) {
        loadingState = ls;
        nextPageExistence = npe;
    }

    /**
     * エラーメッセージを表示するべきかどうかを返す。
     * エラーが返ってきており、それ以降読み込みが行われていない場合は真。
     * @return エラーメッセージを表示するかどうか。
     */
    public boolean doShowErrorMessage() {
        return viewType() == NextPageControlViewType.ERROR;
    }

    /**
     * 読み込み中表示を行うべきかどうかを返す。
     * {@link #doShowErrorMessage()} が真でなく {@link #doShowNothing()} も真でない場合に真を返す。
     * @return エラーメッセージを表示するかどうか。
     */
    public boolean doShowProgress() {
        return viewType() == NextPageControlViewType.PROGRESS;
    }

    /**
     * 次ページ読み込みに関して何も表示するべきでないかどうかを返す。
     * 次ページが存在しないことがわかっており、読み込み中の状態でないときに真。
     * @return 次ページ読み込みに関して何も表示しないかどうか。
     */
    public boolean doShowNothing() {
        return viewType() == NextPageControlViewType.NONE;
    }

    private NextPageControlViewType viewType() {
        return (nextPageExistence == NextPageExistence.NOT_EXIST && loadingState != LoadingState.LOADING ? NextPageControlViewType.NONE :
                loadingState == LoadingState.ERROR ? NextPageControlViewType.ERROR :
                        NextPageControlViewType.PROGRESS);
    }

    /**
     * 自動で次ページを読み込むべきかどうか。
     * @return 読み込み中の状態でなく、エラーでもなく、次ページが存在しないことが確定しているわけではない場合に真。
     */
    public boolean doLoadAutomatically() {
        return loadingState != LoadingState.LOADING &&
                loadingState != LoadingState.ERROR &&
                nextPageExistence != NextPageExistence.NOT_EXIST;
    }

    private enum NextPageControlViewType {
        PROGRESS, ERROR, NONE
    }

}
