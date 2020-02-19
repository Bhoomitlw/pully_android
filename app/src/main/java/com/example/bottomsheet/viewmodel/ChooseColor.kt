package com.example.bottomsheet.viewmodel

import androidx.ui.graphics.Color

class ChooseColor {
    companion object {
        var selectedBackgroundColor = Color.White
        var selectedContentColor = Color.Black
        var selectedRoundingValue = 0

        fun selectedColor(backgroundColor: Color = Color.Black, contentColor: Color = Color.White,roundingValue: Int = selectedRoundingValue) {
            selectedBackgroundColor =  backgroundColor
            selectedContentColor = contentColor
            selectedRoundingValue = roundingValue
        }
    }
}
class MyColor{
    companion object{
        val greenLight : Color = Color(0xFFEEFEEF)
        val darkgray : Color = Color(0xFF5A5A59)
        val yellowLight : Color = Color(0xFFFDFEEE)
        val pinkLight : Color = Color(0xFFFEEEF5 )
    }
}