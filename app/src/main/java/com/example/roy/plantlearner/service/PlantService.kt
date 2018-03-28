package com.example.roy.plantlearner.service

import com.example.roy.plantlearner.dao.NetworkingDAO
import com.example.roy.plantlearner.dto.Plant
import org.json.JSONObject

/**
 * Created by Roy on 3/23/18.
 */

class PlantService {

    fun parsePlantsFromJson(difficulty: String?): List<Plant>? {
        var allPlants = ArrayList<Plant>()

        val rawJSON = JSONObject(NetworkingDAO().request("http://plantplaces.com/perl/mobile/flashcard.pl"))
        val JSONArray = rawJSON.getJSONArray("values")

        var i = 0

        while (i < JSONArray.length()) {
            var plant = Plant()

            var plantJson = JSONArray.getJSONObject(i)
            with(plantJson) {
                plant.guid = getInt("plant")
                plant.genus = getString("genus")
                plant.species = getString("species")
                plant.cultivar = getString("cultivar")
                plant.common = getString("common")
            }


            allPlants.add(plant)

            i++
        }

        return allPlants
    }
}