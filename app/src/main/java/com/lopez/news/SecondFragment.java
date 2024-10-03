package com.lopez.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        // Retrieve the arguments
        Bundle args = getArguments();
        if (args != null) {
            String itemDetails = args.getString("itemDetails");
            Integer itemImageResId = args.getInt("itemImageResId", -1);

            // Set the details in the TextView
            TextView detailsTextView = view.findViewById(R.id.detailsTextView);
            detailsTextView.setText(itemDetails);

            // Set the image in the ImageView
            ImageView detailsImageView = view.findViewById(R.id.detailsImageView);
            if (itemImageResId != -1) {
                detailsImageView.setImageResource(itemImageResId);
            }
        }

        return view;
    }
}