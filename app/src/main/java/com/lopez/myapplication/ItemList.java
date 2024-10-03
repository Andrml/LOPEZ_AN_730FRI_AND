package com.lopez.myapplication;

import android.net.Uri;

public class ItemList {
    private boolean checked;
    private String text;
    private Uri imageUri;

    public ItemList(boolean checked, String text, Uri imageUri) {
        this.checked = checked;
        this.text = text;
        this.imageUri = imageUri;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}