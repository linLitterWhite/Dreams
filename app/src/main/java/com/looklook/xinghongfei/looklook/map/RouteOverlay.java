package com.looklook.xinghongfei.looklook.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lin on 2017/3/5.
 */
public class RouteOverlay {
    protected List<Polyline> allPolyLines = new ArrayList();
    private Bitmap busBit;
    private Bitmap driveBit;
    private Bitmap endBit;
    protected Marker endMarker;
    protected LatLng endPoint;
    protected AMap mAMap;
    private Context mContext;
    protected boolean nodeIconVisible = true;
    private Bitmap startBit;
    protected Marker startMarker;
    protected LatLng startPoint;
    protected List<Marker> stationMarkers = new ArrayList();
    private Bitmap walkBit;

    public RouteOverlay(Context paramContext)
    {
        this.mContext = paramContext;
    }

    private void destroyBit()
    {
        if (this.startBit != null)
        {
            this.startBit.recycle();
            this.startBit = null;
        }
        if (this.endBit != null)
        {
            this.endBit.recycle();
            this.endBit = null;
        }
        if (this.busBit != null)
        {
            this.busBit.recycle();
            this.busBit = null;
        }
        if (this.walkBit != null)
        {
            this.walkBit.recycle();
            this.walkBit = null;
        }
        if (this.driveBit != null)
        {
            this.driveBit.recycle();
            this.driveBit = null;
        }
    }

    protected void addPolyLine(PolylineOptions paramPolylineOptions)
    {
        Polyline localPolyline;
        if (paramPolylineOptions == null) {
            return;
        }else {
            localPolyline = this.mAMap.addPolyline(paramPolylineOptions);
            this.allPolyLines.add(localPolyline);
        }


    }

    protected void addStartAndEndMarker()
    {
        this.startMarker = this.mAMap.addMarker(new MarkerOptions().position(this.startPoint).icon(getStartBitmapDescriptor()).title("起点"));
        this.endMarker = this.mAMap.addMarker(new MarkerOptions().position(this.endPoint).icon(getEndBitmapDescriptor()).title("终点"));
    }

    protected void addStationMarker(MarkerOptions paramMarkerOptions)
    {
        Marker localMarker;
        if (paramMarkerOptions == null) {
            return;
        }else
        {
            localMarker = this.mAMap.addMarker(paramMarkerOptions);
        }
        this.stationMarkers.add(localMarker);
    }

    protected BitmapDescriptor getBusBitmapDescriptor()
    {
        return BitmapDescriptorFactory.fromResource(2130837587);
    }

    protected int getBusColor()
    {
        return Color.parseColor("#537edc");
    }

    protected BitmapDescriptor getDriveBitmapDescriptor()
    {
        return BitmapDescriptorFactory.fromResource(2130837588);
    }

    protected int getDriveColor()
    {
        return Color.parseColor("#537edc");
    }

    protected BitmapDescriptor getEndBitmapDescriptor()
    {
        return BitmapDescriptorFactory.fromResource(2130837589);
    }

    protected LatLngBounds getLatLngBounds()
    {
        LatLngBounds.Builder localBuilder = LatLngBounds.builder();
        localBuilder.include(new LatLng(this.startPoint.latitude, this.startPoint.longitude));
        localBuilder.include(new LatLng(this.endPoint.latitude, this.endPoint.longitude));
        return localBuilder.build();
    }

    protected float getRouteWidth()
    {
        return 18.0F;
    }

    protected BitmapDescriptor getStartBitmapDescriptor()
    {
        return BitmapDescriptorFactory.fromResource(2130837592);
    }

    protected BitmapDescriptor getWalkBitmapDescriptor()
    {
        return BitmapDescriptorFactory.fromResource(2130837590);
    }

    protected int getWalkColor()
    {
        return Color.parseColor("#6db74d");
    }

    public void removeFromMap()
    {
        if (this.startMarker != null)
            this.startMarker.remove();
        if (this.endMarker != null)
            this.endMarker.remove();
        Iterator localIterator1 = this.stationMarkers.iterator();
        while (localIterator1.hasNext())
            ((Marker)localIterator1.next()).remove();
        Iterator localIterator2 = this.allPolyLines.iterator();
        while (localIterator2.hasNext())
            ((Polyline)localIterator2.next()).remove();
        destroyBit();
    }

    public void setNodeIconVisibility(boolean paramBoolean)
    {
        try
        {
            this.nodeIconVisible = paramBoolean;
            if ((this.stationMarkers != null) && (this.stationMarkers.size() > 0))
                for (int i = 0; i < this.stationMarkers.size(); i++)
                    ((Marker)this.stationMarkers.get(i)).setVisible(paramBoolean);
        }
        catch (Throwable localThrowable)
        {
            localThrowable.printStackTrace();
        }
    }

    public void zoomToSpan()
    {
        if ((this.startPoint == null) || (this.mAMap == null))
            return;
        try
        {
            LatLngBounds localLatLngBounds = getLatLngBounds();
            this.mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(localLatLngBounds, 50));
            return;
        }
        catch (Throwable localThrowable)
        {
            localThrowable.printStackTrace();
        }
    }
}
