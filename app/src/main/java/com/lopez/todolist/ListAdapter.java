package com.lopez.todolist;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private static final long DOUBLE_CLICK_DELAY = 300;
    private ArrayList<ItemList> items;
    private Context context;
    private long lastClickTime = 0;
    private Handler handler = new Handler();

    public ListAdapter(Context context, ArrayList<ItemList> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        ItemList currentItem = (ItemList) getItem(position);

        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        checkBox.setChecked(currentItem.isChecked());
        textView.setText(currentItem.getText());

        if (currentItem.getImageUri() != null) {
            imageView.setImageURI(currentItem.getImageUri());
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime < DOUBLE_CLICK_DELAY) {
                    Toast.makeText(context, "Double-clicked item: " + currentItem.getText(), Toast.LENGTH_SHORT).show();
                    ((MainActivity) context).showEditDeleteDialog(position);
                    lastClickTime = 0;
                } else {
                    lastClickTime = currentTime;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (lastClickTime != 0) {
                                Toast.makeText(context, "Single-clicked item: " + currentItem.getText(), Toast.LENGTH_SHORT).show();
                                lastClickTime = 0;
                            }
                        }
                    }, DOUBLE_CLICK_DELAY);
                }
            }
        });

        return convertView;
    }
}
