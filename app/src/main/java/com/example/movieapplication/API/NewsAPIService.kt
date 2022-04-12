package com.example.movieapplication.API

import com.example.movieapplication.model.NewsResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {
    @GET("top-headlines")
    fun getTopHeadlineNews(
        @Query("country") location: String,
        @Query("q") keyword: String?,
        @Query("apiKey") token: String,
        @Query("pageSize") size: Int = 100
    ): Call<NewsResponseData>

    @GET("everything")
    fun getAllNews(
        @Query("q") keyword: String,
        @Query("apiKey") token: String,
        @Query("pageSize") size: Int = 100
    ): Call<NewsResponseData>
}