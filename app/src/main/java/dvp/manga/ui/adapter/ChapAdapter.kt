package dvp.manga.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.Chapter
import dvp.manga.databinding.ChapterItemBinding
import dvp.manga.utils.NavManager

/**
 * @author dvphu on 19,March,2020
 */

class ChapAdapter : ListAdapter<Chapter, ChapAdapter.ViewHolder>(ChapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.chapter_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { comment ->
            with(holder) {
                itemView.tag = comment
                bind(comment)
            }
        }
    }

    class ViewHolder(private val binding: ChapterItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                NavManager.gotoReadChap(binding.data!!)
            }
        }

        fun bind( value: Chapter) {
            with(binding) {
                data = value
                executePendingBindings()
            }
        }
    }

}

private class ChapterDiffCallback : DiffUtil.ItemCallback<Chapter>() {

    override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
        return oldItem.name == oldItem.name
    }
}