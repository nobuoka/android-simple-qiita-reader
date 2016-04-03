package info.vividcode.android.sqr.presentation.viewholders;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class ViewDataBindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public final T binding;

    public ViewDataBindingViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}
