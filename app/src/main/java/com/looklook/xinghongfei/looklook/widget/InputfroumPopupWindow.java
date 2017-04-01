package com.looklook.xinghongfei.looklook.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.huanxin.EaseChatFragment;
import com.looklook.xinghongfei.looklook.huanxin.EaseChatInputMenu;
import com.looklook.xinghongfei.looklook.huanxin.EaseEmojicon;
import com.looklook.xinghongfei.looklook.huanxin.EaseVoiceRecorderView;

import java.util.Map;

public class InputfroumPopupWindow implements View.OnKeyListener {

    private static PopupWindow popupWindow;
    private Context mContext;
    EaseChatInputMenu inputMenu;
    InputMethodManager inputMethodManager;

    public InputfroumPopupWindow(Context paramContext)
    {
        this.mContext = paramContext;
        inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private void initlisten()
    {

        recoverView();
    }


    public PopupWindow getPopupWindow()
    {
        initPopwindow();
        return popupWindow;
    }


    public void initPopwindow()
    {
        View localView = LayoutInflater.from(this.mContext).inflate(R.layout.inputfroum, null, false);
        popupWindow = new PopupWindow(localView, -1, -2);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        inputMenu = (EaseChatInputMenu) localView.findViewById(R.id.input_menu);
        inputMenu.init(null);
        inputMenu.setChatInputMenuListener(new EaseChatInputMenu.ChatInputMenuListener() {

            @Override
            public void onSendMessage(String content) {
                //Toast.makeText(mContext,content,Toast.LENGTH_SHORT).show();
                mComment.comment(content);
                inputMenu.hideExtendMenuContainer();
                popupWindow.dismiss();
                inputMethodManager.hideSoftInputFromWindow(((Activity)mContext).getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                return false;
            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {
                //sendBigExpressionMessage(emojicon.getName(), emojicon.getIdentityCode());
            }
        });

        initlisten();
    }


    public void recoverView()
    {
    }

    private Comment mComment;
    public void setreturninfo(Comment mComment)
    {
        this.mComment = mComment;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (popupWindow != null && popupWindow.isShowing()) {
                inputMenu.hideExtendMenuContainer();
                popupWindow.dismiss();
                inputMethodManager.hideSoftInputFromWindow(((Activity)mContext).getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
                return true;
            }
        }
        return false;
    }


    public  interface Comment
    {
        void comment(String commentcontent);

    }


}
