package in.prasilabs.fcare.modules.login.splash;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.prasilabs.fcare.R;

/**
 * Created by Contus team on 31/8/17.
 */

public class SplashViewPagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return 3; //return views count.
    }

    @Override
    public Object instantiateItem(final ViewGroup collection, int position) {
        View view;

        //Based on the position show step by step view.
        if (position == 0) {
            view = LayoutInflater.from(collection.getContext()).inflate(R.layout.view_welcome_splash, collection, false);
        } else if (position == 1) {
            view = LayoutInflater.from(collection.getContext()).inflate(R.layout.view_location_splash, collection, false);
        } else {
            view = LayoutInflater.from(collection.getContext()).inflate(R.layout.view_care_splash, collection, false);
        }

        //Add the views to collection for showing it in view pager.
        collection.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
