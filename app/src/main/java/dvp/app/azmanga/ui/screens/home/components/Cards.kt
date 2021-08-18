package dvp.app.azmanga.ui.screens.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import dvp.app.azmanga.utils.toPx

@Composable
fun TopCard(
    isLoading: Boolean = false,
    content: @Composable (Float, Float) -> Unit?
) {
    BaseCard(
        isLoading = isLoading,
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
    ) {
        content.invoke(-1f, 180.dp.toPx())
    }
}

@Composable
fun DefaultCard(
    isLoading: Boolean = false,
    content: @Composable () -> Unit
) {
    BaseCard(
        isLoading = isLoading,
        modifier = Modifier
            .height(180.dp)
            .width(150.dp)
    ) {
        content.invoke()
    }
}

@SuppressLint("ModifierParameter")
@Composable
private fun BaseCard(
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .placeholder(
                visible = isLoading,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.LightGray,
                shape = RoundedCornerShape(2.dp)
            ),
        elevation = 2.dp,
        shape = RoundedCornerShape(2.dp)
    ) {
        content.invoke()
    }
}
