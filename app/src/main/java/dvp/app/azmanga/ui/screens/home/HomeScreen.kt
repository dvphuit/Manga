package dvp.app.azmanga.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dvp.app.azmanga.base.Resource
import dvp.app.azmanga.data.entities.Manga
import dvp.app.azmanga.ui.components.NetImage
import dvp.app.azmanga.ui.screens.home.components.DefaultCard
import dvp.app.azmanga.ui.screens.home.components.TopCard
import dvp.app.azmanga.ui.theme.appTypography

@Composable
fun CustomColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        var childWidth = 0
        val placeables = measurables.mapIndexed { index, measurable ->
            val childHeight = constraints.maxHeight / measurables.size
            childWidth = (childHeight * .65).toInt()
            val placeable = measurable.measure(
                constraints.copy(
                    minHeight = childHeight,
                    maxHeight = childHeight,
                    minWidth = childWidth,
                    maxWidth = childWidth
                )
            )
            placeable
        }

        var yPosition = 0
        layout(childWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val top by viewModel.topMangas.collectAsState()
    RecentMangas(resource = top)
}

@Composable
fun RecentMangas(resource: Resource<List<Manga>>) {
    when (resource) {
        is Resource.Success -> {
            Log.d("TEST", "success ${resource.data.size}")
            RecentMangaHorizontalList(list = resource.data)
        }
        is Resource.Loading -> {
            Log.d("TEST", "loading")
            DefaultCard(isLoading = true) {}
        }
        is Resource.Empty -> {
            Log.d("TEST", "empty")
            DefaultCard(isLoading = true) {}
        }
        is Resource.Error -> {
            Log.d("TEST", "Error")
            DefaultCard(isLoading = true) {}
        }
    }
}

@Composable
fun RecentMangaHorizontalList(list: List<Manga>) {
    val row = 2
    LazyRow(
        modifier = Modifier.fillMaxSize()
    ) {
        items(list.size / row) {
            CustomColumn() {
                MangaCard(manga = list[it * row])
                MangaCard(manga = list[it * row + 1])
//                MangaCard(manga = list[it * 3 + 2])
            }
        }
    }
}


@OptIn(ExperimentalUnitApi::class)
@Composable
fun MangaCard(manga: Manga) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .weight(1f, fill = true)
                .alpha(1f),
            elevation = 2.dp,
            shape = RoundedCornerShape(2.dp)
        ) {
            NetImage(url = manga.cover, modifier = Modifier.fillMaxSize())
        }
        Text(
            modifier = Modifier.height(40.dp),
            text = manga.name,
            style = TextStyle(
                fontSize = TextUnit(14f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold,
            ),

            maxLines = 2
        )
        Text(
            modifier = Modifier,
            text = manga.last_chap ?: "",
            style = TextStyle(
                fontSize = TextUnit(12f, TextUnitType.Sp),
                color = Color.Red
            ),
            maxLines = 1
        )
    }

}


@Composable
fun TopMangas(resource: Resource<List<Manga>>) {
    when (resource) {
        is Resource.Success -> {
            Log.d("TEST", "success ${resource.data.size}")
            Slider(list = resource.data)
        }
        is Resource.Loading -> {
            Log.d("TEST", "loading")
            TopCard(isLoading = true) { _, _ -> }
        }
        is Resource.Empty -> {
            Log.d("TEST", "empty")
            TopCard(isLoading = true) { _, _ -> }
        }
        is Resource.Error -> {
            Log.d("TEST", "Error")
            TopCard(isLoading = true) { _, _ -> }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Slider(list: List<Manga>) {
    val pagerState = rememberPagerState(pageCount = list.size)
    HorizontalPager(
        state = pagerState,
    ) { index ->
        val item = list[index]
        TopItem(url = item.cover, title = item.name)
    }
}

@Composable
fun TopItem(url: String?, title: String) {
    TopCard { _, height ->
        NetImage(url = url)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = height * .5f,
                        endY = height
                    )
                )
        ) {
            Text(
                text = title,
                color = Color.White,
                style = appTypography.subtitle1.copy(
                    shadow = Shadow(
                        color = Color.Black,
                        blurRadius = 15f
                    ),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .align(alignment = Alignment.BottomStart)
            )
        }
    }
}

