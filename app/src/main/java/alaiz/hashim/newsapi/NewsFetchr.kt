package alaiz.hashim.newsapi

import alaiz.hashim.newsapi.api.NewsApi
import alaiz.hashim.newsapi.api.NewsResponse
import alaiz.hashim.newsapi.api.CategoryNewsResponse
import alaiz.hashim.newsapi.api.NewsDetail
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsFetchr {

    private val newsApi: NewsApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            //.baseUrl("http://192.168.43.98/")
            .baseUrl("http://192.168.1.2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsApi = retrofit.create(NewsApi::class.java)
    }

    fun fetchNews(): LiveData<List<NewsItem>> {
        val responseLiveData: MutableLiveData<List<NewsItem>> = MutableLiveData()
        val NewsRequest: Call<NewsResponse> = newsApi.fetchNews()

        NewsRequest.enqueue(object : Callback<NewsResponse> {

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("fetchNews", "Failed to fetch news",t)
            }

            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                Log.d("fetchNews", "Response received correctly")
                val newsResponse: NewsResponse? = response.body()
                var newsItems: List<NewsItem> = newsResponse?.newsItems
                    ?: mutableListOf()
                responseLiveData.value = newsItems
            }
        })

        return responseLiveData
    }
    fun fetchSportNews(): LiveData<List<NewsItem>> {
        val responseLiveData: MutableLiveData<List<NewsItem>> = MutableLiveData()
        val NewsRequest: Call<CategoryNewsResponse> = newsApi.fetchSportNews()

        NewsRequest.enqueue(object : Callback<CategoryNewsResponse> {

            override fun onFailure(call: Call<CategoryNewsResponse>, t: Throwable) {
                Log.e("fetchSportNews", "Failed to fetch sport news",t)
            }

            override fun onResponse(call: Call<CategoryNewsResponse>, response: Response<CategoryNewsResponse>) {
                Log.d("fetchSportNews", "Response received corr")
                val sportNewsResponse: CategoryNewsResponse? = response.body()
                var newsItems: List<NewsItem> = sportNewsResponse?.newsItemsByCategory
                    ?: mutableListOf()
                responseLiveData.value = newsItems
            }
        })

        return responseLiveData
    }

    fun fetchOddityNews(): LiveData<List<NewsItem>> {
        val responseLiveData: MutableLiveData<List<NewsItem>> = MutableLiveData()
        val NewsRequest: Call<CategoryNewsResponse> = newsApi.fetchOddityNews()

        NewsRequest.enqueue(object : Callback<CategoryNewsResponse> {

            override fun onFailure(call: Call<CategoryNewsResponse>, t: Throwable) {
                Log.e("fetchOddityNews", "Failed to fetch oddity news",t)
            }

            override fun onResponse(call: Call<CategoryNewsResponse>, response: Response<CategoryNewsResponse>) {
                Log.d("fetchOddityNews", "Oddity Response received")
                val oddityNewsResponse: CategoryNewsResponse? = response.body()
                var newsItems: List<NewsItem> = oddityNewsResponse?.newsItemsByCategory
                    ?: mutableListOf()
                responseLiveData.value = newsItems
            }
        })

        return responseLiveData
    }
    fun fetchDetailsNews(id:Int): LiveData<NewsItem> {
        val responseLiveData: MutableLiveData<NewsItem> = MutableLiveData()
        val NewsRequest: Call<NewsDetail> = newsApi.fetchDetailsNews(id)

        NewsRequest.enqueue(object : Callback<NewsDetail> {

            override fun onFailure(call: Call<NewsDetail>, t: Throwable) {
                Log.e("fetchDetailsNews", "Failed to fetch  news Details",t)
            }

            override fun onResponse(call: Call<NewsDetail>, response: Response<NewsDetail>) {
                Log.d("fetchDetailsNews", "Details News Response received")
                val detailsNewsResponse: NewsDetail? = response.body()
                var newsItems: NewsItem = detailsNewsResponse?.newsDetailsItems ?: NewsItem()

                responseLiveData.value = newsItems
            }
        })

        return responseLiveData
    }
}