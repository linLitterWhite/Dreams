package com.looklook.xinghongfei.looklook.map;

import android.text.Html;
import android.text.Spanned;
import android.widget.EditText;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.RouteBusLineItem;
import com.amap.api.services.route.RouteRailwayItem;
import com.looklook.xinghongfei.looklook.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lin on 2017/3/5.
 */
public class AMapUtil
{
    public static final String HtmlBlack = "#000000";
    public static final String HtmlGray = "#808080";

    public static boolean IsEmptyOrNullString(String paramString)
    {
        return (paramString == null) || (paramString.trim().length() == 0);
    }

    public static String checkEditText(EditText paramEditText)
    {
        if ((paramEditText != null) && (paramEditText.getText() != null) && (!paramEditText.getText().toString().trim().equals("")))
            return paramEditText.getText().toString().trim();
        return "";
    }

    public static String colorFont(String paramString1, String paramString2)
    {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append("<font color=").append(paramString2).append(">").append(paramString1).append("</font>");
        return localStringBuffer.toString();
    }

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

    public static String convertToTime(long paramLong)
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(paramLong));
    }

    public static String getBusPathDes(BusPath paramBusPath)
    {
        if (paramBusPath == null)
            return String.valueOf("");
        String str1 = getFriendlyTime((int)paramBusPath.getDuration());
        String str2 = getFriendlyLength((int)paramBusPath.getDistance());
        String str3 = getFriendlyLength((int)paramBusPath.getWalkDistance());
        return String.valueOf(str1 + " | " + str2 + " | 步行" + str3);
    }

    public static String getBusPathTitle(BusPath paramBusPath)
    {
        if (paramBusPath == null)
            return String.valueOf("");
        List localList = paramBusPath.getSteps();
        if (localList == null)
            return String.valueOf("");
        StringBuffer localStringBuffer1 = new StringBuffer();
        Iterator localIterator1 = localList.iterator();
        while (localIterator1.hasNext())
        {
            BusStep localBusStep = (BusStep)localIterator1.next();
            StringBuffer localStringBuffer2 = new StringBuffer();
            if (localBusStep.getBusLines().size() > 0)
            {
                Iterator localIterator2 = localBusStep.getBusLines().iterator();
                while (localIterator2.hasNext())
                {
                    RouteBusLineItem localRouteBusLineItem = (RouteBusLineItem)localIterator2.next();
                    if (localRouteBusLineItem != null)
                    {
                        localStringBuffer2.append(getSimpleBusLineName(localRouteBusLineItem.getBusLineName()));
                        localStringBuffer2.append(" / ");
                    }
                }
                localStringBuffer1.append(localStringBuffer2.substring(0, -3 + localStringBuffer2.length()));
                localStringBuffer1.append(" > ");
            }
            if (localBusStep.getRailway() != null)
            {
                RouteRailwayItem localRouteRailwayItem = localBusStep.getRailway();
                localStringBuffer1.append(localRouteRailwayItem.getTrip() + "(" + localRouteRailwayItem.getDeparturestop().getName() + " - " + localRouteRailwayItem.getArrivalstop().getName() + ")");
                localStringBuffer1.append(" > ");
            }
        }
        return localStringBuffer1.substring(0, -3 + localStringBuffer1.length());
    }

    public static int getDriveActionID(String paramString)
    {
        if ((paramString == null) || (paramString.equals(""))){
            return 2130837615;
        }else
        {
            if ("左转".equals(paramString))
                return R.drawable.dir2;
            if ("右转".equals(paramString))
                return R.drawable.dir1;
            if (("向左前方行驶".equals(paramString)) || ("靠左".equals(paramString)))
                return R.drawable.dir6;
            if (("向右前方行驶".equals(paramString)) || ("靠右".equals(paramString)))
                return R.drawable.dir5;
            if (("向左后方行驶".equals(paramString)) || ("左转调头".equals(paramString)))
                return R.drawable.dir7;
            if ("向右后方行驶".equals(paramString))
                return R.drawable.dir8;
            if (("直行".equals(paramString)) || (!"减速行驶".equals(paramString)));
            return R.drawable.dir3;
        }

    }

    public static String getFriendlyLength(int paramInt)
    {
        if (paramInt > 10000)
        {
            int k = paramInt / 1000;
            return k + "公里";
        }
        if (paramInt > 1000)
        {
            float f = paramInt / 1000.0F;
            String str = new DecimalFormat("##0.0").format(f);
            return str + "公里";
        }
        if (paramInt > 100)
        {
            int j = 50 * (paramInt / 50);
            return j + "米";
        }
        int i = 10 * (paramInt / 10);
        if (i == 0)
            i = 10;
        return i + "米";
    }

    public static String getFriendlyTime(int paramInt)
    {
        if (paramInt > 3600)
        {
            int j = paramInt / 3600;
            int k = paramInt % 3600 / 60;
            return j + "小时" + k + "分钟";
        }
        if (paramInt >= 60)
        {
            int i = paramInt / 60;
            return i + "分钟";
        }
        return paramInt + "秒";
    }

    public static String getSimpleBusLineName(String paramString)
    {
        if (paramString == null)
            return String.valueOf("");
        return paramString.replaceAll("\\(.*?\\)", "");
    }

    public static int getWalkActionID(String paramString)
    {
        if ((paramString == null) || (paramString.equals(""))){
            return 2130837610;
        }else
        {
            if ("左转".equals(paramString))
                return R.drawable.dir2;
            if ("右转".equals(paramString))
                return R.drawable.dir1;
            if (("向左前方".equals(paramString)) || ("靠左".equals(paramString)) || (paramString.contains("向左前方")))
                return R.drawable.dir6;
            if (("向右前方".equals(paramString)) || ("靠右".equals(paramString)) || (paramString.contains("向右前方")))
                return R.drawable.dir5;
            if (("向左后方".equals(paramString)) || (paramString.contains("向左后方")))
                return R.drawable.dir7;
            if (("向右后方".equals(paramString)) || (paramString.contains("向右后方")))
                return R.drawable.dir8;
            if ("直行".equals(paramString))
                return R.drawable.dir3;
            if ("通过人行横道".equals(paramString))
                return R.drawable.dir9;
            if ("通过过街天桥".equals(paramString))
                return R.drawable.dir12;
            if ("通过地下通道".equals(paramString));
            return R.drawable.dir10;
        }

    }

    public static String makeHtmlNewLine()
    {
        return "<br />";
    }

    public static String makeHtmlSpace(int paramInt)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        for (int i = 0; i < paramInt; i++)
            localStringBuilder.append("&nbsp;");
        return localStringBuilder.toString();
    }

    public static Spanned stringToSpan(String paramString)
    {
        if (paramString == null)
            return null;
        return Html.fromHtml(paramString.replace("\n", "<br />"));
    }
}
