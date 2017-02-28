package me.lazmaid.newsdemo.presentation

import android.content.Intent
import android.net.Uri
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*
import me.lazmaid.newsdemo.R
import me.lazmaid.newsdemo.data.model.News

/**
 * Created by VerachadW on 2/28/2017 AD.
 */

class NewsAdapter : RecyclerView.Adapter<NewsViewHolder>() {

    private val newsList = arrayListOf<News>()

    fun updateNewsList(list: List<News>) {
        val result = DiffUtil.calculateDiff(NewsDiffCallback(newsList, list))

        newsList.clear()
        newsList.addAll(list)
        result.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindView(newsList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

}

class NewsDiffCallback(val oldList: List<News>, val newList: List<News>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].description == newList[newItemPosition].description
                && oldList[oldItemPosition].articleLink == newList[newItemPosition].articleLink
                && oldList[oldItemPosition].thumbnailUrl == newList[newItemPosition].thumbnailUrl
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

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
                val webIntent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(item.articleLink)
                }
                itemView.context.startActivity(webIntent)
            }
        }
    }
}