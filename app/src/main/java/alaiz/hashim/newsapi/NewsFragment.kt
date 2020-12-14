package alaiz.hashim.newsapi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "NewsFragment"
private const val ARG_PARAM1 = "category"

class NewsFragment : Fragment() {
    interface Callbacks {
        fun onNewsSelected(newsId: Int)
    }

    private var callbacks: Callbacks? = null

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsRecyclerView: RecyclerView
    private var newsCategory: String? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsCategory = it.getString(ARG_PARAM1)

        }

        newsViewModel =
            ViewModelProviders.of(this).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        newsRecyclerView = view.findViewById(R.id.news_recycler_view)
        newsRecyclerView.layoutManager = GridLayoutManager(context, 1)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (newsCategory) {
            "Sport News" -> {
                newsViewModel.newsSportItemLiveData.observe(
                    viewLifecycleOwner,
                    Observer { newsItems ->
                        Log.d(TAG, "Have political news items from view model $newsItems")
                        updateui(newsItems)
                    })
            }
            "Political News" -> {
                newsViewModel.newsItemLiveData.observe(
                    viewLifecycleOwner,
                    Observer { newsItems ->
                        Log.d(TAG, "Have sport news items from view model $newsItems")
                        updateui(newsItems)
                    })
            }
            "Oddity News" -> {
                newsViewModel.newsOddityItemLiveData.observe(
                    viewLifecycleOwner,
                    Observer { newsItems ->
                        Log.d(TAG, "Have oddity news items from view model $newsItems")
                        updateui(newsItems)
                    })

            }
            else -> {
                newsViewModel.newsItemLiveData.observe(
                    viewLifecycleOwner,
                    Observer { newsItems ->
                        Log.d(TAG, "Have sport items from view model $newsItems")
                        updateui(newsItems)
                    })
            }
        }


    }

    private fun updateui(newsItems: List<NewsItem>) {
        newsRecyclerView.adapter = QuakeAdapter(newsItems)
    }

    private inner class QuakeHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener  {
        init {
            itemView.setOnClickListener(this)

        }
        private lateinit var newsItemss: NewsItem
        @SuppressLint("SimpleDateFormat")
        var dateFormatter: SimpleDateFormat = SimpleDateFormat("EE, MM d, yyyy")

        @SuppressLint("SimpleDateFormat")
        var timeFormatter: SimpleDateFormat = SimpleDateFormat("hh:mm a")

        private val titleNewsTV = itemView.findViewById(R.id.title_view) as TextView
        private val newsTimeTV = itemView.findViewById(R.id.time_textView) as TextView
        private val newsDateTV = itemView.findViewById(R.id.date_textView) as TextView


        fun bind(newsItems: NewsItem) {
            newsItemss=newsItems
            //titleNewsTV.text=newsItems.title
            titleNewsTV.text = newsItems.title
            newsTimeTV.text = timeFormatter.format(Calendar.getInstance().time)
            newsDateTV.text = newsItems.date

        }


        override fun onClick(v: View?) {
            Toast.makeText(requireContext(),"the id: ${newsItemss.id} and title ${newsItemss.title} is clicked",Toast.LENGTH_LONG)
            callbacks?.onNewsSelected(newsItemss.id)
        }


    }

    private inner class QuakeAdapter(private val newsItems: List<NewsItem>) :
        RecyclerView.Adapter<QuakeHolder>() {


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): QuakeHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
            return QuakeHolder(view)
        }

        override fun getItemCount(): Int = newsItems.size

        override fun onBindViewHolder(holder: QuakeHolder, position: Int) {
            val newsItems = newsItems[position]
            holder.bind(newsItems)

        }
    }
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }


    companion object {
        fun newNewsInstance() = NewsFragment()
        fun newInstance(newsCategory: String) = NewsFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, newsCategory)

            }
        }
    }
}
