package me.lazmaid.newsdemo.presentation

import me.lazmaid.newsdemo.Injector
import me.lazmaid.newsdemo.data.model.News
import me.lazmaid.newsdemo.data.source.NewsDataSource
import rx.Observable

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

interface MainViewModel {
    fun loadNews(): Observable<List<News>>
}

class MainViewModelImpl(
        private val newsDataSource: NewsDataSource = Injector.provideNewsDataSource()) : MainViewModel {

    override fun loadNews(): Observable<List<News>> =
                newsDataSource.getNews()
                        .map {
                            it.sortedByDescending(News::publishedDateTime)
                        }

}