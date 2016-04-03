package info.vividcode.android.sqr.presentation.viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import info.vividcode.android.cra.ViewHolderFactory;
import info.vividcode.android.sqr.databinding.ItemNextPageControlBinding;

public class NextPageControlViewHolder extends ViewDataBindingViewHolder<ItemNextPageControlBinding> {

    public static final ViewHolderFactory<NextPageControlViewHolder> FACTORY = new ViewHolderFactory<NextPageControlViewHolder>() {
        @Override
        public NextPageControlViewHolder createViewHolder(ViewGroup parent) {
            return new NextPageControlViewHolder(
                    ItemNextPageControlBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    };

    public NextPageControlViewHolder(ItemNextPageControlBinding binding) {
        super(binding);
    }

}
