package com.example.bottomsheet.model

import androidx.ui.graphics.Color

class ChooseColor{
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