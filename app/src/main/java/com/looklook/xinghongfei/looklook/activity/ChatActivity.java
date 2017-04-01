package com.looklook.xinghongfei.looklook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.looklook.xinghongfei.looklook.R;
import com.looklook.xinghongfei.looklook.huanxin.EaseChatFragment;
import com.looklook.xinghongfei.looklook.huanxin.EaseConstant;

import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String userID = intent.getStringExtra(EaseConstant.EXTRA_USER_ID);

        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, userID);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.chatfragment, chatFragment).commit();
    }
}
