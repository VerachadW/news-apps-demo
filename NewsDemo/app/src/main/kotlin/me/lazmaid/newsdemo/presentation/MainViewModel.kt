package me.lazmaid.newsdemo.presentation

import com.github.kittinunf.result.Result
import me.lazmaid.newsdemo.Injector
import me.lazmaid.newsdemo.data.model.News
import me.lazmaid.newsdemo.data.source.NewsDataSource
import rx.Observable

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

interface MainViewModel {
    fun loadNews(): Observable<Result<List<News>, Exception>>
}

class MainViewModelImpl(
        private val newsDataSource: NewsDataSource = Injector.provideNewsDataSource()) : MainViewModel {

    override fun loadNews(): Observable<Result<List<News>, Exception>> =
            newsDataSource.getNews()
            .map {
                Result.of(it.sortedByDescending(News::publishedDateTime))
            }.onErrorReturn {
                Result.error(it as Exception)
            }

}