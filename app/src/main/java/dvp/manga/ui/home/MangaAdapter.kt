package dvp.manga.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dvp.manga.R
import dvp.manga.data.model.Manga
import kotlinx.android.synthetic.main.manga_item.view.*

class MangaAdapter : RecyclerView.Adapter<MangaAdapter.ViewHolder>() {

    private var mangas: List<Manga> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.manga_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mangas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mangas[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(manga: Manga) {
            itemView.tvName.text = manga.name
            itemView.tvLastChap.text = manga.last_chap
            Picasso.get().load(manga.cover).into(itemView.imgCover)
        }
    }

    fun setMangas(manga: List<Manga>) {
        this.mangas = manga
        notifyDataSetChanged()
    }

}