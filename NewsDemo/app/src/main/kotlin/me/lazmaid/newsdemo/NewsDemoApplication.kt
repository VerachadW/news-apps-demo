package me.lazmaid.newsdemo

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

class NewsDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("demo.realm")
                .build()
        Realm.setDefaultConfiguration(config)
    }
}