package com.mickmelon.carshare.presentation;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
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
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            RVAdapter adapter = new RVAdapter(adverts);
            recyclerView.setAdapter(adapter);
        });
    }
}