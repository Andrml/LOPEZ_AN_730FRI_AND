package com.lopez.todolist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private static final long DOUBLE_CLICK_DELAY = 300;

    private ArrayList<ItemList> items;
    private ListAdapter adapter;
    private ListView listView;
    private EditText editText;
    private Button buttonAdd;
    private long lastClickTime = 0;
    private int selectedPosition = -1;
    private Uri selectedImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editText);
        buttonAdd = findViewById(R.id.buttonAdd);

        items = new ArrayList<>();
        adapter = new ListAdapter(this, items);
        listView.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (!text.isEmpty()) {
                    Uri imageUri = (selectedImageUri != null) ? selectedImageUri : Uri.parse("android.resource://com.lopez.todolist/" + R.drawable.img);
                    items.add(new ItemList(false, text, imageUri));
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                    selectedImageUri = null;
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime < DOUBLE_CLICK_DELAY) {
                    showEditDeleteDialog(position);
                }
                lastClickTime = currentTime;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null && selectedPosition != -1) {
                items.get(selectedPosition).setImageUri(selectedImageUri);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void showEditDeleteDialog(final int position) {
        final ItemList item = items.get(position);
        selectedPosition = position;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit or Delete");

        builder.setPositiveButton("Edit", (dialog, which) -> {
            editText.setText(item.getText());
            selectedImageUri = item.getImageUri();
            items.remove(position);
            adapter.notifyDataSetChanged();
        });

        builder.setNeutralButton("Change Image", (dialog, which) -> openGallery());

        builder.setNegativeButton("Delete", (dialog, which) -> {
            items.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
        });

        builder.show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }
}
