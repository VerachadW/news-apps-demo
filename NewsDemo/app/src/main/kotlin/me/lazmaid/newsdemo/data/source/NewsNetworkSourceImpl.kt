package me.lazmaid.newsdemo.data.source

import me.lazmaid.newsdemo.data.model.News
import rx.Observable

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

class NewsNetworkSourceImpl : NewsNetworkSource {

    override fun getNews(): Observable<List<News>> {
        return Observable.just(listOf())
    }

}
