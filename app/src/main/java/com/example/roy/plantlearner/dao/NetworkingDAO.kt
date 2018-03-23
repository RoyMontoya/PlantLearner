package com.example.roy.plantlearner.dao

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Roy on 3/23/18.
 */

class NetworkingDAO {

    @Throws(IOException::class)
    fun request(uri: String): String {
        val sb = StringBuilder()

        val url = URL(uri)
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val bis = BufferedInputStream(urlConnection.inputStream)
            val bin = BufferedReader(InputStreamReader(bis))

            var inputLine: String
            inputLine = bin.readLine()
            while (inputLine != null) {
                sb.append(inputLine)
                inputLine = bin.readLine()
            }
        } finally {
            urlConnection.disconnect()
        }
        return sb.toString()
    }
}