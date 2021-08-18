package dvp.app.azmanga.navigation

import android.graphics.Path
import android.text.TextPaint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dvp.app.azmanga.utils.asNativeColor
import dvp.app.azmanga.utils.px2dp
import dvp.app.azmanga.utils.sp

/**
 * @author PhuDV
 * Created 8/15/21
 */


@Composable
fun SideBar(viewModel: AppViewModel, nav: NavHostController) {

    val currentRoute by viewModel.selectedTab
    Column(
        modifier = Modifier
            .width(40.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        menuBar.forEach { item ->
            SideBarItem(label = item.title, selected = item.route == currentRoute.route) {
                viewModel.selectTab(item)
                nav.navigate(item.route) {
                    nav.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }

}


@Composable
fun SideBarItem(
    label: String,
    selectedColor: Color = Color.Red,
    unselectedColor: Color = Color.DarkGray,
    selected: Boolean,
    modifier: Modifier = Modifier.wrapContentSize(),
    onClick: () -> Unit
) {
    val ripple = rememberRipple(bounded = false, color = selectedColor)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = true,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple
            ),
        contentAlignment = Alignment.Center
    ) {
        VerticalText(
            text = label,
            selected = selected,
            selectedColor = selectedColor,
            unselectedColor = unselectedColor
        )

    }
}

private val SideBarAnimationSpec = TweenSpec<Float>(
    durationMillis = 300,
    easing = FastOutSlowInEasing
)

@Composable
fun VerticalText(
    text: String,
    selected: Boolean,
    selectedColor: Color,
    unselectedColor: Color
) {
    val paint = remember {
        TextPaint().apply {
            isAntiAlias = true
            textSize = 14.sp
        }
    }
    val width = paint.measureText(text)
    val height = paint.fontMetrics.run { bottom - top }

    val animationProgress by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = SideBarAnimationSpec
    )

    val color = lerp(unselectedColor, selectedColor, animationProgress)

    CompositionLocalProvider(
        LocalContentColor provides color.copy(alpha = 1f),
        LocalContentAlpha provides color.alpha,
    ) {
        paint.color = color.asNativeColor()
        Canvas(
            modifier = Modifier
                .width(height.px2dp())
                .height(width.px2dp())
        ) {
            drawIntoCanvas {
                val centerX = size.width / 2 + height / 4

                val path = Path().apply {
                    moveTo(centerX, width)
                    lineTo(centerX, 0f)
                }
                it.nativeCanvas.drawTextOnPath(text, path, 0f, 0f, paint)
            }
        }
    }


}
