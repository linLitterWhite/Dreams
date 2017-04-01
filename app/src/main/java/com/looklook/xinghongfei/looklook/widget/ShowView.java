package com.looklook.xinghongfei.looklook.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.activity.SchoolActivity;
import com.looklook.xinghongfei.looklook.util.DribbbleTarget;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnTouch;

import static com.looklook.xinghongfei.looklook.R.id.school;

/**
 * Created by lin on 2017/2/11.
 */

public class ShowView extends FrameLayout {
    private static final int TIME_INTERVAL = 2000;
    private Context context;
    private List<View> dotViewsList;

    private List<String> imageUrls = new ArrayList<>();
    private List<ImageView> imageViewsList;
    private ViewPager viewPager;
    private String schoolId;
    public void setSchoolId(String schoolId){
        this.schoolId = schoolId;
    }
    public void getImageUrls(){
        imageUrls.clear();
    }

    public void addImageUrls(String imageUrls){
        this.imageUrls.add(imageUrls);

    }

    public ShowView(Context paramContext)
    {
        this(paramContext, null);
    }

    public ShowView(Context paramContext, AttributeSet paramAttributeSet)
    {
        this(paramContext, paramAttributeSet, 0);
    }

    public ShowView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        this.context = paramContext;
        initData();
        initUI(paramContext);
        startPlay();
    }

    private void initData()
    {
        imageUrls.add("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg");
        this.imageViewsList = new ArrayList();
        this.dotViewsList = new ArrayList();
    }

    public void initUI(Context paramContext)
    {
        imageViewsList.clear();
        LayoutInflater.from(paramContext).inflate(R.layout.layout_slideshow, this, true);
        //LinearLayout localLinearLayout = (LinearLayout)findViewById(R.id.dotLayout);
        //localLinearLayout.removeAllViews();
        for (int i = 0; i < this.imageUrls.size(); i++)
        {
            ImageView localImageView1 = new ImageView(paramContext);
            localImageView1.setTag(Integer.valueOf(i));
            //localImageView1.setBackgroundResource(this.imageUrls[i]);

            localImageView1.setScaleType(ImageView.ScaleType.FIT_XY);
            this.imageViewsList.add(localImageView1);
            //ImageView localImageView2 = new ImageView(paramContext);
            LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
            localLayoutParams.leftMargin = 4;
            localLayoutParams.rightMargin = 4;
            //localLinearLayout.addView(localImageView2, localLayoutParams);
           // this.dotViewsList.add(localImageView2);
        }
        this.viewPager = ((ViewPager)findViewById(R.id.viewPager));
        this.viewPager.setFocusable(true);
        this.viewPager.setAdapter(new MyPagerAdapter());
        this.viewPager.setOnPageChangeListener(new MyPageChangeListener());

    }

    private void startPlay()
    {
        this.viewPager.setCurrentItem(Integer.MAX_VALUE/2);
        //this.handler.sendEmptyMessageDelayed(3, 2000L);
    }


    private class MyPageChangeListener
            implements ViewPager.OnPageChangeListener
    {
        private MyPageChangeListener()
        {
        }

        public void onPageScrollStateChanged(int paramInt)
        {
        }

        public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
        {
        }

        public void onPageSelected(int paramInt)
        {

            int i = paramInt % imageUrls.size();
            int j = 0;
            while (j < ShowView.this.dotViewsList.size())
            {

                if (j == i)
                    ((View)ShowView.this.dotViewsList.get(i)).setBackgroundResource(R.mipmap.ic_launcher);
                else
                {
                    ((View)ShowView.this.dotViewsList.get(j)).setBackgroundResource(R.mipmap.main_dot_white);
                }
                j++;
            }
        }
    }

    private class MyPagerAdapter extends PagerAdapter
    {
        private MyPagerAdapter()
        {
        }

        public void destroyItem(View paramView, int paramInt, Object paramObject)
        {
        }

        public void finishUpdate(View paramView)
        {
        }

        public int getCount()
        {
            return Integer.MAX_VALUE;
        }

        public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
        {
            int i = paramInt % ShowView.this.imageViewsList.size();
            if (i < 0)
                i += ShowView.this.imageViewsList.size();
            ImageView localImageView = new ImageView(context);
                    Glide.with(context)
                    .load(imageUrls.get(i))
                    .into(localImageView);
            localImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewParent localViewParent = localImageView.getParent();
            localImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(context, SchoolActivity.class);
                    intent.putExtra("id",schoolId);
                    context.startActivity(intent);
                }
            });
            if (localViewParent != null)
                ((ViewGroup)localViewParent).removeView(localImageView);
            paramViewGroup.addView(localImageView);
            return localImageView;
        }

        public boolean isViewFromObject(View paramView, Object paramObject)
        {
            return paramView == paramObject;
        }

        public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader)
        {
        }

        public Parcelable saveState()
        {
            return null;
        }

        public void startUpdate(View paramView)
        {
        }

}


}
