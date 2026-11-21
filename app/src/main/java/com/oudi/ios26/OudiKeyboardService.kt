package com.oudi.ios26

import android.inputmethodservice.InputMethodService
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.LinearLayout
import android.widget.TextView

class OudiKeyboardService : InputMethodService() {
    private var arabic = false
    private var shifted = false
    private var symbols = false
    private val bg = Color.rgb(211, 216, 224)
    private val key = Color.rgb(250, 251, 253)
    private val dark = Color.rgb(48, 54, 64)
    private val blue = Color.rgb(125, 174, 245)
    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()

    override fun onCreateInputView(): View {
        return buildKeyboard()
    }

    private fun buildKeyboard(): View {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(4), dp(7), dp(4), dp(9))
            background = GradientDrawable().apply {
                setColor(bg)
                cornerRadii = floatArrayOf(dp(18).toFloat(),dp(18).toFloat(),dp(18).toFloat(),dp(18).toFloat(),0f,0f,0f,0f)
            }
        }
        val suggestions = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, dp(7))
        }
        listOf("✧", "OUDI", "⋯").forEachIndexed { i, s ->
            val t = TextView(this).apply {
                text = s; gravity = Gravity.CENTER; textSize = if(i==1) 13f else 19f
                setTextColor(if(i==1) Color.rgb(85,105,137) else Color.GRAY)
                typeface = Typeface.DEFAULT_BOLD
            }
            suggestions.addView(t, LinearLayout.LayoutParams(0, dp(28), 1f))
        }
        root.addView(suggestions)
        val rows = if (symbols) {
            listOf(listOf("1","2","3","4","5","6","7","8","9","0"),
                listOf("@","#","$","%","&","-","+","(",")","/"),
                listOf("=", "*", "\"", "'", ":", ";", "!", "?", "⌫"))
        } else if (arabic) {
            listOf(listOf("ض","ص","ث","ق","ف","غ","ع","ه","خ","ح","ج"),
                listOf("ش","س","ي","ب","ل","ا","ت","ن","م","ك"),
                listOf("ئ","ء","ؤ","ر","لا","ى","ة","و","ز","ظ"))
        } else {
            val top = if(shifted) "QWERTYUIOP" else "qwertyuiop"
            val mid = if(shifted) "ASDFGHJKL" else "asdfghjkl"
            val low = if(shifted) "ZXCVBNM" else "zxcvbnm"
            listOf(top.map { it.toString() }, mid.map { it.toString() }, low.map { it.toString() })
        }
        rows.forEachIndexed { idx, row ->
            val line = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
                setPadding(0, dp(3), 0, dp(3))
            }
            if(idx==2 && !symbols && !arabic) addKey(line, "⇧", .13f, blue, 20f) { shifted = !shifted; refresh() }
            row.forEach { label ->
                val wide = label == "⌫"
                addKey(line, label, if(wide) .12f else 1f, if(wide) blue else key, 21f) { handle(label) }
            }
            if(idx==2 && !symbols && !arabic) addKey(line, "⌫", .13f, blue, 20f) { delete() }
            root.addView(line, LinearLayout.LayoutParams(-1, dp(48)))
        }
        val bottom = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(0, dp(3), 0, 0)
        }
        addKey(bottom, "🌐", .14f, blue, 18f) { arabic = !arabic; symbols = false; refresh() }
        addKey(bottom, "123", .16f, blue, 14f) { symbols = !symbols; refresh() }
        addKey(bottom, "😊", .14f, blue, 18f) { currentInputConnection?.commitText("😊",1) }
        addKey(bottom, if(arabic) "مسافة" else "space", .42f, key, 14f) { currentInputConnection?.commitText(" ",1) }
        addKey(bottom, "،", .10f, key, 20f) { currentInputConnection?.commitText(if(arabic) "،" else ",",1) }
        addKey(bottom, "↵", .14f, blue, 20f) { enter() }
        root.addView(bottom, LinearLayout.LayoutParams(-1, dp(49)))
        return root
    }

    private fun addKey(row: LinearLayout, label: String, weight: Float, color: Int, size: Float, action: () -> Unit) {
        val v = TextView(this).apply {
            text = label
            textSize = size
            gravity = Gravity.CENTER
            setTextColor(dark)
            typeface = Typeface.create("sans-serif", Typeface.NORMAL)
            background = GradientDrawable().apply {
                setColor(color)
                cornerRadius = dp(7).toFloat()
                setStroke(dp(1), 0x22FFFFFF)
            }
            elevation = dp(1).toFloat()
            isClickable = true
            isFocusable = true
            setOnClickListener { action() }
        }
        val p = LinearLayout.LayoutParams(0, dp(42), weight).apply {
            setMargins(dp(2), 0, dp(2), 0)
        }
        row.addView(v, p)
    }

    private fun handle(label: String) {
        when(label) {
            "⌫" -> delete()
            else -> currentInputConnection?.commitText(label, 1)
        }
    }
    private fun delete() {
        val ic = currentInputConnection ?: return
        ic.deleteSurroundingText(1, 0)
    }
    private fun enter() {
        val ic = currentInputConnection ?: return
        val action = currentInputEditorInfo?.imeOptions?.and(EditorInfo.IME_MASK_ACTION) ?: EditorInfo.IME_ACTION_NONE
        if(action != EditorInfo.IME_ACTION_NONE && action != EditorInfo.IME_ACTION_UNSPECIFIED) ic.performEditorAction(action)
        else ic.commitText("\n",1)
    }
    private fun refresh() {
        inputView = buildKeyboard()
        setInputView(inputView)
    }
}