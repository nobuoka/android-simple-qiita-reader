package info.vividcode.android.sqr.presentation.viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import info.vividcode.android.cra.ViewHolderFactory;
import info.vividcode.android.sqr.databinding.ItemQiitaItemBinding;

public class QiitaItemViewHolder extends ViewDataBindingViewHolder<ItemQiitaItemBinding> {

    public static final ViewHolderFactory<QiitaItemViewHolder> FACTORY = new ViewHolderFactory<QiitaItemViewHolder>() {
        @Override
        public QiitaItemViewHolder createViewHolder(ViewGroup parent) {
            return new QiitaItemViewHolder(
                    ItemQiitaItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    };

    public QiitaItemViewHolder(ItemQiitaItemBinding binding) {
        super(binding);
    }

}
