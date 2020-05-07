package dvp.manga.ui.adapter

import android.util.Log
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
import dvp.manga.ui.home.HomeFragment
import dvp.manga.ui.home.HomeFragmentDirections

/**
 * @author dvphu on 24,April,2020
 */

class MangaSectionAdapter : RecyclerView.Adapter<MangaSectionAdapter.ViewHolder>() {

    private var list = emptyList<Manga>()
    private var section: String = ""

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
                gotoDetail(binding.data!!, it, binding.imgWrapper)
            }
        }

        fun bind(value: Manga) {
            with(binding) {
                data = value
                ViewCompat.setTransitionName(binding.imgWrapper, getTransitionName())
                executePendingBindings()
            }
        }

        private fun gotoDetail(manga: Manga, parent: View, cover: View) {
            HomeFragment.navSection = section
            val direction = HomeFragmentDirections.actionMangaToDetail(manga, section)
            val extras = FragmentNavigatorExtras(
                cover to getTransitionName()
            )
            parent.findNavController().navigate(direction, extras)
        }

        private fun getTransitionName() = "cover_$section${binding.data!!.name}"
    }

    fun submitData(section: String, topMangas: List<Manga>) {
        Log.d("TEST", "data changed $section -- ${topMangas.size}")
        this.list = topMangas
        this.section = section
        notifyDataSetChanged()
    }

}