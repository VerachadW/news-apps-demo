package me.lazmaid.newsdemo.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.github.kittinunf.reactiveandroid.rx.plusAssign
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import com.github.kittinunf.reactiveandroid.support.v4.widget.rx_refresh
import kotlinx.android.synthetic.main.activity_main.*
import me.lazmaid.newsdemo.R
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.net.UnknownHostException

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModelImpl()
    private val subscriptions = CompositeSubscription()

    private val newsObservable = viewModel.loadNews().subscribeOn(Schedulers.io())
            .observeOn(AndroidThreadScheduler.main)
            .doOnSubscribe {
                swlNews.isRefreshing = true
            }
            .doOnCompleted {
                swlNews.isRefreshing = false
            }

    private val newsAdapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvNews.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = newsAdapter
        }

        val swipeRefreshObservable = swlNews.rx_refresh().flatMap { newsObservable }
        subscriptions += swipeRefreshObservable.mergeWith(newsObservable).subscribe({
                    it.fold({
                        newsAdapter.updateNewsList(it)
                        rvNews.smoothScrollToPosition(0)
                    }, {
                        if (it is UnknownHostException) {
                            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                    })
                })
    }

    override fun onDestroy() {
        subscriptions.clear()
        super.onDestroy()
    }


}

