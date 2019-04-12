package com.ken.customview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeboAdapter extends RecyclerView.Adapter<WeboAdapter.WeboViewHolder> {


    private final int height;

    public WeboAdapter(int height) {
        this.height = height;
    }

    @NonNull
    @Override
    public WeboViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == 0){
            return new WeboViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_empty_head,viewGroup,false));
        }else{
            return new WeboViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_webo,viewGroup,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull WeboViewHolder weboViewHolder, int i) {
            if(i>0){
                ((TextView) weboViewHolder.itemView.findViewById(R.id.title)).setText(String.format("条目 == %d",i));
            }else {
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

    public static class WeboViewHolder extends RecyclerView.ViewHolder{

        public WeboViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
