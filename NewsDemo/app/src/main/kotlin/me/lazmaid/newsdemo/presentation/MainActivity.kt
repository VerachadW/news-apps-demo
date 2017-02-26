package me.lazmaid.newsdemo.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.github.kittinunf.reactiveandroid.rx.plusAssign
import com.github.kittinunf.reactiveandroid.scheduler.AndroidThreadScheduler
import com.github.kittinunf.reactiveandroid.support.v4.widget.rx_refresh
import com.github.kittinunf.reactiveandroid.support.v7.widget.rx_itemsWith
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_news.view.*
import me.lazmaid.newsdemo.R
import me.lazmaid.newsdemo.data.model.News
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

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
            .doOnError {
                swlNews.isRefreshing = false
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val swipeRefreshObservable = swlNews.rx_refresh().flatMap { newsObservable }

        rvNews.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)

            subscriptions += rx_itemsWith(swipeRefreshObservable.mergeWith(newsObservable),
                    onCreateViewHolder = { parent, type ->
                        val view = layoutInflater.inflate(R.layout.item_news, parent, false)
                        NewsViewHolder(view)
                    }, onBindViewHolder = { holder, type, item ->
                holder.bindView(item)
            })
        }
    }

    override fun onDestroy() {
        subscriptions.clear()
        super.onDestroy()
    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(item: News) {
            itemView.apply {
                tvTitle.text = item.title
                tvDescription.text = item.description
                Glide.with(itemView.context)
                        .load(item.thumbnailUrl)
                        .fitCenter()
                        .into(ivNewsImage)
                tvFullArticle.setOnClickListener {
                    //TODO: Open Browser
                }
            }
        }
    }

}

