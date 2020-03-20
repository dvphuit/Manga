package dvp.manga.ui.adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import dvp.manga.R;

/**
 * @author dvphu on 20,March,2020
 */
public abstract class LazyAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView recyclerView;
    public static final int LOADING = 11;
    private static final int ITEM = 0;

    public List<T> arrayList;

    public LazyAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        arrayList = new ArrayList<>();
        setLoadMoreListener();
    }

    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position) == null) {
            return LOADING;
        } else {
            return ITEM;
        }
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_progress, parent, false);
            return new ProgressHolder(view);
        } else {
            return implCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setFadeAnimation(holder.itemView);

        if(getItemViewType(position) == LOADING){
            holder.itemView.getLayoutParams().height = getItemCount() == 1 ? RecyclerView.LayoutParams.MATCH_PARENT : RecyclerView.LayoutParams.WRAP_CONTENT;
            holder.itemView.requestLayout();
        }
    }

    abstract RecyclerView.ViewHolder implCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        holder.itemView.clearAnimation();

    }

    static class ProgressHolder extends RecyclerView.ViewHolder {
        ProgressHolder(View itemView) {
            super(itemView);
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(300);
        view.startAnimation(anim);
    }

    private void setLoadMoreListener() {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy < 0 || isLoading) return;

                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItem = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (!isLoading && lastVisibleItem >= totalItemCount - 5) {
                        startLoadMore();
                    }
                }
            });
            setSpan((GridLayoutManager) layoutManager);
        }
    }

    abstract void setSpan(GridLayoutManager layoutManager);


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void startLoadMore() {
        isLoading = true;
        new Handler().post(runnable);
    }

    private void stopLoadMore(boolean success) {
        if (arrayList.isEmpty()) return;

        arrayList.remove(arrayList.size() - 1);
        notifyItemRemoved(arrayList.size());

        new Handler().postDelayed(() -> {
            isLoading = false;
            if (success) pageIndex++;
        }, 100);
    }

    public void updateList(List<T> list) {
        stopLoadMore(!list.isEmpty());
        for (T item : list) {
            arrayList.add(item);
            notifyItemInserted(arrayList.size());
        }
    }

    private Runnable runnable = () -> {
        arrayList.add(null);
        notifyItemInserted(arrayList.size());
        setDataSource();
    };

    abstract void setDataSource();

    private Boolean isLoading = false;
    public int pageIndex = 1;

}