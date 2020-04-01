package dvp.manga.ui.adapter

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.Entity

/**
 * @author dvphu on 24,March,2020
 */

const val LOADING = 11
const val ITEM = 0

abstract class LazyAdapter<T : Entity>(private val recyclerView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: MutableList<T> = mutableListOf()

    private var isLoading = false
    private var pageIndex = 1
    private val lazyItem = LazyModel(true)
    private var lazyCallback: ((Int) -> Unit)? = null

    init {
        setLoadMoreListener()
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is LazyModel) LOADING else ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LOADING -> ProgressHolder(LayoutInflater.from(parent.context).inflate(R.layout.footer_progress, parent, false))
            else -> implCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setFadeAnimation(holder.itemView)
        if (getItemViewType(position) == LOADING) {
            holder.itemView.layoutParams.height = if (itemCount == 1) RecyclerView.LayoutParams.MATCH_PARENT else RecyclerView.LayoutParams.WRAP_CONTENT
            holder.itemView.requestLayout()
        }
    }

    abstract fun implCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        holder.itemView.clearAnimation()
    }

    internal class ProgressHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    private fun setFadeAnimation(view: View) {
        val anim = ScaleAnimation(.8f, 1f, .8f, 1f)
        anim.duration = 150
        view.startAnimation(anim)
    }

    private fun setLoadMoreListener() {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy < 0 || isLoading) return
                    val totalItemCount = layoutManager.getItemCount()
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    if (!isLoading && lastVisibleItem >= totalItemCount - 5) {
                        startLazyLoad()
                    }
                }
            })
            setSpan(layoutManager)
        }
    }

    abstract fun getSpan(position: Int): Int

    private fun setSpan(layoutManager: RecyclerView.LayoutManager) {
        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return getSpan(position)
                }
            }
        }
    }

    override fun getItemCount() = list.size

    private fun startLazyLoad() {
        isLoading = true
        Handler().post(insertLoading)
    }

    private fun stopLazyLoad(hasData: Boolean) {
        Log.d("TEST", "has data $hasData")
        list.remove(lazyItem as Entity)
        notifyItemRemoved(list.size)
        isLoading = !hasData
        if (hasData) {
            pageIndex++
        }
    }

    fun submitData(list: List<T>) {
        val oldItemCount = this.list.size
        stopLazyLoad(list.size > oldItemCount)
        this.list = list.toMutableList()
        notifyItemRangeInserted(oldItemCount, itemCount)
    }

    fun setLazyCallback(callback: (Int) -> Unit) {
        pageIndex = 1
        list.clear()
        notifyDataSetChanged()
        startLazyLoad()
        lazyCallback = callback
    }

    @Suppress("UNCHECKED_CAST")
    private val insertLoading = Runnable {
        list.add(lazyItem as T)
        notifyItemInserted(list.size)
        lazyCallback?.invoke(pageIndex)
    }
}