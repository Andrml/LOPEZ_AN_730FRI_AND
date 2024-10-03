package com.lopez.news;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lopez.news.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            // Add the initial fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, ItemFragment.newInstance(1))
                    .commit();
        }
    }

    public void navigateToSecondFragment(PlaceholderContent.PlaceholderItem item) {
        SecondFragment secondFragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString("itemDetails", item.details);
        args.putInt("itemImageResId", item.imageResId);
        secondFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (findViewById(R.id.second_fragment) != null) {
            // Replace the right-side fragment in landscape mode
            transaction.replace(R.id.second_fragment, secondFragment);
        } else {
            // Replace the whole fragment in portrait mode and add to backstack
            transaction.replace(R.id.nav_host_fragment, secondFragment);
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }
}