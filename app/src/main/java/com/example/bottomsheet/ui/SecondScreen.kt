package com.example.bottomsheet.ui

import androidx.compose.Composable
import androidx.compose.ambient
import androidx.ui.core.Clip
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Text
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.DrawShape
import androidx.ui.foundation.shape.RectangleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.imageFromResource
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.text.TextStyle
import androidx.ui.unit.dp
import com.example.bottomsheet.viewmodel.ChooseColor
import com.example.bottomsheet.viewmodel.MyColor
import com.example.bottomsheet.viewmodel.Screens
import com.example.bottomsheet.repository.imageList
import com.example.bottomsheet.repository.titleList
import com.example.bottomsheet.viewmodel.navigateTo

@Composable
fun SecondScreen() {
    Column {
        TopAppBar(title = {
                Text(text = "Second Screen")
            }
        )
        FlexColumn() {
            expanded(1f) {
                viewList()
            }
            inflexible {
                MyButton(
                    text = "Back",
                    onClick = {
                        navigateTo(
                            Screens.Home
                        )
                    },
                    style = MyContainedButtonStyle(
                        backgroundColor = ChooseColor.selectedContentColor,
                        contentColor = ChooseColor.selectedBackgroundColor
                    )
                )
            }
        }
    }
}

@Composable
fun myView(index: Int = 0,backgroundColor:Color = Color.Black,contentColor:Color = Color.Black,roundingValue: Int = 0)
{
    val context = ambient(ContextAmbient)
    Container(padding = EdgeInsets(10.dp))
    {
        DrawShape(shape = RectangleShape, color = backgroundColor)
        Column {
            Text(text = titleList[index],style = TextStyle(color = contentColor),modifier = Spacing(10.dp))
            Container(expanded = true, height = 200.dp) {
                Clip(shape = RoundedCornerShape(roundingValue)) {
                    DrawImage(image = imageFromResource(res = context.resources,resId = imageList[index]))
                }
            }
        }
    }
}
@Composable
fun viewList()
{
    MaterialTheme {
        VerticalScroller {
            Column {
                (imageList.indices).forEachIndexed { index, _ ->
                    myView(
                        index,
                        ChooseColor.selectedBackgroundColor,
                        ChooseColor.selectedContentColor,
                        ChooseColor.selectedRoundingValue
                    )
                    Divider(height = 1.dp, color = MyColor.darkgray)
                }
            }
        }
    }
}