package com.example.movieapplication.ImageLoader

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(imageURL: String?, imageView: ImageView)
}