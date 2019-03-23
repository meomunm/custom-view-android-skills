package techkids.vn.customviewskills.custom_views;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.Objects;

import techkids.vn.customviewskills.R;
import techkids.vn.customviewskills.exceptions.PagesLessException;

public class IndicatorView extends View implements IndicatorInterface, ViewPager.OnPageChangeListener {
    private static final long DEFAULT_ANIMATE_DURATION = 200;
    private static final int DEFAULT_RADIUS_SELECTED = 20;
    private static final int DEFAULT_RADIUS_UNSELECTED = 15;
    private static final int DEFAULT_DISTANCE = 40;
    private ViewPager viewPager;
    private Dot[] dots;
    private long animateDuration = DEFAULT_ANIMATE_DURATION;
    private int radiusSelected = DEFAULT_RADIUS_SELECTED;
    private int radiusUnselected = DEFAULT_RADIUS_UNSELECTED;
    private int distance = DEFAULT_DISTANCE;
    private int colorSelected;
    private int colorUnselected;
    private int currentPosition;
    private int beforePosition;
    private ValueAnimator animatorZoomIn;
    private ValueAnimator animatorZoomOut;


    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        this.radiusSelected = typedArray.getDimensionPixelSize(
                R.styleable.IndicatorView_meomunm_radius_selected
                , DEFAULT_RADIUS_SELECTED);
        this.radiusUnselected = typedArray.getDimensionPixelSize(
                R.styleable.IndicatorView_meomunm_color_unselected
                , DEFAULT_RADIUS_UNSELECTED);
        this.colorUnselected = typedArray.getDimensionPixelSize(
                R.styleable.IndicatorView_meomunm_color_unselected
                , Color.parseColor("#ffffff"));
        this.colorSelected = typedArray.getDimensionPixelSize(
                R.styleable.IndicatorView_meomunm_color_selected
                , Color.parseColor("#ffffff"));
        this.distance = typedArray.getDimensionPixelSize(
                R.styleable.IndicatorView_meomunm_distance
                , DEFAULT_DISTANCE);

        typedArray.recycle();
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredHeight = 2 * radiusSelected;
        int width, height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize= MeasureSpec.getSize(widthMeasureSpec);
        int heightMode= MeasureSpec.getMode(heightMeasureSpec);
        int heightSize= MeasureSpec.getSize(heightMeasureSpec);

        /*
        MeasureSpec.EXACTLY  -> xác định cứng kích thước trong xml
        MeasureSpec.AT_MOST  -> không nên vượt quá giới hạn này
        MeasureSpec.EXACTLY  -> tu do thoa thich, ko bi gioi han
         */
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else if(widthMode == MeasureSpec.AT_MOST){
            width = widthSize;
        }else {
            width = 0;
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else if(heightMode == MeasureSpec.AT_MOST){
            height = Math.min(desiredHeight, heightSize);
        }else {
            height = desiredHeight;
        }
//        desiredHeight là chiều cao mong muốn có được
//        ở trong trường hợp này thì ta cần chiều cao tối thiểu đó là bằng
//        2 lần bán kính chấm tròn lúc được chọn desiredHeight = 2 * radiusSelected
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //xac dinh tam va ban kinh cac dot
        float yCenter = getHeight() / 2; //<chiều cao của view> / 2 -> vì các dot nằm cùng 1 hàng
        int d = distance +  2 * radiusUnselected;   //<khooảng cách giữa các chấm>
        float firstXcenter = (getWidth() / 2) - ((dots.length - 1) * d /2);

        for (int i = 0; i < dots.length; i++){
            dots[i].setCenter(i == 0 ? firstXcenter : firstXcenter + d * i, yCenter);
            dots[i].setCurrentRadius(i == currentPosition ? radiusSelected : radiusUnselected);
            dots[i].setColor(i == currentPosition ? colorSelected : colorUnselected);
            dots[i].setAlpha(i == currentPosition ? 255 : radiusUnselected * 255 / radiusSelected);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Dot dot: dots){
            dot.draw(canvas);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    /*  onPageSelected
        ----------------------------------------------------------------------------------------
        Method này đảm nhiệm việc thực hiện animation khi mà ViewPager thay đổi page
        page được chọn thì chấm tương ứng sẽ phóng to ra
        còn page trước đó được chọn sẽ thu nhỏ lại
        Vì muốn thực hiện 2 thao tác này một lúc nên ta dùng AnimatorSet để kết hợp 2 animation
     */
    @Override
    public void onPageSelected(int position) {
        beforePosition = currentPosition;
        currentPosition = position;

        if (beforePosition == currentPosition){
            beforePosition = currentPosition + 1;
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animateDuration);

        animatorZoomIn = ValueAnimator.ofInt(radiusUnselected, radiusSelected);
        animatorZoomIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int positionPerform = currentPosition;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int newRadius = (int) animation.getAnimatedValue();
                changeNewRadius(positionPerform, newRadius);
            }
        });

        animatorZoomOut = ValueAnimator.ofInt(radiusSelected, radiusUnselected);
        animatorZoomOut.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int positionPerform = beforePosition;
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int newRadius = (int) animation.getAnimatedValue();
                changeNewRadius(positionPerform, newRadius);
            }
        });

        animatorSet.play(animatorZoomIn).with(animatorZoomOut);
        animatorSet.start();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void setViewPager(ViewPager viewPager) throws PagesLessException {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);

        initDot(Objects.requireNonNull(viewPager.getAdapter()).getCount());
        onPageSelected(0);
    }

    private void initDot(int count) throws PagesLessException {
        if (count < 2) throw new PagesLessException();

        dots = new Dot[count];
        for (int i = 0; i < dots.length; i++){
            dots[i] = new Dot();
        }
    }

    @Override
    public void setAnimationDuration(long duration) {
        this.animateDuration = duration;
    }

    @Override
    public void setRadiusSelected(int radius) {
        this.radiusSelected = radius;
    }

    @Override
    public void setRadiusUnselected(int radius) {
        this.radiusUnselected = radius;
    }

    @Override
    public void setDistanceDot(int distance) {
        this.distance = distance;
    }


    /*  changeNewRadius
        Lưu ý: là alpha sẽ là tác nhân bổ trợ cho color
        nên việc setAlpha chỉ được thực hiện sau setColor thì mới có tác dụng
        ------------------------------------------------------------------------------------------
        thay đổi lại chỉ số radius cũ thành mới và thay đổi độ mờ của nó
        sau đó gọi method invalidate() để vẽ lại
        đồng nghĩa với việc method onDraw sẽ được thực hiện
     */
    private void changeNewRadius(int positionPerform, int newRadius) {
        if (dots[positionPerform].getCurrentRadius() != newRadius){
            dots[positionPerform].setCurrentRadius(newRadius);
            dots[positionPerform].setAlpha(newRadius * 255 / radiusSelected);
            invalidate();
        }
    }
}
