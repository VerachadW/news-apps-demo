package me.lazmaid.newsdemo.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

@RealmClass
open class News (
        @PrimaryKey
        open var title: String = "",
        open var description: String = "",
        @SerializedName("urlToImage")
        open var thumbnailUrl: String = "",
        @SerializedName("url")
        open var articleLink: String = "",
        @SerializedName("publishedAt")
        open var publishedDateTime: Date = Date()

): RealmModel
