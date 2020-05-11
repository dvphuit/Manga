package dvp.manga.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.Chapter

/**
 * @author dvphu on 08,May,2020
 */

data class PageChap(val page: String, val chaps: List<Chapter>)

class ChapPageAdapter : RecyclerView.Adapter<ChapPageAdapter.ViewHolder>() {

    private var list = emptyList<PageChap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_chap, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val rvChap = view.findViewById<RecyclerView>(R.id.chap_list)
        private val adapter = ChapAdapter()

        init {
            rvChap.adapter = adapter
        }

        fun bind(value: PageChap) {
            adapter.submitList(value.chaps)
        }
    }

    fun submitData(pageChaps: List<PageChap>) {
        this.list = pageChaps
        notifyDataSetChanged()
    }

    fun getTitle(position: Int) = list[position]?.page

}