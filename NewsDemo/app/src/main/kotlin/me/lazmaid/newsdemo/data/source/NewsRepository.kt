package me.lazmaid.newsdemo.data.source

import me.lazmaid.newsdemo.data.model.News
import rx.Observable

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

class NewsRepository(val localSource: NewsLocalSource = NewsLocalSourceImpl(),
                     val networkSource: NewsNetworkSource = NewsNetworkSourceImpl()) : NewsDataSource {

    override fun getNews(): Observable<List<News>> =
        localSource.getNews().concatWith(networkSource.getNews().doOnNext {
            localSource.saveNews(it)
        })

}