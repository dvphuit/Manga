package dvp.app.azmanga.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dvp.app.azmanga.navigation.AppViewModel
import dvp.app.azmanga.ui.theme.appTypography


@Composable
fun TopBar(viewModel: AppViewModel) {
    val tab by viewModel.selectedTab

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(MaterialTheme.colors.background), contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tab.title,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(start = 16.dp),
                style = appTypography.h3
            )
            Row(modifier = Modifier.wrapContentSize(), Arrangement.End) {

//                IconButton(
//                    onClick = { onFavouritesClick() },
//                    modifier = Modifier.padding(end = 16.dp)
//                ) {
//                    val settingsIcon: Painter = painterResource(id = R.drawable.ic_heart)
//                    Icon(
//                        painter = settingsIcon,
//                        contentDescription = "favourites_icon",
//                        tint = MaterialTheme.colors.onPrimary
//                    )
//                }
//
//                IconButton(onClick = { onToggle() }, modifier = Modifier.padding(end = 16.dp)) {
//                    val toggleIcon = if (isSystemInDarkTheme())
//                        painterResource(id = R.drawable.ic_night)
//                    else
//                        painterResource(id = R.drawable.ic_day)
//                    Icon(
//                        painter = toggleIcon,
//                        contentDescription = "text_day_night",
//                        tint = MaterialTheme.colors.onPrimary
//                    )
//                }

            }

        }
    }
}


@Composable
fun TopBarWithBack(title: String, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onBackClick() }, modifier = Modifier.padding(start = 16.dp)) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back_icon",
                tint = MaterialTheme.colors.onPrimary
            )
        }

        Text(
            text = title,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(start = 16.dp)
        )

    }

}