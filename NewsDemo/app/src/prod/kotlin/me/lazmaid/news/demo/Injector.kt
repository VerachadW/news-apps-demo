package me.lazmaid.news.demo

import me.lazmaid.newsdemo.data.source.NewsRepository

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

object Injector {

    fun provideNewsDataSource() = NewsRepository()
}