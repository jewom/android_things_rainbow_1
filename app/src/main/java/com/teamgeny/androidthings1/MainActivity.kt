package com.teamgeny.androidthings1

import android.app.Activity
import android.os.Bundle
import java.io.IOException
import android.util.Log

import com.google.android.things.pio.PeripheralManager
import com.teamgeny.androidthings1.rainbow.*

class MainActivity : Activity() {

    private val led = Led()
    private val buttons = HatButton()
    private val piezo = Piezo()
    private val temperatureSensor = TemperatureSensor()
    private val topDisplay = TopDisplay()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val manager = PeripheralManager.getInstance()
        Log.d("test", "Available GPIO: " + manager.gpioList)

        runSafeIO {

            buttons.setOnClickListener(HatButton.ID.A) { _, _, pressed ->
                led.turn(Led.ID.RED, pressed)
            }

            buttons.setOnClickListener(HatButton.ID.B) { _, _, pressed ->
                led.turn(Led.ID.GREEN, pressed)
            }

            buttons.setOnClickListener(HatButton.ID.C) { _, _, pressed ->
                led.turn(Led.ID.BLUE, pressed)
            }

            led.blink(Led.ID.STRIP)
            piezo.play(Piezo.Note.DO, 5000)
            val temperature = temperatureSensor.getTemperature()
            topDisplay.displayMessage(temperature.toString())
        }
    }

    override fun onStop() {
        super.onStop()
        led.clean()
        buttons.clean()
        piezo.clean()
        temperatureSensor.clean()
        topDisplay.clean()
    }

    private fun runSafeIO(ioOperation: () -> Unit) {
        try {
            ioOperation()
        } catch (error: IOException) {
            Log.e(error.toString(), "IO Error")
        }
    }


}
