package me.lazmaid.newsdemo.test

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.lazmaid.newsdemo.data.model.News
import me.lazmaid.newsdemo.data.source.NewsDataSource
import me.lazmaid.newsdemo.presentation.MainViewModelImpl
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import rx.Observable
import rx.observers.TestSubscriber
import java.util.*

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

@RunWith(JUnitPlatform::class)
class MainViewModelSpec : Spek({
    describe("MainViewModel") {

        val news1 = News(title="Test1", publishedDateTime = Date(0))
        val news2 = News(title="Test2", publishedDateTime = Date(1))
        val news3 = News(title="Test3", publishedDateTime = Date(2))

        val sampleNews = listOf(news1, news2, news3)

        val mockDataSource = mock<NewsDataSource>()
        whenever(mockDataSource.getNews()).thenReturn(Observable.just(sampleNews))
        val viewModel = MainViewModelImpl(mockDataSource)

        describe("loadNews") {
            it("should return a list of news sorted by published date") {
                val subscriber = TestSubscriber<List<News>>()
                viewModel.loadNews().subscribe(subscriber)
                val actual = subscriber.onNextEvents[0]
                subscriber.assertNoErrors()
                assertThat(actual[0].title, equalTo(news3.title))
                assertThat(actual[2].title, equalTo(news1.title))
            }
        }

    }
})

