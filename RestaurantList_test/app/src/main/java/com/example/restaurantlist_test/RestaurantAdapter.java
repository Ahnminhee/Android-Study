package com.example.minchelin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> implements OnRestaurantClickListener{
    ArrayList<Restaurant> items = new ArrayList<Restaurant>();

    OnRestaurantClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.restaurant_item, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Restaurant item) {
        items.add(item);
    }

    public void setItems(ArrayList<Restaurant> items) {
        this.items = items;
    }

    public Restaurant getItem(int position) {
        return items.get(position);
    }

    public void  setItem(int position, Restaurant item) {
        items.set(position,item);
    }


    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        ImageView imageView;

        public ViewHolder(View itemView, final OnRestaurantClickListener listener) {
            super(itemView);

            textView = itemView.findViewById(R.id.Restaurant_name);
            textView2 = itemView.findViewById(R.id.Restaurant_moblie);
            imageView = itemView.findViewById(R.id.Restaurant_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener != null) {
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(Restaurant item) {
            textView.setText(item.getName());
            textView2.setText(item.getMobile());
            imageView.setImageResource(item.getImage());
        }
    }
}
