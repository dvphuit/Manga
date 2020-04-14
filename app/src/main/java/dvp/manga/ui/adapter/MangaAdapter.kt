package dvp.manga.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.Manga
import dvp.manga.databinding.MangaItemBinding
import dvp.manga.ui.home.HomeFragmentDirections


class MangaAdapter(recyclerView: RecyclerView) : LazyAdapter<Manga>(recyclerView) {

    override fun implCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return MangaHolder(DataBindingUtil.inflate(LayoutInflater.from(parent!!.context), R.layout.manga_item, parent, false))
    }

    override fun getSpan(position: Int) = if (getItemViewType(position) == LOADING) 3 else 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is MangaHolder) {
            holder.bind(mList[position])
        }
    }

    inner class MangaHolder(private val binding: MangaItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                gotoDetail(binding.data!!, view, binding.imgWrapper)
            }
        }

        fun bind(manga: Manga) {
            with(binding) {
                data = manga
                ViewCompat.setTransitionName(binding.imgWrapper, "cover_${manga.name}")
                executePendingBindings()
            }
        }

        private fun gotoDetail(manga: Manga, parent: View, cover: View) {
            val direction = HomeFragmentDirections.actionMangaToDetail(manga)
            val extras = FragmentNavigatorExtras(
                cover to "cover_${manga.name}"
            )
            parent.findNavController().navigate(direction, extras)
        }
    }

    override fun getItemCount() = mList.size

}
