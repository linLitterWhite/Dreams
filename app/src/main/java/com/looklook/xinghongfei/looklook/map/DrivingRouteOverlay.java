package com.looklook.xinghongfei.looklook.map;

import android.content.Context;
import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.TMC;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lin on 2017/3/5.
 */
public class DrivingRouteOverlay extends RouteOverlay
{
    private DrivePath drivePath;
    private boolean isColorfulline = true;
    private Context mContext;
    private List<LatLng> mLatLngsOfPath;
    private PolylineOptions mPolylineOptions;
    private PolylineOptions mPolylineOptionscolor;
    private float mWidth = 25.0F;
    private List<LatLonPoint> throughPointList;
    private List<Marker> throughPointMarkerList = new ArrayList();
    private boolean throughPointMarkerVisible = true;
    private List<TMC> tmcs;

    public DrivingRouteOverlay(Context paramContext, AMap paramAMap, DrivePath paramDrivePath, LatLonPoint paramLatLonPoint1, LatLonPoint paramLatLonPoint2, List<LatLonPoint> paramList)
    {
        super(paramContext);
        this.mContext = paramContext;
        this.mAMap = paramAMap;
        this.drivePath = paramDrivePath;
        this.startPoint = AMapUtil.convertToLatLng(paramLatLonPoint1);
        this.endPoint = AMapUtil.convertToLatLng(paramLatLonPoint2);
        this.throughPointList = paramList;
    }

    private void addDrivingStationMarkers(DriveStep paramDriveStep, LatLng paramLatLng)
    {
        addStationMarker(new MarkerOptions().position(paramLatLng).title("方向:" + paramDriveStep.getAction() + "\n道路:" + paramDriveStep.getRoad()).snippet(paramDriveStep.getInstruction()).visible(this.nodeIconVisible).anchor(0.5F, 0.5F).icon(getDriveBitmapDescriptor()));
    }

    private void addThroughPointMarker()
    {
        if ((this.throughPointList != null) && (this.throughPointList.size() > 0))
            for (int i = 0; i < this.throughPointList.size(); i++)
            {
                LatLonPoint localLatLonPoint = (LatLonPoint)this.throughPointList.get(i);
                if (localLatLonPoint != null)
                    this.throughPointMarkerList.add(this.mAMap.addMarker(new MarkerOptions().position(new LatLng(localLatLonPoint.getLatitude(), localLatLonPoint.getLongitude())).visible(this.throughPointMarkerVisible).icon(getThroughPointBitDes()).title("途经点")));
            }
    }

