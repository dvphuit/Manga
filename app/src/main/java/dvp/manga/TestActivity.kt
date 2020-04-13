package dvp.manga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class TestActivity : AppCompatActivity() {
    private val items = (1..20).map { "item $it" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
//        recyclerview.apply {
//            adapter = SimpleAdapter(items)
//            layoutManager = LinearLayoutManager(this@TestActivity, RecyclerView.VERTICAL, false)
//        }

//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SimpleAdapter(private val items: List<String>) : RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return LayoutInflater.from(parent.context).run {
                inflate(android.R.layout.simple_list_item_1, parent, false).let {
                    ViewHolder(it)
                }
            }
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text = items[position]
        }

        class ViewHolder(
            view: View,
            private val textView: TextView = view.findViewById(android.R.id.text1)
        ) : RecyclerView.ViewHolder(view) {
            var text: CharSequence
                get() = textView.text
                set(value) {
                    textView.text = value
                }
        }

    }
}
