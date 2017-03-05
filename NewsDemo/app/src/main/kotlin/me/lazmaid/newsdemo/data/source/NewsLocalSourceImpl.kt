package me.lazmaid.newsdemo.data.source

import android.util.Log
import io.realm.Realm
import me.lazmaid.newsdemo.data.model.News
import rx.Observable

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

class NewsLocalSourceImpl : NewsLocalSource {

    override fun getNews(): Observable<List<News>> {
        val newsList = arrayListOf<News>()
        Realm.getDefaultInstance().use {
            val items = it.where(News::class.java).findAll()
            newsList.addAll(it.copyFromRealm(items))
        }
        Log.d("Local", "getNews")
        return Observable.just(newsList)
    }

    override fun saveNews(data: List<News>) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.copyToRealmOrUpdate(data)
            }
        }
    }

}
