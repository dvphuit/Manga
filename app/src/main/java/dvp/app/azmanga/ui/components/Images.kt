package dvp.app.azmanga.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import coil.request.CachePolicy

/**
 * @author PhuDV
 * Created 8/15/21
 */

@Composable
fun NetImage(url: String?, modifier: Modifier = Modifier){
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                this.diskCachePolicy(CachePolicy.ENABLED)
                this.crossfade(true)
            }
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize()
    )
}