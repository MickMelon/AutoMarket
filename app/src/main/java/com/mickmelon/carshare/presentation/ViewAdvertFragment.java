package com.mickmelon.carshare.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mickmelon.carshare.R;
import com.mickmelon.carshare.database.AdvertRepository;

public class ViewAdvertFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewadvert, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        int advertId = args.getInt("Position");
        Toast.makeText(getContext(),
                "ADVERT:" + advertId,
                Toast.LENGTH_SHORT).show();

        TextView textView = getView().findViewById(R.id.textView);
        textView.setText("ID:" + advertId);
    }


}
