package dvp.manga.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.Chapter

/**
 * @author dvphu on 08,May,2020
 */

data class PagedChap(val title: String, val chaps: List<Chapter>)

class ChapPageAdapter : RecyclerView.Adapter<ChapPageAdapter.ViewHolder>() {

    private var list = emptyList<PagedChap>()

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

        fun bind(value: PagedChap) {
            adapter.submitList(value.chaps)
        }
    }

    fun submitData(pageChaps: List<PagedChap>) {
        this.list = pageChaps
        notifyDataSetChanged()
    }

    fun getTitle(position: Int) = list[position].title

}