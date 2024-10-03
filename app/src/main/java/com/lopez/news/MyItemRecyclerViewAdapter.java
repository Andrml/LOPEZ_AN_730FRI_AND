package com.lopez.news;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lopez.news.databinding.FragmentItemBinding;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderContent.PlaceholderItem> values;
    private final OnItemClickListener onItemClicked;

    public MyItemRecyclerViewAdapter(List<PlaceholderContent.PlaceholderItem> items, OnItemClickListener onItemClicked) {
        this.values = items;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentItemBinding binding = FragmentItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlaceholderContent.PlaceholderItem item = values.get(position);
        holder.idView.setText(item.id);
        holder.contentView.setText(item.content);

        // Handle the click event
        holder.itemView.setOnClickListener(v -> onItemClicked.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public interface OnItemClickListener {
        void onItemClick(PlaceholderContent.PlaceholderItem item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView idView;
        public final TextView contentView;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            idView = binding.itemNumber;
            contentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }
    }
}
