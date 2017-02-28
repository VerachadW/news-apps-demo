package me.lazmaid.newsdemo.test.data.source

import android.support.test.runner.AndroidJUnit4
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import io.realm.Realm
import io.realm.RealmConfiguration
import me.lazmaid.newsdemo.data.model.News
import me.lazmaid.newsdemo.data.source.NewsLocalSource
import me.lazmaid.newsdemo.data.source.NewsLocalSourceImpl
import org.junit.Test
import rx.observers.TestSubscriber

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

@org.junit.runner.RunWith(AndroidJUnit4::class)
class NewsLocalSourceTest {

    val dataSource: NewsLocalSource = NewsLocalSourceImpl()
    val realmConfig: RealmConfiguration = RealmConfiguration.Builder()
                .name("test.realm")
                .deleteRealmIfMigrationNeeded()
                .build()

    @org.junit.Before
    fun setUp() {
        io.realm.Realm.setDefaultConfiguration(realmConfig)
        io.realm.Realm.getDefaultInstance().use {
            it.executeTransaction(Realm::deleteAll)
        }
    }

    @Test
    fun NewsLocalSource_GetNews_ShouldGetSameList() {
        val subscriber = TestSubscriber<List<News>>()
        val expected = listOf(News("Test1"), News("Test2"))
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.copyToRealmOrUpdate(expected)
            }
        }
        dataSource.getNews().subscribe(subscriber)
        assertThat(subscriber.onNextEvents[0], hasSize(equalTo(expected.size)))
        assertThat(subscriber.onNextEvents[0][0].title, equalTo(expected[0].title))
    }

    @Test
    fun NewsLocalSource_SaveNews_ShouldSaveSameList() {
        val expected = listOf(News("Test1"), News("Test2"))
        dataSource.saveNews(expected)
        Realm.getDefaultInstance().use {
            val actual = it.where(News::class.java).findAll()
            assertThat(actual, hasSize(equalTo(expected.size)))
            assertThat(actual[0].title, equalTo(expected[0].title))
        }
    }

}