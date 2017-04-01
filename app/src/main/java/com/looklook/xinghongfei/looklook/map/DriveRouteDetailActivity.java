package com.looklook.xinghongfei.looklook.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.TMC;
import com.looklook.xinghongfei.looklook.R;

import java.util.List;

/**
 * Created by lin on 2017/3/5.
 */
public class DriveRouteDetailActivity extends Activity
{
    private TextView mDesDriveRoute;
    private DrivePath mDrivePath;
    private DriveRouteResult mDriveRouteResult;
    private ListView mDriveSegmentList;
    private DriveSegmentListAdapter mDriveSegmentListAdapter;
    private TextView mTitle;
    private TextView mTitleDriveRoute;

    private void configureListView()
    {
        this.mDriveSegmentList = ((ListView)findViewById(R.id.bus_segment_list));
        this.mDriveSegmentListAdapter = new DriveSegmentListAdapter(getApplicationContext(), this.mDrivePath.getSteps());
        this.mDriveSegmentList.setAdapter(this.mDriveSegmentListAdapter);
    }

    private void getIntentData()
    {
        Intent localIntent = getIntent();
        if (localIntent == null) {
            return;
        }
        else
        {

            this.mDrivePath = ((DrivePath)localIntent.getParcelableExtra("drive_path"));
            this.mDriveRouteResult = ((DriveRouteResult)localIntent.getParcelableExtra("drive_result"));
            for (int i = 0; i < this.mDrivePath.getSteps().size(); i++)
            {
                List localList = ((DriveStep)this.mDrivePath.getSteps().get(i)).getTMCs();
                for (int j = 0; j < localList.size(); j++)
                {
                    String str = "" + ((TMC)localList.get(j)).getPolyline().size();
                    Log.i("MY", str + ((TMC)localList.get(j)).getStatus() + ((TMC)localList.get(j)).getDistance() + ((TMC)localList.get(j)).getPolyline().toString());
                }
            }
        }
    }

    private void init()
    {
        this.mTitle = ((TextView)findViewById(R.id.title_center));
        this.mTitleDriveRoute = ((TextView)findViewById(R.id.firstline));
        this.mDesDriveRoute = ((TextView)findViewById(R.id.secondline));
        this.mTitle.setText("驾车路线详情");
        String str1 = AMapUtil.getFriendlyTime((int)this.mDrivePath.getDuration());
        String str2 = AMapUtil.getFriendlyLength((int)this.mDrivePath.getDistance());
        this.mTitleDriveRoute.setText(str1 + "(" + str2 + ")");
        int i = (int)this.mDriveRouteResult.getTaxiCost();
        this.mDesDriveRoute.setText("打车约" + i + "元");
        this.mDesDriveRoute.setVisibility(View.GONE);
        configureListView();
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
        init();
    }
}
