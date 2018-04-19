package com.example.jakub.chaosgame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import processing.android.PFragment

class MainActivity : AppCompatActivity() {

    private var sketch = Sketch()
    private var working = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment  = PFragment(sketch)
        fragment.setView(sketch_layout, this)

        start_stop_button.setOnClickListener { toggleDrawing() }
        reset_button.setOnClickListener { reset() }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        sketch.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    public override fun onNewIntent(intent: Intent) {
        sketch.onNewIntent(intent)
    }

    private fun toggleButton()
    {
        if(working){
            start_stop_button.setBackgroundColor(resources.getColor(R.color.startStopButtonColorAlternative))
            start_stop_button.text = resources.getString(R.string.start_stop_stop)
        }
        else
        {
            start_stop_button.setBackgroundColor(resources.getColor(R.color.startStopButtonColor))
            start_stop_button.text = resources.getString(R.string.start_stop_start)
        }
    }

    private fun toggleDrawing ()
    {
        sketch.keyPressed()
        working = !working
        toggleButton()

    }

    private fun reset()
    {
        sketch.keyReleased()
        working = false
        toggleButton()
    }
}
