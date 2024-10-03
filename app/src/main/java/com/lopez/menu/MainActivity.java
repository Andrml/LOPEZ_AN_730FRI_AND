package com.lopez.menu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public boolean isInNewLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableEdgeToEdge();
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return handleMenuSelection(item.getItemId());
    }

    private boolean handleMenuSelection(int itemId) {

        if (itemId == R.id.action_exit) {
            exitApplication();
        } else if (itemId == R.id.action_new_icon) {
            showActivityFragment();
        } else if (itemId == R.id.action_fragment_icon) {
            toggleFragmentLayout();
        } else {
            return false;
        }
        return true;
    }


    private void exitApplication() {
        finish();
    }

    private void showActivityFragment() {
        new ActivityFragment().show(getSupportFragmentManager(), "ActionDialog");
    }

    private void toggleFragmentLayout() {
        if (isInNewLayout) {
            setContentView(R.layout.activity_main);
            isInNewLayout = false;
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainFragment())
                    .addToBackStack(null)
                    .commit();
            isInNewLayout = true;
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void enableEdgeToEdge() {

    }
}
