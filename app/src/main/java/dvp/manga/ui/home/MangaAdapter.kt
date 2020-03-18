package dvp.manga.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.Manga
import dvp.manga.databinding.MangaItemBinding


class MangaAdapter : ListAdapter<Manga, MangaAdapter.ViewHolder>(MangaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.manga_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: MangaItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                //                binding.data?.let { plantId ->
//                    navigateToPlant(plantId, view)
//                }
            }
        }

        fun bind(manga: Manga) {
            with(binding) {
                data = MangaVM(manga)
                executePendingBindings()
            }
        }
    }
}

private class MangaDiffCallback : DiffUtil.ItemCallback<Manga>() {
    override fun areItemsTheSame(oldItem: Manga, newItem: Manga): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Manga, newItem: Manga): Boolean = oldItem == newItem
}