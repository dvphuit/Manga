package dvp.manga.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.ChapContent
import dvp.manga.databinding.ChapContentItemBinding

/**
 * @author dvphu on 20,March,2020
 */

class ChapContentAdapter : RecyclerView.Adapter<ChapContentAdapter.ViewHolder>() {

    private var contents = emptyList<ChapContent>()

    class ViewHolder(private val binding: ChapContentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(content: ChapContent) {
            with(binding) {
                data = content
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DataBindingUtil.inflate<ChapContentItemBinding>(LayoutInflater.from(parent.context), R.layout.chap_content_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contents[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = contents.size

    fun submitList(contents: List<ChapContent>) {
        this.contents = contents
        notifyDataSetChanged()
    }
}
