package com.looklook.xinghongfei.looklook.map;

import android.graphics.Bitmap;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lin on 2017/3/5.
 */
public class AMapServicesUtil
{
    public static int BUFFER_SIZE = 2048;

    public static ArrayList<LatLng> convertArrList(List<LatLonPoint> paramList)
    {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
            localArrayList.add(convertToLatLng((LatLonPoint)localIterator.next()));
        return localArrayList;
    }

    public static LatLng convertToLatLng(LatLonPoint paramLatLonPoint)
    {
        return new LatLng(paramLatLonPoint.getLatitude(), paramLatLonPoint.getLongitude());
    }

    public static LatLonPoint convertToLatLonPoint(LatLng paramLatLng)
    {
        return new LatLonPoint(paramLatLng.latitude, paramLatLng.longitude);
    }

    public static byte[] inputStreamToByte(InputStream paramInputStream)
            throws IOException
    {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrayOfByte = new byte[BUFFER_SIZE];
        while (true)
        {
            int i = paramInputStream.read(arrayOfByte, 0, BUFFER_SIZE);
            if (i == -1)
                break;
            localByteArrayOutputStream.write(arrayOfByte, 0, i);
        }
        return localByteArrayOutputStream.toByteArray();
    }

    public static Bitmap zoomBitmap(Bitmap paramBitmap, float paramFloat)
    {
        if (paramBitmap == null)
            return null;
        return Bitmap.createScaledBitmap(paramBitmap, (int)(paramFloat * paramBitmap.getWidth()), (int)(paramFloat * paramBitmap.getHeight()), true);
    }
}
