package com.looklook.xinghongfei.looklook.map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.looklook.xinghongfei.looklook.R;


import java.util.Iterator;



/**
 * Created by lin on 2017/3/5.
 */

public class MapActivity extends AppCompatActivity
        implements RouteSearch.OnRouteSearchListener, LocationSource, AMapLocationListener
{
    private static final int FILL_COLOR = 0;
    public static final String LOCATION_MARKER_FLAG = "mylocation";
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private final int ROUTE_TYPE_BUS = 1;
    private final int ROUTE_TYPE_CROSSTOWN = 4;
    private final int ROUTE_TYPE_DRIVE = 2;
    private final int ROUTE_TYPE_WALK = 3;
    private AMap aMap;
    private RelativeLayout mBottomLayout;
    private ImageView mBus;
    private LinearLayout mBusResultLayout;
    private ListView mBusResultList;
    private BusRouteResult mBusRouteResult;
    private Circle mCircle;
    private Context mContext;
    private String mCurrentCityName = "北京";
    private ImageView mDrive;
    private DriveRouteResult mDriveRouteResult;
    private LatLonPoint mEndPoint;
    private LatLonPoint mEndPoint_bus = new LatLonPoint(44.433942000000002D, 125.184449D);
    private boolean mFirstFix = false;
    private LocationSource.OnLocationChangedListener mListener;
    private Marker mLocMarker;
    private AMapLocationClientOption mLocationOption;
    private TextView mRotueTimeDes;
    private TextView mRouteDetailDes;
    private RouteSearch mRouteSearch;
    private SensorEventHelper mSensorHelper;
    private LatLonPoint mStartPoint;
    private LatLonPoint mStartPoint_bus = new LatLonPoint(40.818311000000001D, 111.670801D);
    private ImageView mWalk;
    private WalkRouteResult mWalkRouteResult;
    private MapView mapView;
    private AMapLocationClient mlocationClient;
    private ProgressDialog progDialog = null;

    private void addCircle(LatLng paramLatLng, double paramDouble)
    {
        CircleOptions localCircleOptions = new CircleOptions();
        localCircleOptions.strokeWidth(1.0F);
        localCircleOptions.fillColor(FILL_COLOR);
        localCircleOptions.strokeColor(STROKE_COLOR);
        localCircleOptions.center(paramLatLng);
        localCircleOptions.radius(paramDouble);
        this.mCircle = this.aMap.addCircle(localCircleOptions);
    }

    private void addMarker(LatLng paramLatLng)
    {
        if (this.mLocMarker != null)
            return;
        BitmapDescriptor localBitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), 2130837777));
        MarkerOptions localMarkerOptions = new MarkerOptions();
        localMarkerOptions.icon(localBitmapDescriptor);
        localMarkerOptions.anchor(0.5F, 0.5F);
        localMarkerOptions.position(paramLatLng);
        this.mLocMarker = this.aMap.addMarker(localMarkerOptions);
        this.mLocMarker.setTitle("mylocation");
    }

    private void init()
    {
        if (this.aMap == null)
        {
            this.aMap = this.mapView.getMap();
            setUpMap();
        }
        this.mSensorHelper = new SensorEventHelper(this);
        if (this.mSensorHelper != null)
            this.mSensorHelper.registerSensorListener();
        registerListener();
        this.mRouteSearch = new RouteSearch(this);
        this.mRouteSearch.setRouteSearchListener(this);
        this.mBottomLayout = ((RelativeLayout)findViewById(R.id.bottom_layout));
        this.mBusResultLayout = ((LinearLayout)findViewById(R.id.bus_result));
        this.mRotueTimeDes = ((TextView)findViewById(R.id.firstline));
        this.mRouteDetailDes = ((TextView)findViewById(R.id.secondline));
        this.mDrive = ((ImageView)findViewById(R.id.route_drive));
        this.mBus = ((ImageView)findViewById(R.id.route_bus));
        this.mWalk = ((ImageView)findViewById(R.id.route_walk));
        this.mBusResultList = ((ListView)findViewById(R.id.bus_result_list));
    }

    private void registerListener()
    {
    }

    private void setUpMap()
    {
        this.aMap.setLocationSource(this);
        this.aMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.aMap.setMyLocationEnabled(true);
        this.aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    private void setfromandtoMarker()
    {
        this.aMap.addMarker(new MarkerOptions().position(AMapUtil.convertToLatLng(this.mStartPoint)).icon(BitmapDescriptorFactory.fromResource(2130903068)));
        this.aMap.addMarker(new MarkerOptions().position(AMapUtil.convertToLatLng(this.mEndPoint)).icon(BitmapDescriptorFactory.fromResource(2130903050)));
    }

    public void activate(LocationSource.OnLocationChangedListener paramOnLocationChangedListener)
    {
        this.mListener = paramOnLocationChangedListener;
        if (this.mlocationClient == null)
        {
            this.mlocationClient = new AMapLocationClient(this);
            this.mLocationOption = new AMapLocationClientOption();
            this.mlocationClient.setLocationListener(this);
            this.mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            this.mLocationOption.setOnceLocationLatest(true);
            this.mlocationClient.setLocationOption(this.mLocationOption);
            this.mlocationClient.startLocation();
        }
    }

    public void deactivate()
    {
        this.mListener = null;
        if (this.mlocationClient != null)
        {
            this.mlocationClient.stopLocation();
            this.mlocationClient.onDestroy();
        }
        this.mlocationClient = null;
    }

    public void getmEndPoint()
    {
        Intent localIntent = getIntent();
        String str = localIntent.getStringExtra("shcool_longitude");
        this.mEndPoint = new LatLonPoint(Double.parseDouble(localIntent.getStringExtra("school_latitude")), Double.parseDouble(str));
    }

    public void onBusClick(View paramView)
    {
        searchRouteResult(1, 0);
        this.mDrive.setImageResource(R.drawable.route_drive_normal);
        this.mBus.setImageResource(R.drawable.route_bus_select);
        this.mWalk.setImageResource(R.drawable.route_walk_normal);
        this.mapView.setVisibility(View.GONE);
        this.mBusResultLayout.setVisibility(View.VISIBLE);
        this.mBottomLayout.setVisibility(View.GONE);
    }

    public void onBusRouteSearched(BusRouteResult paramBusRouteResult, int paramInt)
    {
        this.aMap.clear();
        if ((paramInt == 1000) && (paramBusRouteResult != null) && (paramBusRouteResult.getPaths() != null))
        {
            if (paramBusRouteResult.getPaths().size() <= 0){
                Log.e("AmapErr", "没有找到路径" );
            }else {
                this.mBusRouteResult = paramBusRouteResult;
                BusResultListAdapter localBusResultListAdapter = new BusResultListAdapter(this, this.mBusRouteResult);
                this.mBusResultList.setAdapter(localBusResultListAdapter);

            }


        }

    }

    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.acitivy_map);
        this.mapView = ((MapView)findViewById(R.id.map));
        this.mapView.onCreate(paramBundle);
        getmEndPoint();
        init();
        this.aMap.getUiSettings().setScaleControlsEnabled(true);
    }

    public void onCrosstownBusClick(View paramView)
    {
        searchRouteResult(4, 0);
        this.mDrive.setImageResource(R.drawable.route_drive_normal);
        this.mBus.setImageResource(R.drawable.route_bus_normal);
        this.mWalk.setImageResource(R.drawable.route_walk_normal);
        this.mapView.setVisibility(View.GONE);
        this.mBusResultLayout.setVisibility(View.VISIBLE);
    }

    protected void onDestroy()
    {
        super.onDestroy();
        this.mapView.onDestroy();
        if (this.mlocationClient != null)
            this.mlocationClient.onDestroy();
    }

    public void onDriveClick(View paramView)
    {
        searchRouteResult(2, 0);
        this.mDrive.setImageResource(R.drawable.route_drive_select);
        this.mBus.setImageResource(R.drawable.route_bus_normal);
        this.mWalk.setImageResource(R.drawable.route_walk_normal);
        this.mapView.setVisibility(View.VISIBLE);
        this.mBusResultLayout.setVisibility(View.GONE);
    }

    public void onDriveRouteSearched(DriveRouteResult paramDriveRouteResult, int paramInt)
    {
        Log.d("path-->","success");
        this.aMap.clear();
        if ((paramInt == 1000) && (paramDriveRouteResult != null) && (paramDriveRouteResult.getPaths() != null) && (paramDriveRouteResult.getPaths().size() > 0))
        {
            this.mDriveRouteResult = paramDriveRouteResult;
            final DrivePath localDrivePath = (DrivePath)this.mDriveRouteResult.getPaths().get(0);
            Log.d("path-->", "path-->" + localDrivePath.toString());
            Iterator localIterator = localDrivePath.getSteps().iterator();
            while (localIterator.hasNext())
            {
                DriveStep localDriveStep = (DriveStep)localIterator.next();
                Log.d("path-->", "path-->" + localDriveStep.getInstruction());
                Log.d("path-->", "path-->" + localDriveStep.getOrientation());
                Log.d("path-->", "path-->" + localDriveStep.getRoad());
            }
            DrivingRouteOverlay localDrivingRouteOverlay = new DrivingRouteOverlay(this, this.aMap, localDrivePath, this.mDriveRouteResult.getStartPos(), this.mDriveRouteResult.getTargetPos(), null);
            localDrivingRouteOverlay.setNodeIconVisibility(false);
            localDrivingRouteOverlay.setIsColorfulline(true);
            localDrivingRouteOverlay.removeFromMap();
            localDrivingRouteOverlay.addToMap();
            localDrivingRouteOverlay.zoomToSpan();
            this.mBottomLayout.setVisibility(View.VISIBLE);
            int i = (int)localDrivePath.getDistance();
            int j = (int)localDrivePath.getDuration();
            String str = AMapUtil.getFriendlyTime(j) + "(" + AMapUtil.getFriendlyLength(i) + ")";
            this.mRotueTimeDes.setText(str);
            this.mRouteDetailDes.setVisibility(View.VISIBLE);
            int k = (int)this.mDriveRouteResult.getTaxiCost();
            this.mRouteDetailDes.setText("打车约" + k + "元");
            this.mBottomLayout.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View paramAnonymousView)
                {
                    Intent localIntent = new Intent(MapActivity.this, DriveRouteDetailActivity.class);
                    localIntent.putExtra("drive_path", localDrivePath);
                    localIntent.putExtra("drive_result", MapActivity.this.mDriveRouteResult);
                    MapActivity.this.startActivity(localIntent);
                }
            });
        }
    }

    public void onLocationChanged(AMapLocation paramAMapLocation)
    {
        LatLng localLatLng;
        if ((this.mListener != null) && (paramAMapLocation != null))
        {
            if ((paramAMapLocation == null) || (paramAMapLocation.getErrorCode() != 0)) {
                Log.e("AmapErr", "定位失败," + paramAMapLocation.getErrorCode() + ": " + paramAMapLocation.getErrorInfo());
                return;
            }
            localLatLng = new LatLng(paramAMapLocation.getLatitude(), paramAMapLocation.getLongitude());
            this.mStartPoint = new LatLonPoint(paramAMapLocation.getLatitude(), paramAMapLocation.getLongitude());
            if (!this.mFirstFix)
            {
                this.mFirstFix = true;
                addCircle(localLatLng, paramAMapLocation.getAccuracy());
                this.mListener.onLocationChanged(paramAMapLocation);
                addMarker(localLatLng);
                this.mSensorHelper.setCurrentMarker(this.mLocMarker);
            }
        }
        else
        {
            return;
        }
        this.mCircle.setCenter(localLatLng);
        this.mCircle.setRadius(paramAMapLocation.getAccuracy());
        this.mLocMarker.setPosition(localLatLng);
        return;
        //label135: Log.e("AmapErr", "定位失败," + paramAMapLocation.getErrorCode() + ": " + paramAMapLocation.getErrorInfo());
    }

    protected void onPause()
    {
        super.onPause();
        if (this.mSensorHelper != null)
        {
            this.mSensorHelper.unRegisterSensorListener();
            this.mSensorHelper.setCurrentMarker(null);
            this.mSensorHelper = null;
        }
        this.mapView.onPause();
        deactivate();
        this.mFirstFix = false;
    }

    protected void onResume()
    {
        super.onResume();
        this.mapView.onResume();
        if (this.mSensorHelper != null) {
            this.mSensorHelper.registerSensorListener();
        }
        else if (this.mSensorHelper == null) {
            this.mSensorHelper = new SensorEventHelper(this);
        }
        this.mSensorHelper.registerSensorListener();

        if ((this.mSensorHelper.getCurrentMarker() != null) || (this.mLocMarker == null));
        this.mSensorHelper.setCurrentMarker(this.mLocMarker);
    }

    public void onRideRouteSearched(RideRouteResult paramRideRouteResult, int paramInt)
    {
    }

    protected void onSaveInstanceState(Bundle paramBundle)
    {
        super.onSaveInstanceState(paramBundle);
        this.mapView.onSaveInstanceState(paramBundle);
    }

    public void onWalkClick(View paramView)
    {
        searchRouteResult(3, 0);
        this.mDrive.setImageResource(R.drawable.route_drive_normal);
        this.mBus.setImageResource(R.drawable.route_bus_normal);
        this.mWalk.setImageResource(R.drawable.route_walk_select);
        this.mapView.setVisibility(View.VISIBLE);
        this.mBusResultLayout.setVisibility(View.GONE);
    }

    public void onWalkRouteSearched(WalkRouteResult paramWalkRouteResult, int paramInt)
    {
        this.aMap.clear();
        if ((paramInt == 1000) && (paramWalkRouteResult != null) && (paramWalkRouteResult.getPaths() != null))
        {
            if (paramWalkRouteResult.getPaths().size() <= 0){
                Log.e("onWalkRouteSearched","找不到步行路径");
                return;
            }

            this.mWalkRouteResult = paramWalkRouteResult;
           final WalkPath localWalkPath = (WalkPath) this.mWalkRouteResult.getPaths().get(0);
            WalkRouteOverlay localWalkRouteOverlay = new WalkRouteOverlay(this, this.aMap, localWalkPath, this.mWalkRouteResult.getStartPos(), this.mWalkRouteResult.getTargetPos());
            localWalkRouteOverlay.removeFromMap();
            localWalkRouteOverlay.addToMap();
            localWalkRouteOverlay.zoomToSpan();
            this.mBottomLayout.setVisibility(View.VISIBLE);
            int i = (int)localWalkPath.getDistance();
            int j = (int)localWalkPath.getDuration();
           String str = AMapUtil.getFriendlyTime(j) + "(" + AMapUtil.getFriendlyLength(i) + ")";
            this.mRotueTimeDes.setText(str);
            this.mRouteDetailDes.setVisibility(View.GONE);
            this.mBottomLayout.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View paramAnonymousView)
                {
                    Intent localIntent = new Intent(MapActivity.this, WalkRouteDetailActivity.class);
                    localIntent.putExtra("walk_path", localWalkPath);
                    localIntent.putExtra("walk_result", MapActivity.this.mWalkRouteResult);
                    MapActivity.this.startActivity(localIntent);
                }
            });
        }
        label200:
        while ((paramWalkRouteResult == null) || (paramWalkRouteResult.getPaths() != null))
        {
            final WalkPath localWalkPath;
            WalkRouteOverlay localWalkRouteOverlay;
            int i;
            int j;
            String str;
            return;
        }
    }



    public void searchRouteResult(int paramInt1, int paramInt2)
    {
        if (this.mStartPoint == null) {
            Toast.makeText(this, "起点未设置", Toast.LENGTH_SHORT).show();
            return;
        }



            if (this.mEndPoint == null) {
                Toast.makeText(this, "终点未设置", Toast.LENGTH_SHORT).show();
                return;
            }
            RouteSearch.FromAndTo localFromAndTo = new RouteSearch.FromAndTo(this.mStartPoint, this.mEndPoint);
            if (paramInt1 == 1)
            {
                RouteSearch.BusRouteQuery localBusRouteQuery1 = new RouteSearch.BusRouteQuery(localFromAndTo, paramInt2, this.mCurrentCityName, 0);
                this.mRouteSearch.calculateBusRouteAsyn(localBusRouteQuery1);
                return;
            }
            if (paramInt1 == 2)
            {
                RouteSearch.DriveRouteQuery localDriveRouteQuery = new RouteSearch.DriveRouteQuery(localFromAndTo, paramInt2, null, null, "");
                this.mRouteSearch.calculateDriveRouteAsyn(localDriveRouteQuery);
                return;
            }
            if (paramInt1 == 3)
            {
                RouteSearch.WalkRouteQuery localWalkRouteQuery = new RouteSearch.WalkRouteQuery(localFromAndTo, paramInt2);
                this.mRouteSearch.calculateWalkRouteAsyn(localWalkRouteQuery);
                return;
            }

        if (paramInt1 == 4) {
            RouteSearch.BusRouteQuery localBusRouteQuery2 = new RouteSearch.BusRouteQuery(new RouteSearch.FromAndTo(this.mStartPoint_bus, this.mEndPoint_bus), paramInt2, "呼和浩特市", 0);
            localBusRouteQuery2.setCityd("农安县");
            this.mRouteSearch.calculateBusRouteAsyn(localBusRouteQuery2);
            return;
        }
    }
}
