package com.example.movieapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsArticleSource(
    val id: String?,
    val name: String?
) : Parcelable
