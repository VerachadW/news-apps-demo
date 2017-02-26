package me.lazmaid.newsdemo

import android.app.Application
import io.realm.Realm

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

class NewsDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}