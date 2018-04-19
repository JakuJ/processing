package com.example.jakub.multibodysim

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_menu.*
import processing.android.PFragment

class MenuActivity : AppCompatActivity() {

    private val sketch = BackgroundSketch()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        window.decorView.systemUiVisibility = uiOptions
        setContentView(R.layout.activity_menu)

        val fragment  = PFragment(sketch)
        fragment.setView(background_sketch_layout, this)

        start_button.setOnClickListener { startGame() }
        exit_button.setOnClickListener { exitApplication() }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        sketch.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    public override fun onNewIntent(intent: Intent) {
        sketch.onNewIntent(intent)
    }

    private fun startGame() {
        startActivity(Intent(this@MenuActivity, GameActivity::class.java))
        finish()
    }

    private fun exitApplication() {
        finish()
    }

}
