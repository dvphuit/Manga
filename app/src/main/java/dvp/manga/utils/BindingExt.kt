package dvp.manga.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.request.CachePolicy
import dvp.manga.data.model.Genre
import dvp.manga.ui.custom.TwoStateAnimButton

@BindingAdapter("loadUrl")
fun ImageView.loadUrl(url: String?) {
    if(url.isNullOrEmpty()) return
    load(url) {
        addHeader("Referer", "http://truyenqq.com")
        crossfade(true)
        diskCachePolicy(CachePolicy.ENABLED)
        memoryCachePolicy(CachePolicy.ENABLED)
    }
}


@BindingAdapter("setGenres")
fun bindGenres(view: dvp.manga.ui.custom.FlowLayout, listGenres: List<Genre>?) {
    if (listGenres.isNullOrEmpty()) return
    view.setList(listGenres)
}

@BindingAdapter("setBookmark")
fun bindBookmark(view: TwoStateAnimButton, state: Boolean) {
    view.setState(state)
}

