package info.vividcode.android.sqr.presentation.viewholders;

import android.support.v7.widget.RecyclerView;

import info.vividcode.android.cra.ViewHolderFactoryRegistry;
import info.vividcode.android.cra.ViewType;

/**
 * アプリ全体で共通の {@link ViewHolderFactoryRegistry}。
 * {@link RecyclerView} で使われる view holder の生成処理が宣言的に登録される。
 */
public class AppViewTypes extends ViewHolderFactoryRegistry {

    public static final AppViewTypes INSTANCE = new AppViewTypes();

    /** Qiita の投稿を表す view type。 */
    public ViewType<QiitaItemViewHolder> qiitaItem = register(QiitaItemViewHolder.FACTORY);

    /** 次ページ読み込みの表示を表す view type。 */
    public ViewType<NextPageControlViewHolder> nextPageControl = register(NextPageControlViewHolder.FACTORY);

}
