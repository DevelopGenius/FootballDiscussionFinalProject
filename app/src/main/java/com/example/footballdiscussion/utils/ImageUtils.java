package com.example.footballdiscussion.utils;

import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageUtils {

    public static void loadImage(String imageUrl, ImageView imageView, int placeholderResId) {
        if (imageUrl != null && imageUrl.length() > 5) {
            Picasso.get().load(imageUrl).placeholder(placeholderResId).into(imageView);
        } else {
            imageView.setImageResource(placeholderResId);
        }
    }
}
