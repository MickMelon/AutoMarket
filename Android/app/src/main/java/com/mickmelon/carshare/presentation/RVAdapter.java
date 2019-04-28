package com.mickmelon.carshare.presentation;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mickmelon.carshare.R;
import com.mickmelon.carshare.core.Advert;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.AdvertViewHolder> {
    private List<Advert> _adverts;

    public RVAdapter(List<Advert> adverts) {
        _adverts = adverts;
    }

    @NonNull
    @Override
    public AdvertViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.advertcard, viewGroup, false);
        AdvertViewHolder viewHolder = new AdvertViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertViewHolder advertViewHolder, int i) {
        advertViewHolder._description.setText(_adverts.get(i).getDescription());
        advertViewHolder._price.setText(String.format("%s", _adverts.get(i).getPrice()));

        // TODO: Check if bitmap is there!
        advertViewHolder._image.setImageBitmap(_adverts.get(i).getImageBitmap());
    }

    @Override
    public int getItemCount() {
        return _adverts.size();
    }

    public static class AdvertViewHolder extends RecyclerView.ViewHolder {
        CardView _cardView;
        TextView _description;
        TextView _price;
        ImageView _image;

        AdvertViewHolder(View view) {
            super(view);
            _cardView = view.findViewById(R.id.cardView);
            _description = view.findViewById(R.id.textView_Description);
            _price = view.findViewById(R.id.textView_Price);
            _image = view.findViewById(R.id.imageView_Car);
        }
    }
}