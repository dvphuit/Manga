package dvp.manga.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import dvp.manga.data.model.Genres

fun ImageView.loadUrl(url: String) {
    Picasso.get().load(url).into(this)
}

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Picasso.get().load(url).into(view)
    }
}

@BindingAdapter("setGenres")
fun bindGenres(view: dvp.manga.ui.custom.FlowLayout, listGenres: List<Genres>) {
    view.setList(listGenres)
}