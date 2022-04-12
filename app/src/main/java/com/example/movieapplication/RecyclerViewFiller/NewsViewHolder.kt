package com.example.movieapplication.RecyclerViewFiller

import android.content.DialogInterface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication.R
import com.example.movieapplication.model.NewsArticleData
import com.example.movieapplication.ImageLoader.*

class NewsViewHolder(
        private val containerView: View,
        private val imageLoader: ImageLoader,
        private val onClickListener: OnClickListener
) : ListItemViewHolder(containerView){
        private val newTitleView: TextView
                by lazy { containerView.findViewById(R.id.item_news_title) }
        private val newAuthorView: TextView
                by lazy { containerView.findViewById(R.id.item_news_author) }
        private val newDescriptionView: TextView
                by lazy { containerView.findViewById(R.id.item_news_description) }
        private val newPublishedDateView: TextView
                by lazy { containerView.findViewById(R.id.item_news_published_date) }
        private val newImageView: ImageView
                by lazy { containerView.findViewById(R.id.item_news_image) }

        override fun bindData(articleData: NewsArticleData) {
                containerView.setOnClickListener{
                        onClickListener.onClick(articleData) }

                imageLoader.loadImage(articleData.image, newImageView)
                newTitleView.text = articleData.title ?: "[No Title]"
                newAuthorView.text = articleData.author ?: "[No Author]"
                newDescriptionView.text = articleData.description ?: "[No Description]"
                newPublishedDateView.text = articleData.publishedAt ?: "[No Date]"
        }

        interface OnClickListener{
                fun onClick(articleData: NewsArticleData)
        }
}