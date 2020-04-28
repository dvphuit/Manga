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
import dvp.manga.databinding.MangaItemFullBinding
import dvp.manga.ui.home.HomeFragmentDirections


class MangaAdapter(recyclerView: RecyclerView?, val section: String) : LazyAdapter<Manga>(recyclerView) {

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
            binding.setClickListener { view ->
                gotoDetail(binding.data!!, view, binding.imgWrapper)
            }
        }

        fun bind(manga: Manga) {
            with(binding) {
                data = manga
                ViewCompat.setTransitionName(binding.imgWrapper, getTransitionName())
                executePendingBindings()
            }
        }

        private fun gotoDetail(manga: Manga, parent: View, cover: View) {
            val direction = HomeFragmentDirections.actionMangaToDetail(manga, section)
            val extras = FragmentNavigatorExtras(
                cover to getTransitionName()
            )
            parent.findNavController().navigate(direction, extras)
        }

        private fun getTransitionName() = "cover_$section${binding.data!!.name}"
    }

    override fun getItemCount() = mList.size

}
