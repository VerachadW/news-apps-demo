package me.lazmaid.newsdemo.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.github.kittinunf.reactiveandroid.support.v7.widget.rx_itemsWith
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_news.view.*
import me.lazmaid.newsdemo.R

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModelImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvNews.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            rx_itemsWith(viewModel.loadNews(), onCreateViewHolder = { parent, type ->
                val view = layoutInflater.inflate(R.layout.item_news, parent, false)
                NewsViewHolder(view)
            }, onBindViewHolder = { holder, type, item ->
                holder.itemView.apply {
                    tvTitle.text = item.title
                    tvDescription.text = item.description
                    Glide.with(this@MainActivity)
                            .load(item.thumbnailUrl)
                            .placeholder(R.drawable.placeholder)
                            .fitCenter().into(ivNewsImage)
                }
            })
        }
    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view)

}

