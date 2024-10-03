package com.lopez.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int columnCount = 1;
    private PlaceholderContent placeholderContent;

    public ItemFragment() {
        // Required empty public constructor
    }

    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        // Initialize PlaceholderContent with data
        placeholderContent = new PlaceholderContent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter with click listener
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnCount));
            }

            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(placeholderContent.ITEMS, item -> {
                // Navigate to SecondFragment with item details
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToSecondFragment(item);
                }
            }));
        }
        return view;
    }
}