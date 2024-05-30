package com.example.compassapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() ,SensorEventListener {

    var sensor : Sensor ?= null
    var sensorManager : SensorManager ?= null
    lateinit var compassImage : ImageView
    lateinit var  rotationTV: TextView
    //keep  track of degree change
    var currentDegree = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager= getSystemService(Context.SENSOR_SERVICE ) as SensorManager
        sensor= sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)

        compassImage = findViewById(R.id.imageView)
        rotationTV = findViewById(R.id.textView)

    }

    override fun onSensorChanged(event : SensorEvent?) {// most important function
        var degree = Math.round(event!!.values[0])
        rotationTV.text = degree.toString() + "Degrees"
        var rotatationAnimation = RotateAnimation(currentDegree,(-degree).toFloat(),
            Animation.RELATIVE_TO_SELF ,0.5f , Animation.RELATIVE_TO_SELF,0.5f)
        rotatationAnimation.duration = 210
        rotatationAnimation.fillAfter= true


        compassImage.startAnimation(rotatationAnimation)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
}