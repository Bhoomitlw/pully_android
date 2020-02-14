package com.example.bottomsheet.ui

import androidx.compose.Composable
import androidx.ui.core.Clip
import androidx.ui.core.Text
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.DrawShape
import androidx.ui.foundation.shape.RectangleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.FlexColumn
import androidx.ui.layout.LayoutPadding
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.unit.dp
import com.example.bottomsheet.model.ChooseColor
import com.example.bottomsheet.model.MyColor
import com.example.bottomsheet.model.Screens
import com.example.bottomsheet.data.imageList
import com.example.bottomsheet.data.titleList
import com.example.bottomsheet.model.navigateTo

@Composable
fun SecondScreen() {
    Column {
        TopAppBar(title = { Text(text = "Second Screen")})
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
    Container() {
        DrawShape(shape = RectangleShape, color = backgroundColor)
        Column(modifier = LayoutPadding(10.dp)){
            Text(text = titleList[index],style = TextStyle(color = contentColor),modifier = LayoutPadding(10.dp))
            Container(expanded = true, height = 200.dp) {
                Clip(shape = RoundedCornerShape(roundingValue)) {
                    DrawImage(image = imageResource(id = imageList[index]))
                }
            }
        }
    }
}
@Composable
fun viewList()
{
    MaterialTheme() {
        VerticalScroller() {
            Column() {
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