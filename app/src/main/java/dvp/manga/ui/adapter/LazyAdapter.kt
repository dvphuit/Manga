package dvp.manga.ui.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.Entity

/**
 * @author dvphu on 24,March,2020
 */

const val LOADING = 11
const val ITEM = 0

abstract class LazyAdapter<T : Entity>(private val recyclerView: RecyclerView?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mList: MutableList<T> = mutableListOf()

    private var endLoadMore = false
    private val lazyItem = LazyModel(true)
    private var lazyCallback: (() -> Unit)? = null

    init {
        setLoadMoreListener()
    }

    override fun getItemViewType(position: Int): Int {
        return if (mList[position] is LazyModel) LOADING else ITEM
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
        val anim = AlphaAnimation(0.1f, 1f)
        anim.duration = 250
        view.startAnimation(anim)
    }

    private fun setLoadMoreListener() {
        recyclerView ?: return
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy < 0 || endLoadMore) return
                    val totalItemCount = layoutManager.getItemCount()
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    if (!endLoadMore && lastVisibleItem >= totalItemCount - 5) {
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
                    return 1
                }
            }
        }
    }

    override fun getItemCount() = mList.size

    private fun startLazyLoad() {
        Handler().post(insertLoading)
        endLoadMore = true
    }

    private fun stopLazyLoad() {
        mList.remove(lazyItem as Entity)
        notifyItemRemoved(mList.size)
    }

    fun submitData(data: List<T>, hasNext: Boolean) {
        stopLazyLoad()
        val oldItemCount = this.mList.size
        endLoadMore = !hasNext
        this.mList = data.toMutableList()
        notifyItemRangeInserted(oldItemCount, mList.size)
    }

    fun setLazyCallback(callback: () -> Unit) {
        mList.clear()
        notifyDataSetChanged()
        startLazyLoad()
        lazyCallback = callback
    }

    fun resetLazyList() {
        mList.clear()
        notifyDataSetChanged()
        startLazyLoad()
    }

    fun setNoMoreData() {
        endLoadMore = true
        if (mList.contains(lazyItem as Entity)) {
            mList.remove(lazyItem as Entity)
            notifyItemRemoved(mList.size)
        }
    }

    fun registerLazyCallback(callback: () -> Unit) {
        lazyCallback = callback
    }

    @Suppress("UNCHECKED_CAST")
    private val insertLoading = Runnable {
        if (!mList.contains(lazyItem as Entity)) {
            mList.add(lazyItem as T)
            notifyItemInserted(mList.size)
            lazyCallback?.invoke()
        }
    }
}