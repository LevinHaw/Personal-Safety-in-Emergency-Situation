package com.example.pses.ShakeServices;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector implements SensorEventListener {


    private static final float SHAKE_THRESHOLD_GRAVITY = 3.7F;
    private static final int SHAKE_SLOP_TIME_MS = 2000;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private OnShakeListener shakeListener;
    private long ShakeTimestamp;
    private int ShakeCount;

    public void setOnShakeListener(OnShakeListener listener) {
        this.shakeListener = listener;
    }

    public interface OnShakeListener {
        public void onShake(int count);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (shakeListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement.
            Float f = new Float(gX * gX + gY * gY + gZ * gZ);
            Double d = Math.sqrt(f.doubleValue());
            float gForce = d.floatValue();

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                final long now = System.currentTimeMillis();
                if (ShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return;
                }

                if (ShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    ShakeCount = 0;
                }

                ShakeTimestamp = now;
                ShakeCount++;

                shakeListener.onShake(ShakeCount);
            }
        }
    }
}