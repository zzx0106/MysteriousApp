package mytapp.xmz.com.mysteriousapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class Home_FragmentPagerAdapter extends FragmentPagerAdapter {

    private final Fragment[] fragments;
    private final List<String> list;

    public Home_FragmentPagerAdapter(FragmentManager fm, List<String> list, Fragment[] fragments) {
        super(fm);
        this.fragments =fragments;
        this.list =list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
