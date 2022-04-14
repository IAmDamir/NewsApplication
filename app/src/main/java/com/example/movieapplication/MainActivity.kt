package com.example.movieapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapplication.API.NewsAPIService
import com.example.movieapplication.ImageLoader.GlideImageLoader
import com.example.movieapplication.RecyclerViewFiller.NewsAdapter
import com.example.movieapplication.RecyclerViewFiller.PopUpWindow
import com.example.movieapplication.model.NewsArticleData
import com.example.movieapplication.model.NewsResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

enum class Screen{
    TOP_HEADINGS, EVERYTHING
}
enum class Source(val domain: String?) {
    NO_SOURCE(null),
    BBC("bbc.co.uk"),
    NYT("nytimes.com")
}


class MainActivity : AppCompatActivity() {
    private val apiKey = "ff371db233a541a2a5395573579ee290"
    private val baseURL = "https://newsapi.org/v2/"

    private var currentScreen = Screen.TOP_HEADINGS
    private var currentSource = Source.NO_SOURCE

    private val layout: ConstraintLayout by lazy {findViewById(R.id.activity_main)}
    private val search: SearchView by lazy {findViewById(R.id.search_bar)}
    private val amountOfNews: TextView by lazy {findViewById(R.id.amount_of_news)}
    private val topHeadingsBtn: Button by lazy {findViewById(R.id.button_top_headings)}
    private val everythingBtn: Button by lazy {findViewById(R.id.button_everything)}
    private val BBCNewsBtn: Button by lazy {findViewById(R.id.button_BBC_news_top_headings)}
    private val NYTNewsBtn: Button by lazy {findViewById(R.id.button_NYT_news_top_headings)}

    private val enqueue = object : Callback<NewsResponseData>{
        override fun onFailure(call: Call<NewsResponseData>, t: Throwable) {
            showError("Response failed: ${t.message}")
        }
        override fun onResponse(
            call: Call<NewsResponseData>,
            response: Response<NewsResponseData>
        ) = handleResponse(response)
    }

    private val recyclerView: RecyclerView
        by lazy { findViewById(R.id.recycler_view) }
    private val newsAdapter by lazy { NewsAdapter(
        layoutInflater,
        GlideImageLoader(this),
        object : NewsAdapter.OnClickListener {
            override fun onItemClick(articleData: NewsArticleData)
                = showSelectDialog(articleData)
        }
    ) }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val newsAPIService by lazy {
        retrofit.create(NewsAPIService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search.queryHint = "Search in article title and body"

        search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(currentSource == Source.NO_SOURCE) {
                    if (currentScreen == Screen.TOP_HEADINGS)
                        getTopHeadlines(query)
                } else {
                    getEverythingFromSource(query, currentSource)
                }

                search.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        everythingBtn.setOnClickListener{getEverything(null)}
        topHeadingsBtn.setOnClickListener{getTopHeadlines(null)}
        BBCNewsBtn.setOnClickListener {getEverythingFromSource(null, Source.BBC)}
        NYTNewsBtn.setOnClickListener {getEverythingFromSource(null, Source.NYT)}

        recyclerView.layoutManager =
            LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)
        recyclerView.adapter = newsAdapter

        getTopHeadlines(null)
    }

    override fun onResume() {
        super.onResume()
        layout.alpha = 1f
    }

    private fun getTopHeadlines(keyword: String?) {
        newsAPIService
            .getTopHeadlineNews("us", keyword, apiKey)
            .enqueue(enqueue)

        currentScreen = Screen.TOP_HEADINGS
    }

    private fun getEverything(keyword: String?) {
        newsAPIService
            .getAllNews(keyword ?: "apple", apiKey)
            .enqueue(enqueue)

        currentScreen = Screen.EVERYTHING
    }

    private fun getEverythingFromSource(keyword: String?, source: Source) {
        newsAPIService
            .getAllNewsFromSource(keyword ?: "apple", source.domain, apiKey)
            .enqueue(enqueue)

        currentScreen = Screen.EVERYTHING
        currentSource = source
    }


    private fun showSelectDialog(articleData: NewsArticleData) {
        val intent = Intent(this, PopUpWindow::class.java)
        intent.putExtra("extra_object", articleData)

        layout.alpha = 0.5f

        startActivity(intent)
    }

    private fun handleResponse(response: Response<NewsResponseData>) =
        if (response.isSuccessful) {
            response.body()?.let { validResponse ->
                handleValidResponse(validResponse)
            } ?: Unit
        } else {
            Log.e("MainActivity", response.errorBody().toString())
            showError("Response was unsuccessful: ${response.errorBody()}")
        }

    @SuppressLint("SetTextI18n")
    private fun handleValidResponse(response: NewsResponseData) {
        amountOfNews.text = "Amount of news: " + response.amountOfResults
        newsAdapter.setData(response.articles)
    }

    private fun showError(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
            .show()
}