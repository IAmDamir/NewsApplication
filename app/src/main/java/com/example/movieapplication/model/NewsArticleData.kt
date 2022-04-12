package com.example.movieapplication.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsArticleData(
    val source: NewsArticleSource,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    @field:Json(name = "urlToImage")
    val image: String?,
    val publishedAt: String?,
    val content: String?
) : Parcelable
