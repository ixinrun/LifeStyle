package com.ixinrun.lifestyle.module_run.main.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 功能描述: 计步器实现类
 * </p>
 *
 * @author ixinrun
 * @data 2021/3/31
 */
public class StepDetector implements SensorEventListener {
    private final static String TAG = "StepDetector";
    private final SensorManager mSensorManager;
    private float mLimit = 10;
    private final float[] mLastValues = new float[3 * 2];
    private final float[] mScale = new float[2];
    private final float mYOffset;

    private final float[] mLastDirections = new float[3 * 2];
    private final float[][] mLastExtremes = {new float[3 * 2], new float[3 * 2]};
    private final float[] mLastDiff = new float[3 * 2];
    private int mLastMatch = -1;

    private StepListener mStepListener;

    public StepDetector(Context mContext) {
        this.mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);

        // 手机屏幕的高
        int h = 480;
        // 中心点，y轴的偏移量
        mYOffset = h * 0.5f;
        // 获取重力相关参数
        mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
    }

    /**
     * 设置灵敏度
     *
     * @param sensitivity 1.97  2.96  4.44  6.66  10.00  15.00  22.50  33.75  50.62
     */
    public void setSensitivity(float sensitivity) {
        mLimit = sensitivity;
    }

    /**
     * 设置走步监听
     *
     * @param sl
     */
    public void setStepListener(StepListener sl) {
        mStepListener = sl;
    }

    /**
     * 启动传感器监听
     */
    public void onStart() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * 停止传感器监听
     */
    public void onStop() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        synchronized (this) {
            int j = (sensor.getType() == Sensor.TYPE_ACCELEROMETER) ? 1 : 0;
            if (j == 1) {
                //三个方向上的速度和
                float vSum = 0;
                for (int i = 0; i < 3; i++) {
                    final float v = mYOffset + event.values[i] * mScale[j];
                    vSum += v;
                }

                int k = 0;
                //平均速度
                float v = vSum / 3;

                float direction = (v > mLastValues[k] ? 1 : (v < mLastValues[k] ? -1 : 0));
                if (direction == -mLastDirections[k]) {
                    // Direction changed
                    // minumum or maximum?
                    int extType = (direction > 0 ? 0 : 1);
                    mLastExtremes[extType][k] = mLastValues[k];
                    float diff = Math.abs(mLastExtremes[extType][k] - mLastExtremes[1 - extType][k]);

                    //运动太慢，忽略
                    if (diff > mLimit) {
                        boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
                        boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
                        boolean isNotContra = (mLastMatch != 1 - extType);

                        //判断有效的一步
                        if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
                            if (mStepListener != null) {
                                mStepListener.onStep();
                            }
                            mLastMatch = extType;
                        } else {
                            mLastMatch = -1;
                        }
                    }
                    mLastDiff[k] = diff;
                }
                mLastDirections[k] = direction;
                mLastValues[k] = v;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    public interface StepListener {
        /**
         * 走步
         */
        void onStep();
    }
}
