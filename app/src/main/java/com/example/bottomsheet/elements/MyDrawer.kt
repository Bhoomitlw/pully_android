package com.example.bottomsheet.elements

import androidx.animation.AnimationBuilder
import androidx.animation.AnimationEndReason
import androidx.animation.PhysicsBuilder
import androidx.compose.Composable
import androidx.compose.onCommit
import androidx.compose.remember
import androidx.ui.core.*
import androidx.ui.foundation.ValueHolder
import androidx.ui.foundation.animation.AnchorsFlingConfig
import androidx.ui.foundation.animation.animatedDragValue
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.gestures.Draggable
import androidx.ui.graphics.Paint
import androidx.ui.graphics.PaintingStyle
import androidx.ui.layout.Container
import androidx.ui.layout.DpConstraints
import androidx.ui.layout.EdgeInsets
import androidx.ui.layout.Stack
import androidx.ui.material.DrawerState
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import androidx.ui.unit.*

@Composable
fun MyBottomDrawerLayout(
    drawerState: DrawerState,
    onStateChange: (DrawerState) -> Unit,
    gesturesEnabled: Boolean = true,
    drawerContent: @Composable() () -> Unit,
    bodyContent: @Composable() () -> Unit
) {
    Container(expanded = true) {
        WithConstraints { pxConstraints ->
            if (!pxConstraints.hasBoundedHeight) {
                throw IllegalStateException("Drawer shouldn't have infinite height")
            }
            val constraints = withDensity(ambientDensity()) {
                DpConstraints(pxConstraints)
            }
            val minValue = 0f
            val maxValue = 1000f

            val isLandscape = constraints.maxWidth > constraints.maxHeight
            val openedValue = if (isLandscape) maxValue else 900f
            val openedValue1 = 800f
            val openedValue2 = 700f
            val openedValue3 = 600f
            val openedValue4 = 500f
            val openedValue5 = 400f
            val openedValue6 = 300f
            val openedValue7 = 200f
            val openedValue8 = 100f
            val anchors = listOf(
                maxValue to DrawerState.Closed,
                maxValue to DrawerState.Opened,
                openedValue to DrawerState.Closed,
                openedValue to DrawerState.Opened,
                openedValue1 to DrawerState.Closed,
                openedValue1 to DrawerState.Opened,
                openedValue2 to DrawerState.Closed,
                openedValue2 to DrawerState.Opened,
                openedValue3 to DrawerState.Closed,
                openedValue3 to DrawerState.Opened,
                openedValue4 to DrawerState.Closed,
                openedValue4 to DrawerState.Opened,
                openedValue5 to DrawerState.Closed,
                openedValue5 to DrawerState.Opened,
                openedValue6 to DrawerState.Closed,
                openedValue6 to DrawerState.Opened,
                openedValue7 to DrawerState.Closed,
                openedValue7 to DrawerState.Opened,
                openedValue8 to DrawerState.Closed,
                openedValue8 to DrawerState.Opened,
                minValue to DrawerState.Closed,
                minValue to DrawerState.Opened
            )
            StateDraggable(
                state = drawerState,
                onStateChange = onStateChange,
                anchorsToState = anchors,
                animationBuilder = AnimationBuilder,
                dragDirection = DragDirection.Vertical,
                minValue = minValue,
                maxValue = maxValue,
                enabled = gesturesEnabled
            ) { model ->
                Stack {
                    bodyContent()
                    Scrim(
                        drawerState,
                        onStateChange,
                        fraction = {
                            // as we scroll "from height to 0" , need to reverse fraction
                            1 - calculateFraction(
                                openedValue,
                                maxValue,
                                model.value
                            )
                        })
                    BottomDrawerContent(
                        model,
                        constraints,
                        drawerContent
                    )
                }
            }
        }
    }
}

@Composable
private fun DrawerContent(
    xOffset: ValueHolder<Float>,
    constraints: DpConstraints,
    children: @Composable() () -> Unit
) {
    WithOffset(xOffset = xOffset) {
        Container(
            constraints = constraints,
            padding = EdgeInsets(right = VerticalDrawerPadding)
        ) {
            // remove Container when we will support multiply children
            Surface { Container(expanded = true, children = children) }
        }
    }
}

