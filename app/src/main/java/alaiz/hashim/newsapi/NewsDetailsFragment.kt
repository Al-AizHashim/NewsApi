package alaiz.hashim.newsapi


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders


private const val ARG_PARAM1 = "category"
class NewsDetailsFragment : Fragment() {
    lateinit var titleField :  TextView
    lateinit var detailsField :  TextView
 private lateinit var newsItem: NewsItem
    private lateinit var newsDetailsViewModel: NewsViewModel
    private var param1: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsItem= NewsItem()
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)

        }
        newsDetailsViewModel=
            ViewModelProviders.of(this).get(NewsViewModel::class.java)
        newsDetailsViewModel.loadNewsDetails(param1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_news_details, container, false)
        titleField=view.findViewById(R.id.news_title_text_view)
        detailsField=view.findViewById(R.id.news_details_text_view)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsDetailsViewModel.newsDetailsItemLiveData.observe(
            viewLifecycleOwner,
            Observer { newsDetails ->
                newsDetails?.let {
                    Log.d("FromObserver", it.toString())
                    this.newsItem = newsDetails
                    updateUI()
                }
            })
    }
    private fun updateUI() {
        titleField.text= newsItem.title
        detailsField.text=newsItem.info


    }

    companion object {
        fun newInstance(param1: Int) =
            NewsDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}