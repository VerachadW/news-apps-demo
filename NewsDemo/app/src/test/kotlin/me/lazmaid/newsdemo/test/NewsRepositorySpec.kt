package me.lazmaid.newsdemo.test

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.lazmaid.newsdemo.data.model.News
import me.lazmaid.newsdemo.data.source.NewsLocalSource
import me.lazmaid.newsdemo.data.source.NewsNetworkSource
import me.lazmaid.newsdemo.data.source.NewsRepository
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import rx.Observable
import rx.observers.TestSubscriber

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

@RunWith(JUnitPlatform::class)
class NewsRepositorySpec : Spek({

    describe("NewsRepository") {
        val mockLocalSource = mock<NewsLocalSource>()
        val mockNetworkSource = mock<NewsNetworkSource>()
        val repository = NewsRepository(mockLocalSource, mockNetworkSource)
        val sample = listOf(News(title = "test"))
        whenever(mockLocalSource.getNews()).thenReturn(Observable.just(sample))
        whenever(mockNetworkSource.getNews()).thenReturn(Observable.just(sample))

        describe("getNews") {
            it("should execute localSource, then NetworkSource and lastly call saveNews in localSource") {
                val subscriber = TestSubscriber<List<News>>()
                repository.getNews().subscribe(subscriber)
                inOrder(mockLocalSource, mockNetworkSource) {
                    val captor = argumentCaptor<List<News>>()
                    verify(mockLocalSource).getNews()
                    verify(mockNetworkSource).getNews()
                    verify(mockLocalSource).saveNews(captor.capture())
                    assertThat(captor.firstValue, equalTo(sample))
                }
            }
        }

    }
})