package dvp.manga.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.Manga
import dvp.manga.data.model.SectionRoute
import dvp.manga.databinding.MangaItemFullBinding
import dvp.manga.utils.NavManager
import dvp.manga.utils.SharedElementManager


class MangaAdapter(recyclerView: RecyclerView?, val section: SectionRoute) : LazyAdapter<Manga>(recyclerView) {

    override fun implCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return MangaHolder(DataBindingUtil.inflate(LayoutInflater.from(parent!!.context), R.layout.manga_item_full, parent, false))
    }

    override fun getSpan(position: Int) = if (getItemViewType(position) == LOADING) 3 else 3

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is MangaHolder) {
            holder.bind(mList[position])
        }
    }

    inner class MangaHolder(private val binding: MangaItemFullBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                SharedElementManager.setElementInfo(getTransitionName(binding.data!!), adapterPosition)
                NavManager.gotoMangaDetail(section.value, binding.data!!, binding.imgWrapper)
            }
        }

        fun bind(manga: Manga) {
            with(binding) {
                data = manga
                ViewCompat.setTransitionName(binding.imgWrapper, getTransitionName(manga))
                executePendingBindings()
                SharedElementManager.startSE(getTransitionName(manga), adapterPosition)
            }
        }

        private fun getTransitionName(manga: Manga) = "cover_${section.value}${manga.name}"
    }

    override fun getItemCount() = mList.size

}
