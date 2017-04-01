package com.looklook.xinghongfei.looklook.map;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.WindowManager;

import com.amap.api.maps.model.Marker;

/**
 * Created by lin on 2017/3/5.
 */
public class SensorEventHelper implements SensorEventListener
{
    private final int TIME_SENSOR = 100;
    private long lastTime = 0L;
    private float mAngle;
    private Context mContext;
    private Marker mMarker;
    private Sensor mSensor;
    private SensorManager mSensorManager;

    public SensorEventHelper(Context paramContext)
    {
        this.mContext = paramContext;
        this.mSensorManager = ((SensorManager)paramContext.getSystemService(Context.SENSOR_SERVICE));
        this.mSensor = this.mSensorManager.getDefaultSensor(3);
    }

    public static int getScreenRotationOnPhone(Context paramContext)
    {
        switch (((WindowManager)paramContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation())
        {
            case 0:
                return 0;
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return 270;
        }
        return  0;

    }

    public Marker getCurrentMarker()
    {
        return this.mMarker;
    }

    public void onAccuracyChanged(Sensor paramSensor, int paramInt)
    {
    }

    public void onSensorChanged(SensorEvent paramSensorEvent)
    {
//        if (System.currentTimeMillis() - this.lastTime < 100L);


//            return;
//            switch (paramSensorEvent.sensor.getType())
//            {
//                default:
//                    return;
//                case 3:
//            }
//            float f = (paramSensorEvent.values[0] + getScreenRotationOnPhone(this.mContext)) % 360.0F;
//            if (f > 180.0F)
//                f -= 360.0F;
//            if (Math.abs(this.mAngle - f) >= 3.0F)
//            {
//                if (Float.isNaN(f))
//                    f = 0.0F;
//                this.mAngle = f;
//                if (this.mMarker != null)
//                    this.mMarker.setRotateAngle(360.0F - this.mAngle);
//                this.lastTime = System.currentTimeMillis();
//                return;
//
//            }
//        }
    }

    public void registerSensorListener()
    {
        this.mSensorManager.registerListener(this, this.mSensor, 3);
    }

    public void setCurrentMarker(Marker paramMarker)
    {
        this.mMarker = paramMarker;
    }

    public void unRegisterSensorListener()
    {
        this.mSensorManager.unregisterListener(this, this.mSensor);
    }
}