@Composable
private fun BottomDrawerContent(
    yOffset: ValueHolder<Float>,
    constraints: DpConstraints,
    children: @Composable() () -> Unit
) {
    WithOffset(yOffset = yOffset) {
        Container(constraints = constraints) {
            // remove Container when we will support multiply children
            Surface { Container(expanded = true, children = children) }
        }
    }
}

private fun calculateFraction(a: Float, b: Float, pos: Float) =
    ((pos - a) / (b - a)).coerceIn(0f, 1f)

@Composable
private fun Scrim(
    state: DrawerState,
    onStateChange: (DrawerState) -> Unit,
    fraction: () -> Float
) {
    // TODO: use enabled = false here when it will be available
    val scrimContent = @Composable {
        Container(expanded = true) {
            val paint = remember { Paint().apply { style = PaintingStyle.fill } }
            val color = MaterialTheme.colors().onSurface
            Draw { canvas, parentSize ->
                val scrimAlpha = fraction() * ScrimDefaultOpacity
                paint.color = color.copy(alpha = scrimAlpha)
                canvas.drawRect(parentSize.toRect(), paint)
            }
        }
    }
    /* if (state == DrawerState.Opened) {
         Clickable(onClick = { onStateChange(DrawerState.Closed) }, children = scrimContent)
     } else {
         scrimContent()
     }*/
}

@Composable
private fun WithOffset(
    xOffset: ValueHolder<Float>? = null,
    yOffset: ValueHolder<Float>? = null,
    child: @Composable() () -> Unit
) {
    Layout(children = {
        RepaintBoundary(children = child)
    }) { measurables, constraints ->
        if (measurables.size > 1) {
            throw IllegalStateException("Only one child is allowed")
        }
        val childMeasurable = measurables.firstOrNull()
        val placeable = childMeasurable?.measure(constraints)
        val width: IntPx
        val height: IntPx
        if (placeable == null) {
            width = constraints.minWidth
            height = constraints.minHeight
        } else {
            width = min(placeable.width, constraints.maxWidth)
            height = min(placeable.height, constraints.maxHeight)
        }
        layout(width, height) {
            val offX = xOffset?.value?.px ?: 0.px
            val offY = yOffset?.value?.px ?: 0.px
            placeable?.place(offX, offY)
        }
    }
}

private val ScrimDefaultOpacity = 0.32f
private val VerticalDrawerPadding = 56.dp

// drawer children specs
private val StaticDrawerWidth = 256.dp
private val DrawerStiffness = 1000f

private val AnimationBuilder =
    PhysicsBuilder<Float>().apply {
        stiffness = DrawerStiffness
    }

internal val BottomDrawerOpenFraction = 0.5f

@Composable
internal fun <T> StateDraggable(
    state: T,
    onStateChange: (T) -> Unit,
    anchorsToState: List<Pair<Float, T>>,
    animationBuilder: AnimationBuilder<Float>,
    dragDirection: DragDirection,
    enabled: Boolean = true,
    minValue: Float = Float.MIN_VALUE,
    maxValue: Float = Float.MAX_VALUE,
    children: @Composable() (ValueHolder<Float>) -> Unit
) {
    val forceAnimationCheck = androidx.compose.state { true }

    val anchors = remember(anchorsToState) { anchorsToState.map { it.first } }
    val currentValue = anchorsToState.firstOrNull { it.second == state }!!.first
    val flingConfig =
        AnchorsFlingConfig(anchors, animationBuilder, onAnimationEnd = { reason, finalValue, _ ->
            if (reason != AnimationEndReason.Interrupted) {
                val newState = anchorsToState.firstOrNull { it.first == finalValue }?.second
                if (newState != null && newState != state) {
                    onStateChange(newState)
                    forceAnimationCheck.value = !forceAnimationCheck.value
                }
            }
        })
    val position = animatedDragValue(currentValue, minValue, maxValue)

    // This state is to force this component to be recomposed and trigger onCommit below
    // This is needed to stay in sync with drag state that caller side holds
    onCommit(currentValue, forceAnimationCheck.value) {
        position.animatedFloat.animateTo(currentValue, animationBuilder)
    }
    Draggable(
        dragValue = position,
        onDragValueChangeRequested = { position.animatedFloat.snapTo(it) },
        onDragStopped = { position.fling(flingConfig, it) },
        dragDirection = dragDirection,
        enabled = enabled
    ) {
        children(position)
    }
}
