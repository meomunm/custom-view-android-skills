package techkids.vn.customviewskills;

import techkids.vn.customviewskills.animations.AnimationDepthPageTransformer;
import techkids.vn.customviewskills.animations.AnimationZoomOutForViewPager;
import techkids.vn.customviewskills.custom_views.IndicatorView;
import techkids.vn.customviewskills.exceptions.PagesLessException;
import techkids.vn.customviewskills.fragments.*;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

public class ScrollPagerActivity extends FragmentActivity {
    private static final int NUM_PAGES = 4;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private IndicatorView indicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_pager);

        init();
    }

    private void init() {
        indicatorView = findViewById(R.id.indicator);
        viewPager = findViewById(R.id.activity_scroll_view_pager);
        pagerAdapter = new ScrollSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setPageTransformer(true, new AnimationDepthPageTransformer());
        viewPager.setAdapter(pagerAdapter);
        try {
            indicatorView.setViewPager(viewPager);
        } catch (PagesLessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0){
            //neu current item dang o fragment dau tien thi su dung back cua he thong
            //nguoc lai thi di chuyen lui ve fragment truoc do
            super.onBackPressed();
        }else{
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
        }
    }

    private class ScrollSlidePagerAdapter extends FragmentStatePagerAdapter {

        ScrollSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PageOneIndicatorFragment();
                case 1:
                    return new PageTwoIndicatorFragment();
                case 2:
                    return new PageThreeIndicatorFragment();
                case 3:
                    return new PageFourIndicatorFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
