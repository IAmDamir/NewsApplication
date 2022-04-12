package com.example.movieapplication.ImageLoader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.movieapplication.R

class GlideImageLoader(private val context: Context) : ImageLoader {
    override fun loadImage(imageURL: String?, imageView: ImageView) {
        if(imageURL.isNullOrBlank())
            imageView.setImageResource(R.drawable.ic_image_not_supported)
        else
            Glide.with(context)
                .load(imageURL)
                .centerInside()
                .into(imageView)
    }
}