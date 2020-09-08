package com.example.brightness_control

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    var seekBar: SeekBar? = null
    var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        seekBar = findViewById(R.id.brihtnessControl)
        textView = findViewById(R.id.brihtnessControlText)
        val cBrightness = Settings.System.getInt(
            contentResolver,
            Settings.System.SCREEN_BRIGHTNESS, 0
        )
        textView?.setText("$cBrightness/255")
        seekBar?.setProgress(cBrightness)
        seekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            @RequiresApi(api = Build.VERSION_CODES.M)
            override fun onProgressChanged(
                seekBar: SeekBar,
                i: Int,
                b: Boolean
            ) {
                val context = applicationContext
                val canWrite = Settings.System.canWrite(context)
                if (canWrite) {
                    val sBrightness = i * 255 / 255
                    textView?.setText("$sBrightness/255")
                    Settings.System.putInt(
                        context.contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                    )
                    Settings.System.putInt(
                        context.contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS, sBrightness
                    )
                } else {
                    val intent =
                        Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                    context.startActivity(intent)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}