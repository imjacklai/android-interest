package tw.ctl.interest.history

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_history.*
import tw.ctl.interest.Entity
import tw.ctl.interest.R

class HistoryFragment : Fragment(), HistoryView {

    private var adapter: HistoryAdapter? = null
    private val presenter = HistoryPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater?.inflate(R.layout.fragment_history, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setAdView()
        presenter.attachView(this)
        presenter.fetchLocalData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        recyclerView.adapter = null
        adView.destroy()
    }

    override fun onHistories(histories: RealmResults<Entity>) {
        if (histories.size == 0) {
            recyclerView.visibility = View.GONE
            description.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            description.visibility = View.GONE
        }
        adapter?.setEntities(histories)
    }

    private fun setRecyclerView() {
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = manager
        adapter = HistoryAdapter()
        recyclerView.adapter = adapter
    }

    private fun setAdView() {
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                adView.visibility = View.VISIBLE
            }
        }
        adView.loadAd(AdRequest.Builder().build())
    }

}
