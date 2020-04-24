package dvp.manga.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import dvp.manga.R
import dvp.manga.data.model.ChildList
import dvp.manga.data.model.Section
import dvp.manga.data.model.Top
import kotlin.math.abs


/**
 * @author dvphu on 24,April,2020
 */

const val TOP_MANGA = 0
const val SECTION = 1

class HomeAdapter(val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = emptyList<Section>()
    private var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TOP_MANGA -> {
                TopMangaVH(inflater.inflate(R.layout.top_manga_list, parent, false))
            }
            SECTION -> {
                SectionVH(inflater.inflate(R.layout.manga_section_title, parent, false))
            }
            else -> throw Exception("invalid view type")
        }
    }


    inner class TopMangaVH(view: View) : RecyclerView.ViewHolder(view) {
        private val topMangaAdapter = TopMangaAdapter()

        init {
            view.findViewById<ViewPager2>(R.id.top_manga_list).apply {
                clipChildren = false
                clipToPadding = false
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                val transform = CompositePageTransformer()
                transform.addTransformer(MarginPageTransformer(40))
                transform.addTransformer { page, position ->
                    val r = 1 - abs(position)
                    page.scaleY = (.85 + .15 * r).toFloat()
                }
                setPageTransformer(transform)
                adapter = topMangaAdapter
            }
        }


        fun bind(top: Top) {
            top.mangaList.observe(lifecycleOwner) {
                topMangaAdapter.submitData(it)
            }
        }
    }

    inner class SectionVH(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.section_title)
        private val mangaAdapter = MangaSectionAdapter()

        init {
            view.findViewById<RecyclerView>(R.id.manga_list).apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = mangaAdapter
                setRecycledViewPool(viewPool)
            }
        }

        fun bind(mangaSection: ChildList) {
            title.text = mangaSection.title
            mangaSection.mangaList.observe(lifecycleOwner) {
                mangaAdapter.submitData(it)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TopMangaVH) {
            holder.bind(list[position] as Top)
        } else if (holder is SectionVH) {
            holder.bind(list[position] as ChildList)
        }
    }

    override fun getItemCount() = list.size

    fun submitData(section: List<Section>) {
        this.list = section
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TOP_MANGA
            else -> SECTION
        }
    }
}