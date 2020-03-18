package dvp.manga.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dvp.manga.R
import dvp.manga.utils.Injector
import kotlinx.android.synthetic.main.home_fragment.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var mangaAdapter: MangaAdapter

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            Injector.getHomeVMFactory(requireContext())
        ).get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mangaAdapter = MangaAdapter(getPicasso(requireContext()))
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = mangaAdapter

        viewModel.mangas.observe(requireActivity(), Observer {
            mangaAdapter.setMangas(it)
        })
    }

    private fun getPicasso(context: Context): Picasso {
        val okHttpClient = OkHttpClient()
        okHttpClient.interceptors().add(Interceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Referer", "http://truyenqq.com")
                .build()
            chain.proceed(newRequest)
        })
        return Picasso.Builder(context).downloader(OkHttp3Downloader(okHttpClient)).build()
    }


}
