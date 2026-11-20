package com.oudi.ios26

import android.app.Activity
import android.os.Bundle
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.*
import android.util.TypedValue

class MainActivity : Activity() {
    private val bg = Color.rgb(7, 17, 34)
    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        window.decorView.systemUiVisibility = 0

        val scroll = ScrollView(this).apply { setBackgroundColor(bg); isFillViewport = true }
        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(28))
        }
        scroll.addView(page)

        val brand = TextView(this).apply {
            text = "OUDI"
            setTextColor(Color.rgb(155, 220, 255))
            textSize = 13f
            letterSpacing = .28f
            gravity = Gravity.CENTER
        }
        page.addView(brand)
        val title = TextView(this).apply {
            text = "iOS 26  •  Liquid Glass"
            setTextColor(Color.WHITE)
            textSize = 27f
            gravity = Gravity.CENTER
            setPadding(0, dp(7), 0, dp(5))
        }
        page.addView(title)
        val intro = TextView(this).apply {
            text = "مجموعة أيقونات زجاجية مستوحاة من iPhone"
            setTextColor(Color.rgb(164, 190, 218))
            textSize = 14f
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, dp(22))
        }
        page.addView(intro)

        val count = TextView(this).apply {
            text = "ICON COLLECTION     •     24 ICONS"
            setTextColor(Color.rgb(132, 190, 231))
            textSize = 11f
            letterSpacing = .12f
            setPadding(dp(2), dp(4), 0, dp(14))
        }
        page.addView(count)

        val icons = listOf(
            "الهاتف" to "📞", "الرسائل" to "💬", "FaceTime" to "📹",
            "الصور" to "🌈", "الكاميرا" to "📷", "الطقس" to "🌤️",
            "Safari" to "🧭", "الموسيقى" to "🎵", "الإعدادات" to "⚙️",
            "التقويم" to "📅", "الساعة" to "⏱️", "الخرائط" to "🗺️",
            "البريد" to "✉️", "الملاحظات" to "📝", "التذكيرات" to "☑️",
            "App Store" to "🅰️", "الملفات" to "📁", "المحفظة" to "💳",
            "الصحة" to "❤️", "المنزل" to "🏠", "الطقس" to "🌦️",
            "الكتب" to "📚", "البودكاست" to "🎙️", "الحاسبة" to "🧮"
        )
        val grid = GridLayout(this).apply { columnCount = 3; useDefaultMargins = false }
        icons.forEachIndexed { index, item ->
            val cell = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(dp(4), dp(8), dp(4), dp(13))
                isClickable = true
                isFocusable = true
                setOnClickListener {
                    Toast.makeText(this@MainActivity, item.first + " — معاينة الأيقونة", Toast.LENGTH_SHORT).show()
                }
            }
            val icon = GlassIconView(this, item.second, index).apply {
                layoutParams = LinearLayout.LayoutParams(dp(76), dp(76))
            }
            val label = TextView(this).apply {
                text = item.first
                textSize = 12f
                setTextColor(Color.rgb(229, 238, 249))
                gravity = Gravity.CENTER
                setPadding(0, dp(7), 0, 0)
                maxLines = 1
            }
            cell.addView(icon)
            cell.addView(label)
            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(index % 3, 1f)
                rowSpec = GridLayout.spec(index / 3)
            }
            grid.addView(cell, params)
        }
        page.addView(grid)

        val note = TextView(this).apply {
            text = "OUDI  |  Liquid Glass collection\nمعاينة الأيقونات داخل التطبيق. تطبيقها كحزمة نظام على HONOR يحتاج صيغة ثيم متوافقة مع إصدار MagicOS."
            setTextColor(Color.rgb(132, 158, 188))
            textSize = 12f
            gravity = Gravity.CENTER
            setPadding(dp(12), dp(18), dp(12), dp(12))
        }
        page.addView(note)
        setContentView(scroll)
    }
}

private class GlassIconView(context: android.content.Context, private val glyph: String, private val index: Int) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val palettes = arrayOf(
        intArrayOf(0xff52c9ff.toInt(), 0xff1266d8.toInt()),
        intArrayOf(0xff61e6bd.toInt(), 0xff078a83.toInt()),
        intArrayOf(0xff9bdcff.toInt(), 0xff4769d9.toInt()),
        intArrayOf(0xffffa3c8.toInt(), 0xff7657d9.toInt()),
        intArrayOf(0xffb5c9dc.toInt(), 0xff536b89.toInt()),
        intArrayOf(0xffffd36c.toInt(), 0xffee873b.toInt())
    )
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        val p = palettes[index % palettes.size]
        val rect = RectF(2f, 2f, w - 2f, h - 2f)
        paint.shader = LinearGradient(0f, 0f, w, h, p[0], p[1], Shader.TileMode.CLAMP)
        canvas.drawRoundRect(rect, 22f, 22f, paint)
        paint.shader = null
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.4f
        paint.color = 0x99ffffff.toInt()
        canvas.drawRoundRect(rect, 22f, 22f, paint)
        paint.style = Paint.Style.FILL
        paint.shader = LinearGradient(0f, 0f, w, h * .75f, 0x66ffffff, 0x00ffffff, Shader.TileMode.CLAMP)
        canvas.drawRoundRect(RectF(5f, 5f, w - 5f, h * .52f), 19f, 19f, paint)
        paint.shader = null
        paint.color = 0x22ffffff
        canvas.drawOval(RectF(w * .13f, h * .06f, w * .86f, h * .32f), paint)
        paint.typeface = Typeface.create("sans-serif", Typeface.NORMAL)
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 36f
        paint.color = Color.WHITE
        paint.setShadowLayer(5f, 0f, 2f, 0x66000000)
        canvas.drawText(glyph, w / 2f, h * .66f, paint)
        paint.clearShadowLayer()
    }
}
