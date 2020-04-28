package dvp.manga.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.request.CachePolicy
import dvp.manga.data.model.Genres

@BindingAdapter("loadUrl")
fun ImageView.loadUrl(url: String) {
    load(url){
        addHeader("Referer", "http://truyenqq.com")
        crossfade(true)
        diskCachePolicy(CachePolicy.ENABLED)
        memoryCachePolicy(CachePolicy.ENABLED)
    }
}


@BindingAdapter("setGenres")
fun bindGenres(view: dvp.manga.ui.custom.FlowLayout, listGenres: List<Genres>) {
    view.setList(listGenres)
}

