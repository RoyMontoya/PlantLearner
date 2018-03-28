package com.example.roy.plantlearner

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.roy.plantlearner.dto.Plant
import com.example.roy.plantlearner.service.PlantService
import kotlinx.android.synthetic.main.activity_flash_card.*
import java.io.IOException

class FlashCardActivity : AppCompatActivity() {

    val CAMARA_REQUEST = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card)
    }

    fun onButton0Click(v: View) {
        var randomNumber = (Math.random() * 4).toInt() + 1

        when(randomNumber){
            1 -> button.setBackgroundColor(Color.GREEN)
            2 -> button1.setBackgroundColor(Color.GREEN)
            3 -> button2.setBackgroundColor(Color.GREEN)
            4 -> button3.setBackgroundColor(Color.GREEN)
        }
    }

    fun onButton1Click(v: View) {
        button.setBackgroundColor(Color.LTGRAY)
        button1.setBackgroundColor(Color.LTGRAY)
        button2.setBackgroundColor(Color.LTGRAY)
        button3.setBackgroundColor(Color.LTGRAY)
    }

    fun onButton2Click(v: View) {
        var getPlantsActivity = getPlantsActivity()
        getPlantsActivity.execute("1")
    }


    fun onClickButton3(v: View) {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMARA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMARA_REQUEST && resultCode == Activity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap

            bitmap?.let { imageSwitcher.setImageBitmap(bitmap) }
        }
    }

    private fun readBitmap(uri: Uri?): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }


    inner class getPlantsActivity : AsyncTask<String, Int, List<Plant>?>() {

        override fun doInBackground(vararg search: String?): List<Plant>? {
            val difficulty = search[0]

            return PlantService().parsePlantsFromJson(difficulty)
        }

        override fun onPostExecute(result: List<Plant>?) {
            super.onPostExecute(result)

        }

    }


}
