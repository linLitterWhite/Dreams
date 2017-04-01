package com.looklook.xinghongfei.looklook.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.looklook.xinghongfei.looklook.R;


/**
 * Created by Administrator on 2016/11/8.
 */

public class MyStarMenu extends ViewGroup implements View.OnClickListener {

    private static final int POS_LEFT_TOP = 0;
    private static final int POS_RIGHT_TOP = 1;
    private static final int POS_RIGHT_BOTTOM = 2;
    private static final int POS_LEFT_BOTTOM = 3;




    private enum Positon{
        LEFT_TOP , LEFT_BOTTOM , RIGHT_TOP , RIGHT_BOTTOM

    };
    private enum Start{
        OPEN , CLOSE
    }

    private View cButton;




    private Positon positon=Positon.RIGHT_BOTTOM;
    private Start start = Start.CLOSE;

    private int radius;

    private OnMenuItemClickListener mMenuItemClickListener;



    //对外抛出一个接口
    public interface  OnMenuItemClickListener{
        void onClick(View v, int pos);
    }

    public void setmMenuItemClickListener(OnMenuItemClickListener mMenuItemClickListener) {
        this.mMenuItemClickListener = mMenuItemClickListener;
    }

    public MyStarMenu(Context context) {
        this(context,null);
    }

