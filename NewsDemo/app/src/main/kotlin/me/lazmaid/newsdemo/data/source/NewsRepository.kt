package me.lazmaid.newsdemo.data.source

import com.github.kittinunf.fuel.core.FuelError
import me.lazmaid.newsdemo.data.model.News
import rx.Observable

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

class NewsRepository(private val localSource: NewsLocalSource = NewsLocalSourceImpl(),
                     private val networkSource: NewsNetworkSource = NewsNetworkSourceImpl()) : NewsDataSource {

    override fun getNews(): Observable<List<News>> =
            localSource.getNews().mergeWith(networkSource.getNews()
                    .map {
                        localSource.saveNews(it)
                    }.flatMap {
                        localSource.getNews()
                    }.onErrorResumeNext {
                        Observable.error((it as FuelError).exception)
                    })
}