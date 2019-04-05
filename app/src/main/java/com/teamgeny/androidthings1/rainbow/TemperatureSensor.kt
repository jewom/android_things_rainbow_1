package com.teamgeny.androidthings1.rainbow

import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat


class TemperatureSensor {

    private var temperatureSensor: Bmx280? = null

    fun getTemperature() : Float {
        if (temperatureSensor == null)
            temperatureSensor = RainbowHat.openSensor()
        temperatureSensor?.temperatureOversampling = Bmx280.OVERSAMPLING_1X
        return temperatureSensor?.readTemperature() ?: 0f
    }

    fun clean() {
        temperatureSensor?.close()
    }

}