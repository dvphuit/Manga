package dvp.manga.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.Chapter
import dvp.manga.databinding.ChapterItemBinding

/**
 * @author dvphu on 19,March,2020
 */

class ChapAdapter : RecyclerView.Adapter<ChapAdapter.ViewHolder>() {

    private var chaps = emptyList<Chapter>()

    class ViewHolder(private val binding: ChapterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chap: Chapter) {
            with(binding) {
                data = chap
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DataBindingUtil.inflate<ChapterItemBinding>(LayoutInflater.from(parent.context), R.layout.chapter_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chaps[position])
    }

    override fun getItemCount(): Int  = chaps.size

    fun submitList(chaps: List<Chapter>){
        this.chaps = chaps
        notifyDataSetChanged()
    }
}
