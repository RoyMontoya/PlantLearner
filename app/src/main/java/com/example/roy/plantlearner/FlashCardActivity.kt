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
import com.example.roy.plantlearner.dao.NetworkingDAO
import com.example.roy.plantlearner.dto.Plant
import com.example.roy.plantlearner.service.PlantService
import kotlinx.android.synthetic.main.activity_flash_card.*
import java.io.IOException

class FlashCardActivity : AppCompatActivity() {

    var plants: List<Plant> = ArrayList<Plant>()
    val CAMARA_REQUEST = 10
    var correctAnswer = 0
    var answeredCorrectly = 0
    var answeredInCorrectly = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card)

        getPlantsTask().execute("")
        button.setBackgroundColor(Color.LTGRAY)
        button1.setBackgroundColor(Color.LTGRAY)
        button2.setBackgroundColor(Color.LTGRAY)
        button3.setBackgroundColor(Color.LTGRAY)
    }

    fun onButton0Click(v: View) = evaluate(0)


    fun onButton1Click(v: View) = evaluate(1)

    fun onButton2Click(v: View) = evaluate(2)

    fun onClickButton3(v: View) = evaluate(3)


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


    inner class getPlantsTask : AsyncTask<String, Int, List<Plant>?>() {

        override fun doInBackground(vararg search: String?): List<Plant>? {
            val difficulty = search[0]

            return PlantService().parsePlantsFromJson(difficulty)
        }

        override fun onPostExecute(result: List<Plant>?) {
            super.onPostExecute(result)
            if (result != null && result.size > 3) {
                button.text = result.get(0).toString()
                button1.text = result.get(1).toString()
                button2.text = result.get(2).toString()
                button3.text = result.get(3).toString()
                correctAnswer = (Math.random() * 4).toInt()
            }

            getPhotoTask().execute(result.get(correctAnswer).photoName)

            plants = result!!
        }

    }

    private fun evaluate(answer: Int) {
        when (correctAnswer) {
            0 -> button.setBackgroundColor(Color.GREEN)
            1 -> button1.setBackgroundColor(Color.GREEN)
            2 -> button2.setBackgroundColor(Color.GREEN)
            3 -> button3.setBackgroundColor(Color.GREEN)
        }
        if(answer == correctAnswer){
            tv_status.text = "Correct"
            answeredCorrectly++
            text_correct_answer.text = "$answeredCorrectly"
        } else {
            val correct = plants.get(correctAnswer).toString()
            tv_status.text = "The correct answer is: $correct"
            answeredInCorrectly++
            text_wrong_answer.text = "$answeredInCorrectly"
        }
    }

    inner class getPhotoTask : AsyncTask<String, Int, Bitmap>() {

        override fun doInBackground(vararg picture: String?): Bitmap? {
            return NetworkingDAO().populatePicture(picture[0])
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            imageSwitcher.setImageBitmap(result)
        }

    }


}
