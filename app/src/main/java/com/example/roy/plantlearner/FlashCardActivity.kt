package com.example.roy.plantlearner

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.roy.plantlearner.dto.Plant
import com.example.roy.plantlearner.service.PlantService

class FlashCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card)
    }

    fun onButton2Click(v: View) {
        var getPlantsActivity = getPlantsActivity()
        getPlantsActivity.execute("1")
    }


    fun onClickButton3(v: View) {

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
