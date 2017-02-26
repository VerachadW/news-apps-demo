package me.lazmaid.newsdemo.data.source

import me.lazmaid.newsdemo.data.model.News
import rx.Observable

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

interface NewsDataSource {
    fun getNews(): Observable<List<News>>
}

interface NewsNetworkSource : NewsDataSource
interface NewsLocalSource: NewsDataSource {
    fun saveNews(data: List<News>)
}