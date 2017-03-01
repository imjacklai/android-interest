package tw.ctl.interest;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tw.ctl.interest.calculation.CalculationFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)    Toolbar toolbar;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.tab_layout) TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

        /** Set each tab view. */
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab == null) continue;
            tab.setCustomView(getTabView(i));
        }
    }

    private View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab, tabLayout, false);
        TabViewHolder viewHolder = new TabViewHolder(view);
        viewHolder.tabTitle.setText(position == 0 ? "計算" : (position == 1 ? "歷史" : "資訊"));
        Drawable drawable = ContextCompat
                .getDrawable(this, position == 0 ? R.drawable.tab_calculator :
                        (position == 1 ? R.drawable.tab_history : R.drawable.tab_info));
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        viewHolder.tabTitle.setCompoundDrawables(null, drawable, null, null);
        return view;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return new HistoryFragment();
                case 2:
                    return new InfoFragment();
                default:
                    return new CalculationFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

    class TabViewHolder {

        @BindView(R.id.tab_title) TextView tabTitle;

        TabViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
