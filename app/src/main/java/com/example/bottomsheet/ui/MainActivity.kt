package com.example.bottomsheet.ui
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.animation.Crossfade
import androidx.ui.core.*
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.DrawShape
import androidx.ui.foundation.shape.RectangleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.*
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.surface.Surface
import androidx.ui.text.TextStyle
import androidx.ui.unit.*
import com.example.bottomsheet.elements.MyBottomDrawerLayout
import com.example.bottomsheet.model.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppContent(this@MainActivity)
            }
        }
    }
}

@Composable
fun BottomSheet(context: Context) {
        val (drawerState, onDrawerStateChange) = state { DrawerState.Closed }
    Column {
        TopAppBar(
            title = { Text(text = "Custom drawer") })
        MyBottomDrawerLayout(
            drawerState = drawerState,
            onStateChange = onDrawerStateChange,
            drawerContent = {
                Container(alignment = Alignment.TopCenter, expanded = true) {
                    Clip(
                        shape = RoundedCornerShape(
                            topLeft = 20.dp,
                            topRight = 20.dp,
                            bottomLeft = 0.dp,
                            bottomRight = 0.dp
                        )
                    ) {
                        DrawShape(shape = RectangleShape, color = Color(0xFFECF0F1))
                    }
                    Column(arrangement = Arrangement.Center) {
                        Container(
                            expanded = true,
                            height = 15.dp,
                            alignment = Alignment.TopCenter,
                            padding = EdgeInsets(top = 10.dp)
                        ) {
                            Button(
                                text = "",
                                style = ContainedButtonStyle(backgroundColor = Color.Gray)
                            )
                        }
                        PopulateList(
                            backgroundColor = ChooseColor.selectedBackgroundColor,
                            contentColor = ChooseColor.selectedContentColor,
                            roundedValue = ChooseColor.selectedRoundingValue
                        )
                    }
                }

            },
            bodyContent = {
                Column() {
                    Row() {
                        MyButton(
                            text = "Pink",
                            onClick = {
                                ChooseColor.selectedColor(
                                    backgroundColor = MyColor.pinkLight,
                                    contentColor = Color.Black
                                )
                                navigateTo(
                                    Screens.Home
                                )
                            },
                            style = MyContainedButtonStyle(
                                backgroundColor = MyColor.pinkLight,
                                contentColor = Color.Black
                            )
                        )
                        MyButton(
                            text = "Green",
                            onClick = {
                                ChooseColor.selectedColor(
                                    backgroundColor = MyColor.greenLight,
                                    contentColor = Color.Black
                                )
                                navigateTo(
                                    Screens.Home
                                )
                            },
                            style = MyContainedButtonStyle(
                                backgroundColor = MyColor.greenLight,
                                contentColor = Color.Black
                            )
                        )
                        MyButton(
                            text = "Yellow",
                            onClick = {
                                ChooseColor.selectedColor(
                                    backgroundColor = MyColor.yellowLight,
                                    contentColor = Color.Black
                                )
                                navigateTo(
                                    Screens.Home
                                )
                            },
                            style = MyContainedButtonStyle(
                                backgroundColor = MyColor.yellowLight,
                                contentColor = Color.Black
                            )
                        )
                        MyButton(
                            text = "gray",
                            onClick = {
                                ChooseColor.selectedColor(
                                    backgroundColor = MyColor.darkgray,
                                    contentColor = Color.White
                                )
                                navigateTo(
                                    Screens.Home
                                )
                            },
                            style = MyContainedButtonStyle(
                                backgroundColor = MyColor.darkgray,
                                contentColor = Color.White
                            )
                        )
                    }
                    Row() {
                        MyButton(
                            text = "round 10%",
                            onClick = {
                                ChooseColor.selectedRoundingValue =
                                    10
                                navigateTo(
                                    Screens.Home
                                )
                            },
                            style = MyContainedButtonStyle(
                                roundedValue = 10
                            )
                        )
                        MyButton(
                            text = "round 20%",
                            onClick = {
                                ChooseColor.selectedRoundingValue =
                                    20
                                navigateTo(
                                    Screens.Home
                                )
                            },
                            style = MyContainedButtonStyle(
                                roundedValue = 20
                            )
                        )
                        MyButton(
                            text = "round 25%",
                            onClick = {
                                ChooseColor.selectedRoundingValue =
                                    25
                                navigateTo(
                                    Screens.Home
                                )
                            },
                            style = MyContainedButtonStyle(
                                roundedValue = 25
                            )
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun PopulateList(backgroundColor : Color = Color.Black, contentColor :Color = Color.White,roundedValue:Int = 0 ) {
    MaterialTheme {
        VerticalScroller {
            Column {
                (0..2).forEachIndexed { index, _ ->
                    createListItem(
                        itemIndex = index,
                        backgroundColor = backgroundColor,
                        contentColor = contentColor,
                        roundedValue = roundedValue
                    )
                    Divider(height = 1.dp)
                }
            }
        }
    }
}
@Composable
private fun createListItem(itemIndex: Int,backgroundColor: Color,contentColor: Color,roundedValue: Int) {
    val buttonList = listOf("click","click","click")
    Padding(left = 8.dp, right = 8.dp, top = 8.dp, bottom = 8.dp) {
        FlexRow(crossAxisAlignment = CrossAxisAlignment.Center) {
            expanded(1.0f) {
                Text("Item $itemIndex",style = TextStyle(color = Color.Black))
            }
            inflexible {
                MyButton(text = buttonList[itemIndex],
                    onClick = {
                        when (itemIndex) {
                            0 -> {
                                navigateTo(
                                    Screens.Second
                                )
                            }
                            1 -> {
                                navigateTo(
                                    Screens.Second
                                )
                            }
                            2 -> {
                                navigateTo(
                                    Screens.Second
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
@Composable
private fun AppContent(context: Context) {
    Crossfade(Status.currentScreen) { screen ->
        Surface(color = (MaterialTheme.colors()).background) {
            when (screen) {
                is Screens.Home -> Home(
                    context
                )
                is Screens.Second -> SecondScreen()
            }
        }
    }
}
@Composable
fun Home(context: Context) {
    BottomSheet(context)
}
@Composable
fun MyButton(text:String="Button",
             style:ButtonStyle = MyContainedButtonStyle(),
             onClick: (() -> Unit)? = null,
             modifier: LayoutPadding= LayoutPadding(5.dp))
{
    Button(text = text,
        style =style,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun MyContainedButtonStyle(backgroundColor : Color = ChooseColor.selectedBackgroundColor,
                           contentColor : Color = ChooseColor.selectedContentColor,
                           roundedValue: Int = ChooseColor.selectedRoundingValue
) : ButtonStyle
{
   return ContainedButtonStyle(backgroundColor = backgroundColor,contentColor = contentColor,shape = RoundedCornerShape(roundedValue))
}

