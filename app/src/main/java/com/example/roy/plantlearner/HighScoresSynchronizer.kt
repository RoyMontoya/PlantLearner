package com.example.roy.plantlearner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Roy on 3/27/18.
 */

class HighScoresSynchronizer : BroadcastReceiver() {

    var power :Boolean = false

    override fun onReceive(context: Context?, intent: Intent?) {
          if(intent?.action.equals(Intent.ACTION_POWER_CONNECTED)){
            power = true

        }else if(intent?.action.equals(Intent.ACTION_POWER_DISCONNECTED)){
            power = false
        }

        syncronize()
    }

    private fun syncronize() {
        if(power){

        }else{

        }
    }

}