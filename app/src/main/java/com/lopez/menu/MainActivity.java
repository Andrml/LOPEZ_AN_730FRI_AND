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

    public boolean isInNewLayout = false; // Changed to public to allow access

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableEdgeToEdge();
        setContentView(R.layout.activity_main); // Initial layout

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Inflate the menu for the toolbar dropdown
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Handle menu item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return handleMenuSelection(item.getItemId());
    }

    private boolean handleMenuSelection(int itemId) {
        // Using a simple if-else structure for clarity
        if (itemId == R.id.action_exit) {
            exitApplication();
        } else if (itemId == R.id.action_new_icon) {
            showActivityFragment();
        } else if (itemId == R.id.action_fragment_icon) {
            toggleFragmentLayout();
        } else {
            return false; // Return false for unhandled cases
        }
        return true; // Return true if an action was handled
    }

    // Method to handle exiting the application
    private void exitApplication() {
        finish(); // Close the current activity, which usually ends the app
    }

    private void showActivityFragment() {
        new ActivityFragment().show(getSupportFragmentManager(), "ActionDialog");
    }

    private void toggleFragmentLayout() {
        if (isInNewLayout) {
            setContentView(R.layout.activity_main); // Switch back to main layout
            isInNewLayout = false; // Update the flag
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainFragment())
                    .addToBackStack(null)
                    .commit();
            isInNewLayout = true; // Set the flag to indicate we're in a new layout
        }
    }

    // Helper function to show a toast message
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Placeholder for enableEdgeToEdge function
    private void enableEdgeToEdge() {
        // Add the implementation if needed.
    }
}
