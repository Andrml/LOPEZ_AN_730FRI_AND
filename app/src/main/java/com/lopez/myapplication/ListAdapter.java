package com.lopez.myapplication;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    public interface OnItemActionListener {
        void onItemDoubleClicked(int position);
    }

    private static final long DOUBLE_CLICK_DELAY = 300;
    private ArrayList<ItemList> items;
    private Context context;
    private long lastClickTime = 0;
    private Handler handler = new Handler();
    private OnItemActionListener listener;

    public ListAdapter(Context context, ArrayList<ItemList> items) {
        this.context = context;
        this.items = items;
    }

    public void setOnItemActionListener(OnItemActionListener listener) {
        this.listener = listener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        final ItemList currentItem = (ItemList) getItem(position);

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
                    if (listener != null) {
                        listener.onItemDoubleClicked(position);
                    }
                    lastClickTime = 0;
                } else {
                    lastClickTime = currentTime;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (lastClickTime != 0) {
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