    public static int calculateDistance(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
    {
        double d1 = paramDouble1 * 0.01745329251994329D;
        double d2 = paramDouble2 * 0.01745329251994329D;
        double d3 = paramDouble3 * 0.01745329251994329D;
        double d4 = paramDouble4 * 0.01745329251994329D;
        double d5 = Math.sin(d1);
        double d6 = Math.sin(d2);
        double d7 = Math.cos(d1);
        double d8 = Math.cos(d2);
        double d9 = Math.sin(d3);
        double d10 = Math.sin(d4);
        double d11 = Math.cos(d3);
        double d12 = Math.cos(d4);
        double[] arrayOfDouble = new double[3];
        arrayOfDouble[0] = (d8 * d7 - d12 * d11);
        arrayOfDouble[1] = (d8 * d5 - d12 * d9);
        arrayOfDouble[2] = (d6 - d10);
        return (int)(12742001.579854401D * Math.asin(Math.sqrt(arrayOfDouble[0] * arrayOfDouble[0] + arrayOfDouble[1] * arrayOfDouble[1] + arrayOfDouble[2] * arrayOfDouble[2]) / 2.0D));
    }

    public static int calculateDistance(LatLng paramLatLng1, LatLng paramLatLng2)
    {
        return calculateDistance(paramLatLng1.longitude, paramLatLng1.latitude, paramLatLng2.longitude, paramLatLng2.latitude);
    }

    private void colorWayUpdate(List<TMC> paramList)
    {
        if (this.mAMap == null);
        while ((paramList == null) || (paramList.size() <= 0))
            return;
        this.mPolylineOptionscolor = null;
        this.mPolylineOptionscolor = new PolylineOptions();
        this.mPolylineOptionscolor.width(getRouteWidth());
        ArrayList localArrayList = new ArrayList();
        this.mPolylineOptionscolor.add(this.startPoint);
        this.mPolylineOptionscolor.add(AMapUtil.convertToLatLng((LatLonPoint)((TMC)paramList.get(0)).getPolyline().get(0)));
        localArrayList.add(Integer.valueOf(getDriveColor()));
        for (int i = 0; i < paramList.size(); i++)
        {
            TMC localTMC = (TMC)paramList.get(i);
            int j = getcolor(localTMC.getStatus());
            List localList = localTMC.getPolyline();
            for (int k = 1; k < localList.size(); k++)
            {
                this.mPolylineOptionscolor.add(AMapUtil.convertToLatLng((LatLonPoint)localList.get(k)));
                localArrayList.add(Integer.valueOf(j));
            }
        }
        this.mPolylineOptionscolor.add(this.endPoint);
        localArrayList.add(Integer.valueOf(getDriveColor()));
        this.mPolylineOptionscolor.colorValues(localArrayList);
    }

    public static LatLng getPointForDis(LatLng paramLatLng1, LatLng paramLatLng2, double paramDouble)
    {
        double d = paramDouble / calculateDistance(paramLatLng1, paramLatLng2);
        return new LatLng(d * (paramLatLng2.latitude - paramLatLng1.latitude) + paramLatLng1.latitude, d * (paramLatLng2.longitude - paramLatLng1.longitude) + paramLatLng1.longitude);
    }

    private BitmapDescriptor getThroughPointBitDes()
    {
        return BitmapDescriptorFactory.fromResource(2130837593);
    }

    private int getcolor(String paramString)
    {
        if (paramString.equals("畅通"))
            return -16711936;
        if (paramString.equals("缓行"))
            return -256;
        if (paramString.equals("拥堵"))
            return -65536;
        if (paramString.equals("严重拥堵"))
            return Color.parseColor("#990033");
        return Color.parseColor("#537edc");
    }

    private void initPolylineOptions()
    {
        this.mPolylineOptions = null;
        this.mPolylineOptions = new PolylineOptions();
        this.mPolylineOptions.color(getDriveColor()).width(getRouteWidth());
    }

    private void showPolyline()
    {
        addPolyLine(this.mPolylineOptions);
    }

    private void showcolorPolyline()
    {
        addPolyLine(this.mPolylineOptionscolor);
    }

    public void addToMap()
    {
        initPolylineOptions();
        try
        {
            if (this.mAMap == null)
                return;
            if ((this.mWidth == 0.0F) || (this.drivePath == null))
                return;
            this.mLatLngsOfPath = new ArrayList();
            this.tmcs = new ArrayList();
            List localList1 = this.drivePath.getSteps();
            this.mPolylineOptions.add(this.startPoint);
            Iterator localIterator1 = localList1.iterator();
            while (localIterator1.hasNext())
            {
                DriveStep localDriveStep = (DriveStep)localIterator1.next();
                List localList2 = localDriveStep.getPolyline();
                List localList3 = localDriveStep.getTMCs();
                this.tmcs.addAll(localList3);
                addDrivingStationMarkers(localDriveStep, convertToLatLng((LatLonPoint)localList2.get(0)));
                Iterator localIterator2 = localList2.iterator();
                while (localIterator2.hasNext())
                {
                    LatLonPoint localLatLonPoint = (LatLonPoint)localIterator2.next();
                    this.mPolylineOptions.add(convertToLatLng(localLatLonPoint));
                    this.mLatLngsOfPath.add(convertToLatLng(localLatLonPoint));
                }
            }
        }
        catch (Throwable localThrowable)
        {
            localThrowable.printStackTrace();
            return;
        }
        this.mPolylineOptions.add(this.endPoint);
        if (this.startMarker != null)
        {
            this.startMarker.remove();
            this.startMarker = null;
        }
        if (this.endMarker != null)
        {
            this.endMarker.remove();
            this.endMarker = null;
        }
        addStartAndEndMarker();
        addThroughPointMarker();
        if ((this.isColorfulline) && (this.tmcs.size() > 0))
        {
            colorWayUpdate(this.tmcs);
            showcolorPolyline();
            return;
        }
        showPolyline();
    }

    public LatLng convertToLatLng(LatLonPoint paramLatLonPoint)
    {
        return new LatLng(paramLatLonPoint.getLatitude(), paramLatLonPoint.getLongitude());
    }

    protected LatLngBounds getLatLngBounds()
    {
        LatLngBounds.Builder localBuilder = LatLngBounds.builder();
        localBuilder.include(new LatLng(this.startPoint.latitude, this.startPoint.longitude));
        localBuilder.include(new LatLng(this.endPoint.latitude, this.endPoint.longitude));
        if ((this.throughPointList != null) && (this.throughPointList.size() > 0))
            for (int i = 0; i < this.throughPointList.size(); i++)
                localBuilder.include(new LatLng(((LatLonPoint)this.throughPointList.get(i)).getLatitude(), ((LatLonPoint)this.throughPointList.get(i)).getLongitude()));
        return localBuilder.build();
    }

    public float getRouteWidth()
    {
        return this.mWidth;
    }

    public void removeFromMap()
    {
        try
        {
            super.removeFromMap();
            if ((this.throughPointMarkerList != null) && (this.throughPointMarkerList.size() > 0))
            {
                for (int i = 0; i < this.throughPointMarkerList.size(); i++)
                    ((Marker)this.throughPointMarkerList.get(i)).remove();
                this.throughPointMarkerList.clear();
            }
            return;
        }
        catch (Throwable localThrowable)
        {
            localThrowable.printStackTrace();
        }
    }

    public void setIsColorfulline(boolean paramBoolean)
    {
        this.isColorfulline = paramBoolean;
    }

    public void setRouteWidth(float paramFloat)
    {
        this.mWidth = paramFloat;
    }

    public void setThroughPointIconVisibility(boolean paramBoolean)
    {
        try
        {
            this.throughPointMarkerVisible = paramBoolean;
            if ((this.throughPointMarkerList != null) && (this.throughPointMarkerList.size() > 0))
                for (int i = 0; i < this.throughPointMarkerList.size(); i++)
                    ((Marker)this.throughPointMarkerList.get(i)).setVisible(paramBoolean);
        }
        catch (Throwable localThrowable)
        {
            localThrowable.printStackTrace();
        }
    }
}
