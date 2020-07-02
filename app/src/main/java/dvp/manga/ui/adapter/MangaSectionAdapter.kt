package dvp.manga.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.Manga
import dvp.manga.data.model.SectionRoute
import dvp.manga.databinding.MangaItemBinding
import dvp.manga.utils.NavManager
import dvp.manga.utils.SharedElementManager

/**
 * @author dvphu on 24,April,2020
 */

class MangaSectionAdapter : RecyclerView.Adapter<MangaSectionAdapter.ViewHolder>() {

    private var list = emptyList<Manga>()
    private var section: SectionRoute? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.manga_item, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(private val binding: MangaItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                SharedElementManager.setRoute(section!!)
                NavManager.gotoMangaDetail(section!!.value, binding.data!!, binding.imgWrapper)
            }
        }

        fun bind(value: Manga) {
            with(binding) {
                data = value
                ViewCompat.setTransitionName(binding.imgWrapper, getTransitionName(value))
                executePendingBindings()
            }
        }

        private fun getTransitionName(manga: Manga) = "cover_${section?.value}${manga.id}"
    }

    fun submitData(topMangas: List<Manga>) {
        this.list = topMangas
        notifyDataSetChanged()
    }

    fun setSection(section: SectionRoute) {
        this.section = section
    }

}