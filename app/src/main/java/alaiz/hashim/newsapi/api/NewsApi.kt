package alaiz.hashim.newsapi.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {
    @GET("news_api/api/news_api.php")
    fun fetchNews(): Call<NewsResponse>

    @GET("news_api/api/news_api.php?category=2")
    fun fetchSportNews(): Call<CategoryNewsResponse>
    @GET("news_api/api/news_api.php?category=3")
    fun fetchOddityNews(): Call<CategoryNewsResponse>

    @GET("news_api/api/news_api.php")
    fun fetchDetailsNews(@Query("id") id: Int): Call<NewsDetail>
    //fun fetchDetailsNews(@Query("id") id: Int): Call<NewsDetail>
}