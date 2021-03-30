package com.toperc.lifestyle.widget.stepdetector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.toperc.lifestyle.common.Constents;
import com.toperc.lifestyle.util.SharePreferencesUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//走步检测器，用于检测走步并计数
public class StepDetector implements SensorEventListener {

    private int mCurrentSetp = 0; //步数
    private int mLastSetp = 0;
    private float SENSITIVITY = 0;   //SENSITIVITY灵敏度

    private float mLastValues;  //最终的速度
    private float mScale[] = new float[2];   //探测器每次隔多久回调一次。
    private float mYOffset;
    private static long end = 0;
    private static long start = 0;

    //最后的方向，相同还是相反。
    private float mLastDirections;
    private float mLastExtremes[] = new float[2];
    private float mLastDiff;
    private int mLastMatch = -1;

    private Context mContext;
    //创建一个接口集合，并且允许在读取数据的同时向其中添加接口。
    private List<IStepChangedListener> iStepChangedListenerList = new CopyOnWriteArrayList<IStepChangedListener>();

    public static StepDetector stepDetector;
    public static StepDetector getInstence(Context context){
        if (stepDetector==null){
            stepDetector = new StepDetector(context);
        }
        return stepDetector;
    }

    /**
     * 传入上下文的构造函数
     *
     * @param context
     */
    public StepDetector(Context context) {
        mContext = context;
        //屏幕的高
        int h = 480;
        //手机屏幕高的中心
        mYOffset = h * 0.5f;
        mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));

        mLastSetp = StepCounterService.mLastStep;
        mCurrentSetp = mLastSetp;
        SENSITIVITY = (float) SharePreferencesUtil.get(context, Constents.SP_USER, Constents.SENSITIVITY_VALUE, 10f);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        synchronized (this) {
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                //求三个方向上的速度和
                float vSum = 0;
                for (int i = 0; i < 3; i++) {
                    //vt = v0 + at
                    final float v = mYOffset + event.values[i] * mScale[1];
                    vSum += v;
                }
                //获取平均速度
                float v = vSum / 3;
                float direction = (v > mLastValues ? 1 : (v < mLastValues ? -1 : 0));

                //Direction changed
                if (direction == -mLastDirections) {
                    // minumum or maximum?
                    int extType = (direction > 0 ? 0 : 1);
                    mLastExtremes[extType] = mLastValues;
                    float diff = Math.abs(mLastExtremes[extType] - mLastExtremes[1 - extType]);
                    if (diff > SENSITIVITY) {
                        boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff * 2 / 3);
                        boolean isPreviousLargeEnough = mLastDiff > (diff / 3);
                        boolean isNotContra = (mLastMatch != 1 - extType);

                        if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
                            end = System.currentTimeMillis();
                            // 此时判断为走了一步
                            if (end - start > 500) {
                                mCurrentSetp++;
                                mLastMatch = extType;
                                start = end;

                                //循环向每一个接口中发送当前步数
                                for (IStepChangedListener iStepChangedListener: iStepChangedListenerList) {
                                    iStepChangedListener.onStepChanged(mCurrentSetp);
                                }
                            }
                            if (mCurrentSetp - mLastSetp > 10) {
                                SharePreferencesUtil.put(mContext, Constents.SP_USER, Constents.TODAY_SETP, mCurrentSetp);
                                mLastSetp = mCurrentSetp;
                            }
                        } else {
                            mLastMatch = -1;
                        }
                    }
                    mLastDiff = diff;
                }
                mLastDirections = direction;
                mLastValues = v;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    public void setStepChangedListener(IStepChangedListener iStepChangedListener) {
        iStepChangedListenerList.add(iStepChangedListener);
    }
}
