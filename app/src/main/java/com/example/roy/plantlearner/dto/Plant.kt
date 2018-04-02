package com.example.roy.plantlearner.dto

/**
 * Created by Roy on 3/14/18.
 */

class Plant(var guid: Int, var genus: String, var species: String,
            var cultivar: String, var common: String,
            var height: Int = 0, var photoName:String  = ""){
    constructor(): this(0,"","","","")

    override fun toString(): String {
        return "$genus $species $cultivar $common"
    }
}