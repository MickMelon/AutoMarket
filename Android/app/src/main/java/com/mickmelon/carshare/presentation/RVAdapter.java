package com.mickmelon.carshare.presentation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mickmelon.carshare.R;
import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.util.FragmentHelper;

import java.util.List;

/**
 * RecyclerView adapter used for displaying the adverts
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.AdvertViewHolder> {
    /**
     * The list of adverts to be displayed.
     */
    private List<Advert> _adverts;

    /**
     * Initializes a new instance of the RVAdapter class.
     * @param adverts The list of adverts to be displayed.
     */
    public RVAdapter(List<Advert> adverts) {
        _adverts = adverts;
    }

    /**
     * Called when the ViewHolder is created.
     */
    @NonNull
    @Override
    public AdvertViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.advertcard, viewGroup, false);
        AdvertViewHolder viewHolder = new AdvertViewHolder(view);
        return viewHolder;
    }

    /**
     * Called when the ViewHolder has been binded to the view. This method assigns the values
     * to the views within the layout for each advert.
     */
    @Override
    public void onBindViewHolder(@NonNull AdvertViewHolder advertViewHolder, int i) {
        advertViewHolder._description.setText(_adverts.get(i).getDescription());

        int price = (int) Math.round(_adverts.get(i).getPrice());
        advertViewHolder._price.setText("£" + price);

        advertViewHolder._image.setImageDrawable(null);
        advertViewHolder._image.setImageBitmap(_adverts.get(i).getImageBitmap());

        advertViewHolder._advertId = _adverts.get(i).getAdvertId();
    }

    /**
     * Gets the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return _adverts.size();
    }

    /**
     * The ViewHolder class used to hold the adverts information
     */
    public static class AdvertViewHolder extends RecyclerView.ViewHolder {
        /**
         * The CardView
         */
        CardView _cardView;

        /**
         * The description TextView
         */
        TextView _description;

        /**
         * The price TextView
         */
        TextView _price;

        /**
         * The advert image ImageView
         */
        ImageView _image;

        /**
         * The advert ID
         */
        int _advertId;

        /**
         * Initializes a new instance of the AdvertViewHolder class.
         */
        AdvertViewHolder(View view) {
            super(view);
            _cardView = view.findViewById(R.id.cardView);
            _description = view.findViewById(R.id.textView_Description);
            _price = view.findViewById(R.id.textView_Price);
            _image = view.findViewById(R.id.imageView_Car);
            _advertId = -1;

            // Show the single advert if it has been clicked.
            view.setOnClickListener(v -> {
                ViewAdvertFragment viewAdvert = new ViewAdvertFragment();
                Bundle args = new Bundle();
                args.putInt("Position", _advertId);
                viewAdvert.setArguments(args);

                FragmentHelper.showFragment((AppCompatActivity)_cardView.getContext(), viewAdvert, false);
            });
        }
    }
}