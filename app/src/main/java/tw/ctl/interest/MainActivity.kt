package tw.ctl.interest

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
        val tabTitle = view.findViewById(R.id.tabTitle) as TextView
        tabTitle.text = if (position == 0) "試算" else if (position == 1) "紀錄" else "資訊"
        val drawable = ContextCompat.getDrawable(this,
                if (position == 0) R.drawable.tab_calculator
                else if (position == 1) R.drawable.tab_history
                else R.drawable.tab_info)
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
