package me.lazmaid.newsdemo.presentation

import me.lazmaid.news.demo.data.Injector
import me.lazmaid.newsdemo.data.model.News
import me.lazmaid.newsdemo.data.source.NewsDataSource
import me.lazmaid.newsdemo.data.source.NewsRepository
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

interface MainViewModel {
    fun loadNews(): Observable<List<News>>
}

class MainViewModelImpl(
        private val newsDataSource: NewsDataSource = Injector.provideNewsDataSource()) : MainViewModel {

    override fun loadNews(): Observable<List<News>> = newsDataSource.getNews()
            .map {
                it.sortedByDescending(News::publishedDateTime)
            }

}