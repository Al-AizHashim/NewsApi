package alaiz.hashim.newsapi.api

import alaiz.hashim.newsapi.NewsItem
import com.google.gson.annotations.SerializedName

data class NewsDetail (
    @SerializedName("newsDetails")
    var newsDetailsItems: NewsItem
)