package tw.ctl.interest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import tw.ctl.interest.calculation.CalculationFragment
import tw.ctl.interest.history.HistoryFragment
import tw.ctl.interest.info.InfoFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            selectFragment(item.itemId)
            true
        }

        selectFragment(0)
    }

    private fun selectFragment(itemId: Int) {
        when (itemId) {
            R.id.calculation -> {
                val fragment = supportFragmentManager.findFragmentByTag(CalculationFragment::class.java.simpleName)
                if (fragment == null) {
                    switchFragment(CalculationFragment())
                } else {
                    switchFragment(fragment)
                }
            }
            R.id.history -> {
                val fragment = supportFragmentManager.findFragmentByTag(HistoryFragment::class.java.simpleName)
                if (fragment == null) {
                    switchFragment(HistoryFragment())
                } else {
                    switchFragment(fragment)
                }
            }
            R.id.info -> {
                val fragment = supportFragmentManager.findFragmentByTag(InfoFragment::class.java.simpleName)
                if (fragment == null) {
                    switchFragment(InfoFragment())
                } else {
                    switchFragment(fragment)
                }
            }
            else -> {
                switchFragment(CalculationFragment())
            }
        }
    }

    private fun switchFragment(fragment: Fragment) {
        val fragments = supportFragmentManager.fragments

        val transaction = supportFragmentManager.beginTransaction()

        for (f in fragments) {
            transaction.hide(f)
        }

        if (fragment.isAdded) {
            transaction.show(fragment)
        } else {
            transaction.add(R.id.container, fragment, fragment.javaClass.simpleName)
        }

        transaction.commit()
    }

}
