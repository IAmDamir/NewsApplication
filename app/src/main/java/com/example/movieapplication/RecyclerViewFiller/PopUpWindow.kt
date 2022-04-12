package com.example.movieapplication.RecyclerViewFiller

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.movieapplication.R
import com.example.movieapplication.model.NewsArticleData

class PopUpWindow : Activity() {
    private val title: TextView by lazy { findViewById(R.id.pop_up_title) }
    private val author: TextView by lazy { findViewById(R.id.pop_up_author) }
    private val source: TextView by lazy { findViewById(R.id.pop_up_source) }
    private val time: TextView by lazy { findViewById(R.id.pop_up_published_time) }
    private val url: TextView by lazy { findViewById(R.id.pop_up_url) }
    private val content: TextView by lazy { findViewById(R.id.pop_up_content) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.pop_window)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width*0.8).toInt(), (height*0.8).toInt())

        val articleData = intent.getParcelableExtra<NewsArticleData>("extra_object")

        title.text = articleData?.title
        author.text = articleData?.author
        source.text = articleData?.source?.name
        time.text = articleData?.publishedAt
        url.text = articleData?.url
        content.text = articleData?.content
    }
}