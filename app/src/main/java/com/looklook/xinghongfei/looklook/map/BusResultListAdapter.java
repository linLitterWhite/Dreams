package com.looklook.xinghongfei.looklook.map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.looklook.xinghongfei.looklook.R;

import java.util.List;

/**
 * Created by lin on 2017/3/5.
 */
public class BusResultListAdapter extends BaseAdapter
{
    private List<BusPath> mBusPathList;
    private BusRouteResult mBusRouteResult;
    private Context mContext;

    public BusResultListAdapter(Context paramContext, BusRouteResult paramBusRouteResult)
    {
        this.mContext = paramContext;
        this.mBusRouteResult = paramBusRouteResult;
        this.mBusPathList = paramBusRouteResult.getPaths();
    }

    public int getCount()
    {
        return this.mBusPathList.size();
    }

    public Object getItem(int paramInt)
    {
        return this.mBusPathList.get(paramInt);
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
            paramView = View.inflate(this.mContext, R.layout.item_bus_result, null);
            localViewHolder.title = ((TextView)paramView.findViewById(R.id.bus_path_title));
            localViewHolder.des = ((TextView)paramView.findViewById(R.id.bus_path_des));
            paramView.setTag(localViewHolder);
        }else {
            localViewHolder = (ViewHolder) paramView.getTag();
        }
            BusPath localBusPath = (BusPath)this.mBusPathList.get(paramInt);
            localViewHolder.title.setText(AMapUtil.getBusPathTitle(localBusPath));
            localViewHolder.des.setText(AMapUtil.getBusPathDes(localBusPath));
            paramView.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View paramAnonymousView)
                {
                }
            });


        return paramView;

    }

    private class ViewHolder
    {
        TextView des;
        TextView title;

        private ViewHolder()
        {
        }
    }
}