    public MyStarMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyStarMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Right_Menu_button,defStyleAttr,0);
        int pos = a.getInt(R.styleable.Right_Menu_button_position,POS_RIGHT_BOTTOM);
        switch (pos){
            case POS_LEFT_TOP:
                positon = Positon.LEFT_TOP ;
                break;
            case POS_RIGHT_TOP :
                positon = Positon.RIGHT_TOP ;
                break;
            case POS_RIGHT_BOTTOM :
                positon = Positon.RIGHT_BOTTOM ;
                break;
            case POS_LEFT_BOTTOM :
                positon = Positon.LEFT_BOTTOM ;
                break;

        }


        radius = (int) a.getDimension(R.styleable.Right_Menu_button_radius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics()));
        Log.d("TAG","pos="+positon+",radius="+radius);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count = getChildCount();
        for(int i=0;i<count;i++){
            measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if(changed){
            layoutcButton();

            itemButton();
        }

    }



    private void layoutcButton() {
        cButton = getChildAt(0);
        cButton.setOnClickListener(this);

        int l = 0;
        int t = 0;

        int width = cButton.getMeasuredWidth();
        int height = cButton.getMeasuredHeight();


        switch (positon){
            case LEFT_TOP :
                l = 0;
                t = 0;
                break;
            case RIGHT_TOP :
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case LEFT_BOTTOM :
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case RIGHT_BOTTOM :
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }
        cButton.layout(l,t,l+width,t+height);
    }


    private void itemButton() {
        int count = getChildCount();

        for(int i=0;i<count-1;i++){
            Log.d("TAG","i="+i);
            View itemButton = getChildAt(i+1);
            //itemButton.setVisibility(View.VISIBLE);

            itemButton.setVisibility(GONE);


            int itemButton_width = itemButton.getMeasuredWidth();
            int itemButton_height = itemButton.getMeasuredHeight();

            int cl = (int) (radius * Math.sin(Math.PI/2 / (count-2) * i ));
            int ct = (int) (radius * Math.cos(Math.PI/2 / (count-2) * i));

            if(positon == Positon.RIGHT_TOP || positon == Positon.RIGHT_BOTTOM){
                cl = getMeasuredWidth() - itemButton_width - cl;
            }
            if(positon == Positon.LEFT_BOTTOM || positon == Positon.RIGHT_BOTTOM){
                ct = getMeasuredHeight() - itemButton_height - ct;
            }

            itemButton.layout(cl,ct,cl+itemButton_width,ct+itemButton_height);

        }
    }
    @Override
    public void onClick(View v) {

        rotateCButton(v, 0f, 360f, 300);
        itemButtonAnimat(300);
        changeButtonState();

    }



    private void itemButtonAnimat(int time) {
        int count = getChildCount();
        for(int i=0;i<count-1;i++){
            final View itemButton = getChildAt(i+1);
            itemButton.setVisibility(View.VISIBLE);

            AnimationSet animaset = new AnimationSet(true);
            //平移动画
            TranslateAnimation tranima = null;
            //透明动画
            AlphaAnimation alphaanima = null;


            int xflag = 1;
            int yflag = 1;

            int cl = (int) (radius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (radius * Math.cos(Math.PI / 2 / (count - 2) * i));

            if(positon == Positon.LEFT_TOP || positon == Positon.LEFT_BOTTOM){
                xflag = -1;
            }
            if(positon == Positon.LEFT_TOP || positon == Positon.RIGHT_TOP){
                yflag = -1;
            }

            if(start == Start.OPEN) {
                alphaanima = new AlphaAnimation(1f,0f);
                tranima = new TranslateAnimation(0, xflag * cl, 0, yflag * ct);
                itemButton.setClickable(true);
                itemButton.setFocusable(true);
            }else {
                alphaanima = new AlphaAnimation(0f,1.0f);
                tranima = new TranslateAnimation(xflag * cl, 0, yflag * ct,0);
                itemButton.setClickable(false);
                itemButton.setFocusable(false);
            }

            alphaanima.setFillAfter(true);
            alphaanima.setDuration(time);

            tranima.setFillAfter(true);
            tranima.setDuration(time);
            tranima.setStartOffset((i * 100) / count);

            tranima.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(start == Start.CLOSE){
                        itemButton.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            // 旋转动画
            RotateAnimation rotateAnim = new RotateAnimation(0, 720,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnim.setDuration(time);
            rotateAnim.setFillAfter(true);

            animaset.addAnimation(rotateAnim);
            animaset.addAnimation(alphaanima);
            animaset.addAnimation(tranima);

            itemButton.startAnimation(animaset);

            final int pos = i+1;
            itemButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mMenuItemClickListener != null){
                        mMenuItemClickListener.onClick(itemButton,pos);
                    }

                    itembuttonClickAnimat(pos);
                    changeButtonState();
                }
            });


        }

    }

    private void itembuttonClickAnimat(int pos){
        int count = getChildCount();
        for(int i=1;i<count;i++){
            View itemButton = getChildAt(i);
            if(i == pos){
                itemButton.startAnimation(scaleBigAnim(300));
            }else {
                itemButton.startAnimation(scaleSmallAnim(300));
            }
        }
    }

    private Animation scaleSmallAnim(int time) {
        AnimationSet animationset = new AnimationSet(true);
        ScaleAnimation scaleanim = new ScaleAnimation(1.0f,0.0f,1.0f,0.0f,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);

        AlphaAnimation alphaanima =new AlphaAnimation(1.0f,0.0f);

        animationset.addAnimation(scaleanim);
        animationset.addAnimation(alphaanima);

        animationset.setDuration(time);
        animationset.setFillAfter(true);
        return animationset;
    }

    private Animation scaleBigAnim(int time) {
        AnimationSet animationset = new AnimationSet(true);
        ScaleAnimation scaleanim = new ScaleAnimation(1.0f,4.0f,1.0f,4.0f,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);

        AlphaAnimation alphaanima =new AlphaAnimation(1.0f,0.0f);

        animationset.addAnimation(scaleanim);
        animationset.addAnimation(alphaanima);

        animationset.setDuration(time);
        animationset.setFillAfter(true);
        return animationset;
    }

    private void changeButtonState() {
        if(start == Start.CLOSE){
            start = Start.OPEN;

        }else {
            start = Start.CLOSE;
        }
    }

    private void rotateCButton(View v, float start, float end, int time) {

        RotateAnimation anim = new RotateAnimation(start, end,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setDuration(time);
        anim.setFillAfter(true);
        v.startAnimation(anim);
    }
}

