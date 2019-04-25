package com.ken.customview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeboAdapter extends RecyclerView.Adapter<WeboAdapter.WeboViewHolder> {


    private final int height;
    private final float stickyHeight;

    public WeboAdapter(int height, float stickyHeight) {
        this.height = height;
        this.stickyHeight = stickyHeight;
    }

    @NonNull
    @Override
    public WeboViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new WeboViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_empty_head, viewGroup, false));
        } else if(i == 1) {
            return new WeboViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_message, viewGroup, false));
        } else {
            return new WeboViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_webo, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull WeboViewHolder weboViewHolder, int i) {
        if(i == 1){
            ViewGroup.LayoutParams layoutParams = weboViewHolder.itemView.getLayoutParams();
            layoutParams.height = (int) stickyHeight;
            weboViewHolder.itemView.setLayoutParams(layoutParams);
            weboViewHolder.itemView.setBackgroundResource(R.color.colorPrimary);
        } else if(i > 1) {
            ((TextView) weboViewHolder.itemView.findViewById(R.id.title)).setText(String.format("条目 == %d", i));
            weboViewHolder.itemView.setBackgroundResource(R.color.white);
        } else {
            ViewGroup.LayoutParams layoutParams = weboViewHolder.itemView.getLayoutParams();
            layoutParams.height = height;
            weboViewHolder.itemView.setLayoutParams(layoutParams);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 80;
    }

    public static class WeboViewHolder extends RecyclerView.ViewHolder {

        public WeboViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
