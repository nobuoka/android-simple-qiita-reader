package info.vividcode.android.sqr.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import info.vividcode.android.sqr.databinding.ItemQiitaItemBinding;

public class QiitaItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemQiitaItemBinding b = ItemQiitaItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecyclerView.ViewHolder(b.getRoot()) {};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
