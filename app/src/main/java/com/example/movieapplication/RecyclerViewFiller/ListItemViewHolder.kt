package com.example.movieapplication.RecyclerViewFiller

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication.model.NewsArticleData

abstract class ListItemViewHolder(
    containerView: View,
) : RecyclerView.ViewHolder(containerView) {
    abstract fun bindData(articleData : NewsArticleData)
}