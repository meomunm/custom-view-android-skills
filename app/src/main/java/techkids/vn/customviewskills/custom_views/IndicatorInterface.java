package techkids.vn.customviewskills.custom_views;

import android.support.v4.view.ViewPager;

import techkids.vn.customviewskills.exceptions.PagesLessException;

public interface IndicatorInterface {
    void setViewPager(ViewPager viewPager) throws PagesLessException;

    void setAnimationDuration(long duration);

    /*
     *
     * @param radius: radius in pixcel
     * */
    void setRadiusSelected(int radius);

    /*
     *
     * @param radius: radius in pixcel
     * */
    void setRadiusUnselected(int radius);

    /*
     *
     * @param distance: distance in pixcel
     * */
    void setDistanceDot(int distance);
}
