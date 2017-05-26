package tw.ctl.interest.history

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.fragment_history.*
import tw.ctl.interest.Entity
import tw.ctl.interest.R

/**
 * Created by jacklai on 2017/3/1.
 */

class HistoryFragment : Fragment() {

    private var adapter: HistoryAdapter? = null
    private val realm = Realm.getDefaultInstance()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        fetchData()
        setAdView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.adapter = null
        realm.close()
        adView.destroy()
    }

    private fun setRecyclerView() {
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = manager
        adapter = HistoryAdapter()
        recyclerView.adapter = adapter
    }

    private fun fetchData() {
        val entities = realm.where<Entity>(Entity::class.java).findAllSorted("date", Sort.DESCENDING)

        recyclerView.visibility = if (entities.size == 0) View.GONE else View.VISIBLE
        description.visibility = if (entities.size == 0) View.VISIBLE else View.GONE

        entities.addChangeListener { elements ->
            recyclerView.visibility = if (elements.size == 0) View.GONE else View.VISIBLE
            description.visibility = if (elements.size == 0) View.VISIBLE else View.GONE

            if (elements.size > 20) elements.last().delete()

            adapter?.notifyDataSetChanged()
        }

        adapter?.setEntities(entities)
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
