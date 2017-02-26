package me.lazmaid.newsdemo.data.model

import io.realm.RealmModel
import java.util.*

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

open class News (

    open var title: String = "",
    open var description: String = "",
    open var thumbnailUrl: String = "",
    open var articleLinks: String = "",
    open var publishedDateTime: Date = Date()

): RealmModel
