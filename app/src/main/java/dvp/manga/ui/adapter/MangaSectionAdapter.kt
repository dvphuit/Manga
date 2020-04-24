package dvp.manga.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.Manga
import dvp.manga.databinding.MangaItemBinding

/**
 * @author dvphu on 24,April,2020
 */

class MangaSectionAdapter : RecyclerView.Adapter<MangaSectionAdapter.ViewHolder>() {

    private var list = emptyList<Manga>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.manga_item, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(private val binding: MangaItemBinding) : RecyclerView.ViewHolder(binding.root) {

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
    }

    fun submitData(topMangas: List<Manga>) {
        this.list = topMangas
        notifyDataSetChanged()
    }

}