package tw.ctl.interest

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import tw.ctl.interest.calculation.CalculationFragment
import tw.ctl.interest.history.HistoryFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 2

        tabLayout.setupWithViewPager(viewPager)

        /** Set each tab view. */
        for (i in 0..tabLayout.tabCount - 1) {
            val tab = tabLayout.getTabAt(i) ?: continue
            tab.customView = getTabView(i)
        }
    }

    private fun getTabView(position: Int): View {
        val view = LayoutInflater.from(this).inflate(R.layout.tab, tabLayout, false)
        val tabTitle = view.findViewById<TextView>(R.id.tabTitle)
        val drawable: Drawable

        when (position) {
            0 -> {
                tabTitle.text = "試算"
                drawable = ContextCompat.getDrawable(this, R.drawable.tab_calculator)
            }
            1 -> {
                tabTitle.text = "紀錄"
                drawable = ContextCompat.getDrawable(this, R.drawable.tab_history)
            }
            else -> {
                tabTitle.text = "資訊"
                drawable = ContextCompat.getDrawable(this, R.drawable.tab_info)
            }
        }

        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        tabTitle.setCompoundDrawables(null, drawable, null, null)
        return view
    }

    internal inner class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                1 -> return HistoryFragment()
                2 -> return InfoFragment()
                else -> return CalculationFragment()
            }
        }

        override fun getCount(): Int {
            return 3
        }

    }

}
