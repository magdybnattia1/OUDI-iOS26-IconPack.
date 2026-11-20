package com.oudi.ios26

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(7, 20, 38)
        window.navigationBarColor = Color.rgb(7, 20, 38)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
            setBackgroundColor(Color.rgb(7, 20, 38))
        }
        val title = TextView(this).apply {
            text = "OUDI iOS 26"
            textSize = 30f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }
        val subtitle = TextView(this).apply {
            text = "Liquid Glass Icon Pack\\nYour icons, reimagined."
            textSize = 16f
            setTextColor(Color.rgb(168, 232, 255))
            gravity = Gravity.CENTER
            setPadding(0, 18, 0, 0)
        }
        root.addView(title)
        root.addView(subtitle)
        setContentView(root)
    }
}
