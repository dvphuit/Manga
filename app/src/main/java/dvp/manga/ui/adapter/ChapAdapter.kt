package dvp.manga.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.Chapter
import dvp.manga.databinding.ChapterItemBinding
import dvp.manga.ui.detail.MangaDetailFragmentDirections

/**
 * @author dvphu on 19,March,2020
 */

class ChapAdapter : RecyclerView.Adapter<ChapAdapter.ViewHolder>() {

    private var chaps = emptyList<Chapter>()

    class ViewHolder(private val binding: ChapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                gotoChapContent(binding.data!!, it)
            }
        }

        fun bind(chap: Chapter) {
            with(binding) {
                data = chap
                executePendingBindings()
            }
        }

        private fun gotoChapContent(chap: Chapter, view: View) {
            val direction = MangaDetailFragmentDirections.actionChapToStory(chap)
            view.findNavController().navigate(direction)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DataBindingUtil.inflate<ChapterItemBinding>(LayoutInflater.from(parent.context), R.layout.chapter_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chaps[position])
    }

    override fun getItemCount(): Int = chaps.size

    fun submitList(chaps: List<Chapter>) {
        this.chaps = chaps
        notifyDataSetChanged()
    }
}
