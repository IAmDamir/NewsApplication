package com.example.movieapplication.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponseData(
    @field:Json(name = "totalResults")
    val amountOfResults: String = "0",
    val articles: List<NewsArticleData>
) : Parcelable
