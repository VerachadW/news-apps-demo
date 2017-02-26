package me.lazmaid.newsdemo.data.source

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Deserializable
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.rx.rx_object
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import me.lazmaid.newsdemo.data.model.News
import rx.Observable
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.lang.reflect.Type

/**
 * Created by VerachadW on 2/26/2017 AD.
 */

class NewsNetworkSourceImpl : NewsNetworkSource {

    override fun getNews(): Observable<List<News>> {
        val params = mapOf("source" to "techcrunch",
                "apiKey" to "55acf70fec4040febd3bb8262a212024",
                "sortBy" to "latest")
        return Fuel.get("https://newsapi.org/v1/articles", parameters = params.toList())
                .rx_object(NewsDeserializer())
    }

    class NewsDeserializer : Deserializable<List<News>> {

        override fun deserialize(response: Response): List<News> {
            val gson = Gson()
            val jsonReader = gson.newJsonReader(InputStreamReader(ByteArrayInputStream(response.data)))
            var data = listOf<News>()
            jsonReader.beginObject()
            jsonReader.nextName()
            val status = jsonReader.nextString()
            while (jsonReader.hasNext()) {
                if (status == "ok" && jsonReader.nextName() == "articles") {
                    data = gson.fromReader<List<News>>(jsonReader)
                } else if (status == "error" && jsonReader.nextName() == "message") {

                } else {
                    jsonReader.skipValue()
                }
            }
            jsonReader.endObject()
            return data
        }

        inline fun <reified T> typeToken(): Type = object : TypeToken<T>() {}.type
        inline fun <reified T> Gson.fromReader(reader: JsonReader) = this.fromJson<T>(reader, typeToken<T>())

    }

}
