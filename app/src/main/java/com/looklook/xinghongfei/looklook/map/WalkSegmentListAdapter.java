package com.looklook.xinghongfei.looklook.map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.route.WalkStep;
import com.looklook.xinghongfei.looklook.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lin on 2017/3/5.
 */
public class WalkSegmentListAdapter extends BaseAdapter
{
    private Context mContext;
    private List<WalkStep> mItemList = new ArrayList();

    public WalkSegmentListAdapter(Context paramContext, List<WalkStep> paramList)
    {
        this.mContext = paramContext;
        this.mItemList.add(new WalkStep());
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
        {
            WalkStep localWalkStep = (WalkStep)localIterator.next();
            this.mItemList.add(localWalkStep);
        }
        this.mItemList.add(new WalkStep());
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
            localViewHolder.lineName = ((TextView)paramView.findViewById(R.id.bus_line_name));
            localViewHolder.dirIcon = ((ImageView)paramView.findViewById(R.id.bus_dir_icon));
            localViewHolder.dirUp = ((ImageView)paramView.findViewById(R.id.bus_dir_icon_up));
            localViewHolder.dirDown = ((ImageView)paramView.findViewById(R.id.bus_dir_icon_down));
            localViewHolder.splitLine = ((ImageView)paramView.findViewById(R.id.bus_seg_split_line));
            paramView.setTag(localViewHolder);
        }
        else {
            localViewHolder = (ViewHolder)paramView.getTag();

        }
        WalkStep localWalkStep = (WalkStep)this.mItemList.get(paramInt);
            if (paramInt == 0){
            localViewHolder.dirIcon.setImageResource(R.drawable.dir_start);
            localViewHolder.lineName.setText("出发");
            localViewHolder.dirUp.setVisibility(View.GONE);
            localViewHolder.dirDown.setVisibility(View.VISIBLE);
            localViewHolder.splitLine.setVisibility(View.GONE);
            return paramView;
            }

        if (paramInt == -1 + this.mItemList.size())
        {
            localViewHolder.dirIcon.setImageResource(R.drawable.dir_end);
            localViewHolder.lineName.setText("到达终点");
            localViewHolder.dirUp.setVisibility(View.VISIBLE);
            localViewHolder.dirDown.setVisibility(View.GONE);
            return paramView;
        }
        localViewHolder.splitLine.setVisibility(View.VISIBLE);
        localViewHolder.dirUp.setVisibility(View.VISIBLE);
        localViewHolder.dirDown.setVisibility(View.VISIBLE);
        int i = AMapUtil.getWalkActionID(localWalkStep.getAction());
        localViewHolder.dirIcon.setImageResource(i);
        localViewHolder.lineName.setText(localWalkStep.getInstruction());
        return paramView;
    }

    private class ViewHolder
    {
        ImageView dirDown;
        ImageView dirIcon;
        ImageView dirUp;
        TextView lineName;
        ImageView splitLine;

        private ViewHolder()
        {
        }
    }
}
