package com.akashali.eusaserviceprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PromoteServiceAdapter extends RecyclerView.Adapter<PromoteServiceAdapter.MyViewHolder> {
    List<ServiceDetails> newList;
    Context c;
    private PromoteServiceAdapter.OnItemClickListener mListener;
    public PromoteServiceAdapter(List<ServiceDetails> newList, Context c) {
        this.c=c;
        this.newList=newList;
    }
    public void filterList(ArrayList<ServiceDetails> newList) {
        this.newList=newList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public PromoteServiceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemrow= LayoutInflater.from(c).inflate(R.layout.promote_service_row,parent,false);
        return new PromoteServiceAdapter.MyViewHolder(itemrow,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoteServiceAdapter.MyViewHolder holder, int position) {
        holder.promote_title.setText(newList.get(position).getTitle());
        holder.promote_price.setText(newList.get(position).getPrice());
        holder.promote_description.setText(newList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView promote_title;
        public TextView promote_price;
        public TextView promote_description;
        public MyViewHolder(@NonNull View itemView, final PromoteServiceAdapter.OnItemClickListener listener) {
            super(itemView);
            promote_title=itemView.findViewById(R.id.promote_title);
            promote_price=itemView.findViewById(R.id.promote_price);
            promote_description=itemView.findViewById(R.id.promote_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
