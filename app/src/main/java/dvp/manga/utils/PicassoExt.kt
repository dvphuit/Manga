package dvp.manga.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String) {
    Picasso.get().load(url).into(this)
}

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Picasso.get().load(url).into(view)
    }
}