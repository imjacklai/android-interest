package tw.ctl.interest.info

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.NativeExpressAdView
import kotlinx.android.synthetic.main.fragment_info.*
import tw.ctl.interest.R

class InfoFragment : Fragment() {

    private var adView: NativeExpressAdView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adView = NativeExpressAdView(activity)
        adCardView.addView(adView)

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        val scale = activity!!.resources.displayMetrics.density
        val adWidth = ((screenWidth - resources.getDimension(R.dimen._20sdp).toInt()) / scale).toInt()

        adView?.adSize = AdSize(adWidth, 132)
        adView?.adUnitId = "ca-app-pub-2754939326809910/2852662581"

        adView?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                adCardView.visibility = View.VISIBLE
            }
        }

        adView?.loadAd(AdRequest.Builder().build())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adView?.destroy()
    }

}
