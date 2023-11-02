package com.example.parsing

import android.content.IntentSender.OnFinished
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


var array: JSONArray? = null
val recyclerView: RecyclerView? = null
val viewItems: ArrayList<CatalogDataClass> = ArrayList()


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GetTask().execute(JSONObject())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val timer = object: CountDownTimer(3000,1000){
            override fun onTick(millisUntilFinished: Long){}
            override fun onFinish() {
                val adapterT = CatalogAdapterT(viewItems)
                recyclerView.adapter = adapterT
            }
        }
        timer.start()
    }
    inner class GetTask : AsyncTask<JSONObject?, Void?, String?>() {

        override fun doInBackground(vararg params: JSONObject?): String? {
            try {

// Присваиваем путь
                    val url = URL("https://api.restful-api.dev/objects")
                    var connection = url.openConnection() as HttpURLConnection
                    // Выбираем метод GET для запроса
                    connection.requestMethod = "GET"
                    connection.readTimeout = 10000
                    connection.connect()
                    // Полученный результат разбиваем с помощью байтовых потоков
                    var stream = connection!!.inputStream
                    var reader = BufferedReader(InputStreamReader(stream))
                    val buf = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        buf.append(line).append("\n")
                    }

                array = JSONArray(buf.toString())
                addItemsFromJSON()

                return buf.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    }

    private fun addItemsFromJSON() {
        try {
// Заполняем Модель спаршенными данными
            for (i in 0 until array!!.length()) {
                val itemObj = array!!.getJSONObject(i)
                val id = itemObj.getString("id")
                val name = itemObj.getString("name")
                val catalogs = CatalogDataClass(id, name)
                viewItems.add(catalogs)
            }
        } catch (e: JSONException) {
        }
    }
}
