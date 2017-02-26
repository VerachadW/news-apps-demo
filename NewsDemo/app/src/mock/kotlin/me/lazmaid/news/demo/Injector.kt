package me.lazmaid.news.demo.data

import me.lazmaid.news.demo.data.source.FakeNewsDataSource

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

object Injector {

    fun provideNewsDataSource() = FakeNewsDataSource()

}
