package com.looklook.xinghongfei.looklook.map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.route.DriveStep;
import com.looklook.xinghongfei.looklook.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lin on 2017/3/5.
 */
public class DriveSegmentListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<DriveStep> mItemList = new ArrayList();

    public DriveSegmentListAdapter(Context paramContext, List<DriveStep> paramList)
    {
        this.mContext = paramContext;
        this.mItemList.add(new DriveStep());
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
        {
            DriveStep localDriveStep = (DriveStep)localIterator.next();
            this.mItemList.add(localDriveStep);
        }
        this.mItemList.add(new DriveStep());
    }

    public int getCount()
    {
        return this.mItemList.size();
    }

    public Object getItem(int paramInt)
    {
        return this.mItemList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
        ViewHolder localViewHolder;
        if (paramView == null)
        {
            localViewHolder = new ViewHolder();
            paramView = View.inflate(this.mContext, R.layout.item_map_result, null);
            localViewHolder.driveDirIcon = ((ImageView)paramView.findViewById(R.id.bus_dir_icon));
            localViewHolder.driveLineName = ((TextView)paramView.findViewById(R.id.bus_line_name));
            localViewHolder.driveDirUp = ((ImageView)paramView.findViewById(R.id.bus_dir_icon_up));
            localViewHolder.driveDirDown = ((ImageView)paramView.findViewById(R.id.bus_dir_icon_down));
            localViewHolder.splitLine = ((ImageView)paramView.findViewById(R.id.bus_seg_split_line));
            paramView.setTag(localViewHolder);
        }else {
            localViewHolder = (ViewHolder)paramView.getTag();
        }
        DriveStep localDriveStep;

        localDriveStep = (DriveStep)this.mItemList.get(paramInt);
        if (paramInt == 0) {
            localViewHolder.driveDirIcon.setImageResource(R.drawable.dir_start);
            localViewHolder.driveLineName.setText("出发");
            localViewHolder.driveDirUp.setVisibility(View.GONE);
            localViewHolder.driveDirDown.setVisibility(View.VISIBLE);
            localViewHolder.splitLine.setVisibility(View.GONE);
            return paramView;
        }


        if (paramInt == -1 + this.mItemList.size())
        {
            localViewHolder.driveDirIcon.setImageResource(R.drawable.dir_end);
            localViewHolder.driveLineName.setText("到达终点");
            localViewHolder.driveDirUp.setVisibility(View.VISIBLE);
            localViewHolder.driveDirDown.setVisibility(View.GONE);
            localViewHolder.splitLine.setVisibility(View.VISIBLE);
            return paramView;
        }
        int i = AMapUtil.getDriveActionID(localDriveStep.getAction());
        localViewHolder.driveDirIcon.setImageResource(i);
        localViewHolder.driveLineName.setText(localDriveStep.getInstruction());
        localViewHolder.driveDirUp.setVisibility(View.VISIBLE);
        localViewHolder.driveDirDown.setVisibility(View.VISIBLE);
        localViewHolder.splitLine.setVisibility(View.VISIBLE);
        return paramView;
    }

    private class ViewHolder
    {
        ImageView driveDirDown;
        ImageView driveDirIcon;
        ImageView driveDirUp;
        TextView driveLineName;
        ImageView splitLine;

        private ViewHolder()
        {
        }
    }
}