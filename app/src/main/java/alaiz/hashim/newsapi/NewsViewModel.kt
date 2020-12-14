package alaiz.hashim.newsapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class NewsViewModel : ViewModel() {
    val newsDetailsLiveData = MutableLiveData<Int>()
    val newsItemLiveData: LiveData<List<NewsItem>>
    val newsSportItemLiveData: LiveData<List<NewsItem>>
    val newsOddityItemLiveData: LiveData<List<NewsItem>>
    //val newsDetailsItemLiveData:LiveData<List<NewsItem>>

    init {
        newsItemLiveData = NewsFetchr().fetchNews()
        newsSportItemLiveData = NewsFetchr().fetchSportNews()
        newsOddityItemLiveData = NewsFetchr().fetchOddityNews()
        //newsDetailsItemLiveData=NewsFetchr().fetchDetailsNews(1)
    }

    var newsDetailsItemLiveData: LiveData<NewsItem> =
        Transformations.switchMap(newsDetailsLiveData) { id ->
            NewsFetchr().fetchDetailsNews(id)
        }

    fun loadNewsDetails(id: Int) {
        newsDetailsLiveData.value = id
    }
}