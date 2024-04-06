package com.example.krestikinoliki;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class FieldElement {
    public Vector2 position;
    private ImageView _image;

    public FieldElement(Vector2 position, ImageView image) {
        this.position = position;
        _image = image;
    }

    public boolean compareImage(ImageView image) {
        return _image == image;
    }

    public void setImage(Drawable drawable) {
        _image.setImageDrawable(drawable);
    }

    @NonNull
    @Override
    public String toString() {
        return "Field Element " + position;
    }
}