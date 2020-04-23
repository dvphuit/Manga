package dvp.manga.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Manga
import dvp.manga.databinding.TopMangaItemBinding

class TopMangaAdapter : RecyclerView.Adapter<TopMangaAdapter.ViewHolder>() {

    private var list = emptyList<Manga>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.top_manga_item, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(private val binding: TopMangaItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
            }
        }

        fun bind(value: Manga) {
            with(binding) {
                data = value
                executePendingBindings()
            }
        }

        private fun gotoChapContent(chap: Chapter, view: View) {
//            val direction = MangaDetailFragmentDirections.actionChapToStory(chap)
//            view.findNavController().navigate(direction)
        }
    }

    fun submitData(topMangas: List<Manga>) {
        this.list = topMangas
        notifyDataSetChanged()
    }

}