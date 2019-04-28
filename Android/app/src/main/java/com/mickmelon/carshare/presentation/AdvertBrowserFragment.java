package com.mickmelon.carshare.presentation;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mickmelon.carshare.R;
import com.mickmelon.carshare.core.Advert;
import com.mickmelon.carshare.database.DataAccess;
import com.mickmelon.carshare.presentation.viewmodels.AdvertBrowserViewModel;

/**
 * The fragment for controlling the advert browser layout.
 */
public class AdvertBrowserFragment extends Fragment {
    /**
     * The advert selected listener used to handle the case when an advert is selected.
     */
    private OnAdvertSelectedListener _advertSelectedListener;


    /**
     * The linear layout that contains the populated adverts.
     */
    private LinearLayout _linearLayout;

    /**
     * Called when the fragment is about to be created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advertbrowser, container, false);
    }

    /**
     * Called after the fragment has been created.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        AdvertBrowserViewModel viewModel = ViewModelProviders.of(this).get(AdvertBrowserViewModel.class);
        viewModel.getAdverts().observe(this, adverts -> {
            RecyclerView recyclerView = view.findViewById(R.id.rv);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            RVAdapter adapter = new RVAdapter(adverts);
            recyclerView.setAdapter(adapter);

           /* for (Advert advert : adverts) {
                final int advertId = advert.getAdvertId();

                /*TextView textView = new TextView(getContext());
                textView.setText(advert.getVehicleReg());

                Button button = new Button(getContext());
                button.setText("View " + advert.getAdvertId());
                button.setOnClickListener(v -> _advertSelectedListener.onAdvertSelected(advertId));

                layout.addView(textView);
                layout.addView(button);


                CardView cardView = new CardView(getContext());
                ConstraintLayout constraintLayout = new ConstraintLayout(getContext());
                ImageView imageView = new ImageView(getContext());
                TextView price = new TextView(getContext());
                TextView description = new TextView(getContext());

                // might need check
                imageView.setImageBitmap(advert.getImageBitmap());
                price.setText("" + advert.getPrice());
                description.setText(advert.getDescription());

                cardView.addView(constraintLayout);
                constraintLayout.addView(imageView);
                constraintLayout.addView(price);
                constraintLayout.addView(description);

                ConstraintSet set = new ConstraintSet();

                set.


                _linearLayout.addView(cardView);



            }*/
        });
    }



    /**
     * Sets the OnAdvertSelectedListener.
     * @param listener The OnAdvertSelectedListener.
     */
    public void setOnAdvertSelectedListener(OnAdvertSelectedListener listener) {
        _advertSelectedListener = listener;
    }

    /**
     * The interface for the advert selected listener.
     */
    public interface OnAdvertSelectedListener {
        void onAdvertSelected(int position);
    }
}