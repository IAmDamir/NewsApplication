package com.example.movieapplication.RecyclerViewFiller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication.R
import com.example.movieapplication.model.NewsArticleData
import com.example.movieapplication.ImageLoader.ImageLoader

class NewsAdapter(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<ListItemViewHolder>() {
    private val newsData = mutableListOf<NewsArticleData>()

    fun setData(newsData: List<NewsArticleData?>) {
        this.newsData.clear()
        this.newsData.addAll(newsData.filterNotNull())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = layoutInflater.inflate(
            R.layout.item_news,
            parent, false)

        return NewsViewHolder(
            view,
            imageLoader,
            object : NewsViewHolder.OnClickListener {
                override fun onClick(articleData: NewsArticleData)
                    = onClickListener.onItemClick(articleData)
            }
        )
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bindData(newsData[position])
    }

    override fun getItemCount() = newsData.size

    interface OnClickListener {
        fun onItemClick(articleData: NewsArticleData)
    }
}