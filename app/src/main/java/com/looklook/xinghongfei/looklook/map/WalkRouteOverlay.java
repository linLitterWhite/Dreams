package com.looklook.xinghongfei.looklook.map;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkStep;

import java.util.List;

/**
 * Created by lin on 2017/3/5.
 */
public class WalkRouteOverlay extends RouteOverlay
{
    private PolylineOptions mPolylineOptions;
    private WalkPath walkPath;
    private BitmapDescriptor walkStationDescriptor = null;

    public WalkRouteOverlay(Context paramContext, AMap paramAMap, WalkPath paramWalkPath, LatLonPoint paramLatLonPoint1, LatLonPoint paramLatLonPoint2)
    {
        super(paramContext);
        this.mAMap = paramAMap;
        this.walkPath = paramWalkPath;
        this.startPoint = AMapServicesUtil.convertToLatLng(paramLatLonPoint1);
        this.endPoint = AMapServicesUtil.convertToLatLng(paramLatLonPoint2);
    }

    private void addWalkPolyLine(LatLng paramLatLng1, LatLng paramLatLng2)
    {
        this.mPolylineOptions.add(new LatLng[] { paramLatLng1, paramLatLng2 });
    }

    private void addWalkPolyLine(LatLonPoint paramLatLonPoint1, LatLonPoint paramLatLonPoint2)
    {
        addWalkPolyLine(AMapServicesUtil.convertToLatLng(paramLatLonPoint1), AMapServicesUtil.convertToLatLng(paramLatLonPoint2));
    }

    private void addWalkPolyLines(WalkStep paramWalkStep)
    {
        this.mPolylineOptions.addAll(AMapServicesUtil.convertArrList(paramWalkStep.getPolyline()));
    }

    private void addWalkStationMarkers(WalkStep paramWalkStep, LatLng paramLatLng)
    {
        addStationMarker(new MarkerOptions().position(paramLatLng).title("方向:" + paramWalkStep.getAction() + "\n道路:" + paramWalkStep.getRoad()).snippet(paramWalkStep.getInstruction()).visible(this.nodeIconVisible).anchor(0.5F, 0.5F).icon(this.walkStationDescriptor));
    }

    private void checkDistanceToNextStep(WalkStep paramWalkStep1, WalkStep paramWalkStep2)
    {
        LatLonPoint localLatLonPoint1 = getLastWalkPoint(paramWalkStep1);
        LatLonPoint localLatLonPoint2 = getFirstWalkPoint(paramWalkStep2);
        if (!localLatLonPoint1.equals(localLatLonPoint2))
            addWalkPolyLine(localLatLonPoint1, localLatLonPoint2);
    }

    private LatLonPoint getFirstWalkPoint(WalkStep paramWalkStep)
    {
        return (LatLonPoint)paramWalkStep.getPolyline().get(0);
    }

    private LatLonPoint getLastWalkPoint(WalkStep paramWalkStep)
    {
        return (LatLonPoint)paramWalkStep.getPolyline().get(-1 + paramWalkStep.getPolyline().size());
    }

    private void initPolylineOptions()
    {
        if (this.walkStationDescriptor == null)
            this.walkStationDescriptor = getWalkBitmapDescriptor();
        this.mPolylineOptions = null;
        this.mPolylineOptions = new PolylineOptions();
        this.mPolylineOptions.color(getWalkColor()).width(getRouteWidth());
    }

    private void showPolyline()
    {
        addPolyLine(this.mPolylineOptions);
    }

    public void addToMap()
    {
        initPolylineOptions();
        try
        {
            List localList = this.walkPath.getSteps();
            this.mPolylineOptions.add(this.startPoint);
            for (int i = 0; i < localList.size(); i++)
            {
                WalkStep localWalkStep = (WalkStep)localList.get(i);
                addWalkStationMarkers(localWalkStep, AMapServicesUtil.convertToLatLng((LatLonPoint)localWalkStep.getPolyline().get(0)));
                addWalkPolyLines(localWalkStep);
            }
            this.mPolylineOptions.add(this.endPoint);
            addStartAndEndMarker();
            showPolyline();
            return;
        }
        catch (Throwable localThrowable)
        {
            localThrowable.printStackTrace();
        }
    }
}
