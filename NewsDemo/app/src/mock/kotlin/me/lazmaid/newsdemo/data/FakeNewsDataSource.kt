package me.lazmaid.newsdemo.data.source

import me.lazmaid.newsdemo.data.model.News
import me.lazmaid.newsdemo.data.source.NewsDataSource
import rx.Observable

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

class FakeNewsDataSource : NewsDataSource {

    val newsList = listOf(
            News(title = "Test 1", description = "description 1234", thumbnailUrl = "http://www.drodd.com/images12/pitbull-puppies14.jpg"),
            News(title = "Test 2", description = "description 1234", thumbnailUrl = "http://www.drodd.com/images12/pitbull-puppies14.jpg"),
            News(title = "Test 3", description = "description 1234", thumbnailUrl = "http://www.drodd.com/images12/pitbull-puppies14.jpg"))

    override fun getNews(): Observable<List<News>> = Observable.just(newsList)

}