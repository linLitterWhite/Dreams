package com.looklook.xinghongfei.looklook.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.route.WalkPath;
import com.looklook.xinghongfei.looklook.R;

/**
 * Created by lin on 2017/3/5.
 */
public class WalkRouteDetailActivity extends Activity
{
    private TextView mTitle;
    private TextView mTitleWalkRoute;
    private WalkPath mWalkPath;
    private ListView mWalkSegmentList;
    private WalkSegmentListAdapter mWalkSegmentListAdapter;

    private void getIntentData()
    {
        Intent localIntent = getIntent();
        if (localIntent == null)
            return;
        this.mWalkPath = ((WalkPath)localIntent.getParcelableExtra("walk_path"));
    }

    public void onBackClick(View paramView)
    {
        finish();
    }

    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_route_detail);
        getIntentData();
        this.mTitle = ((TextView)findViewById(R.id.title_center));
        this.mTitle.setText("步行路线详情");
        this.mTitleWalkRoute = ((TextView)findViewById(R.id.firstline));
        String str1 = AMapUtil.getFriendlyTime((int)this.mWalkPath.getDuration());
        String str2 = AMapUtil.getFriendlyLength((int)this.mWalkPath.getDistance());
        this.mTitleWalkRoute.setText(str1 + "(" + str2 + ")");
        this.mWalkSegmentList = ((ListView)findViewById(R.id.bus_segment_list));
        this.mWalkSegmentListAdapter = new WalkSegmentListAdapter(getApplicationContext(), this.mWalkPath.getSteps());
        this.mWalkSegmentList.setAdapter(this.mWalkSegmentListAdapter);
    }
}