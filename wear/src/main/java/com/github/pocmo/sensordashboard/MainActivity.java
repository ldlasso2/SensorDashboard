package com.github.pocmo.sensordashboard;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.Random;

public class MainActivity extends Activity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    private DeviceClient client;
    private Random random;

    private final static int SENS_ACCELEROMETER = Sensor.TYPE_ACCELEROMETER;
    private final static int SENS_AMBIENT_TEMPERATURE = Sensor.TYPE_AMBIENT_TEMPERATURE;
    private final static int SENS_GAME_ROTATION_VECTOR = Sensor.TYPE_GAME_ROTATION_VECTOR;
    private final static int SENS_GEOMAGNETIC = Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR;
    private final static int SENS_GRAVITY = Sensor.TYPE_GRAVITY;
    private final static int SENS_GYROSCOPE = Sensor.TYPE_GYROSCOPE;
    private final static int SENS_GYROSCOPE_UNCALIBRATED = Sensor.TYPE_GYROSCOPE_UNCALIBRATED;
    private final static int SENS_HEARTRATE = Sensor.TYPE_HEART_RATE;
    private final static int SENS_LIGHT = Sensor.TYPE_LIGHT;
    private final static int SENS_LINEAR_ACCELERATION = Sensor.TYPE_LINEAR_ACCELERATION;
    private final static int SENS_MAGNETIC_FIELD = Sensor.TYPE_MAGNETIC_FIELD;
    private final static int SENS_MAGNETIC_FIELD_UNCALIBRATED = Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED;
    private final static int SENS_PRESSURE = Sensor.TYPE_PRESSURE;
    private final static int SENS_PROXIMITY = Sensor.TYPE_PROXIMITY;
    private final static int SENS_HUMIDITY = Sensor.TYPE_RELATIVE_HUMIDITY;
    private final static int SENS_ROTATION_VECTOR = Sensor.TYPE_ROTATION_VECTOR;
    private final static int SENS_SIGNIFICANT_MOTION = Sensor.TYPE_SIGNIFICANT_MOTION;
    private final static int SENS_STEP_COUNTER = Sensor.TYPE_STEP_COUNTER;
    private final static int SENS_STEP_DETECTOR = Sensor.TYPE_STEP_DETECTOR;


    SensorManager mSensorManager;

    private Sensor mGameRotationVectorSensor;
    private Sensor mAmbientTemperatureSensor;
    private Sensor mAccelerometerSensor;
    private Sensor mGeomagneticSensor;
    private Sensor mGravitySensor;
    private Sensor mGyroscopeSensor;
    private Sensor mGyroscopeUncalibratedSensor;
    private Sensor mHeartrateSensor;
    private long mLasttimestamp;
    private Sensor mRotationSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        random = new Random();
        client = DeviceClient.getInstance(this);

        // TODO: Keep the Wear screen always on (for testing only!)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Sensor and sensor manager
        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));

        mAccelerometerSensor = mSensorManager.getDefaultSensor(SENS_ACCELEROMETER);
        mAmbientTemperatureSensor = mSensorManager.getDefaultSensor(SENS_AMBIENT_TEMPERATURE);
        mGameRotationVectorSensor = mSensorManager.getDefaultSensor(SENS_GAME_ROTATION_VECTOR);
        mGeomagneticSensor = mSensorManager.getDefaultSensor(SENS_GEOMAGNETIC);
        mGravitySensor = mSensorManager.getDefaultSensor(SENS_GRAVITY);
        mGyroscopeSensor = mSensorManager.getDefaultSensor(SENS_GYROSCOPE);
        mGyroscopeUncalibratedSensor = mSensorManager.getDefaultSensor(SENS_GYROSCOPE_UNCALIBRATED);
        mHeartrateSensor = mSensorManager.getDefaultSensor(SENS_HEARTRATE);
        mRotationSensor = mSensorManager.getDefaultSensor(SENS_ROTATION_VECTOR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the listener
        if (mSensorManager != null) {
//            if (mAccelerometerSensor != null) {
//                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);
//            } else {
//                Log.d(TAG, "mAcc");
//            }

//            if (mAmbientTemperatureSensor != null) {
//                mSensorManager.registerListener(this, mAmbientTemperatureSensor, SensorManager.SENSOR_DELAY_FASTEST);
//            } else {
//                Log.d(TAG, "mAmbient");
//            }

            if (mGameRotationVectorSensor != null) {
                mSensorManager.registerListener(this, mGameRotationVectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
            } else {
                Log.d(TAG, "mGaming");
            }

            if (mRotationSensor != null) {
                mSensorManager.registerListener(this, mRotationSensor, SensorManager.SENSOR_DELAY_FASTEST);
            } else {
                Log.d(TAG, "mRotating");
            }

            if (mGeomagneticSensor != null) {
                mSensorManager.registerListener(this, mGeomagneticSensor, SensorManager.SENSOR_DELAY_FASTEST);
            } else {
                Log.d(TAG, "mGeomag");
            }

            if (mGravitySensor != null) {
                mSensorManager.registerListener(this, mGravitySensor, SensorManager.SENSOR_DELAY_FASTEST);
            } else {
                Log.d(TAG, "mGrav");
            }

            if (mGyroscopeUncalibratedSensor != null) {
                mSensorManager.registerListener(this, mGyroscopeUncalibratedSensor, SensorManager.SENSOR_DELAY_FASTEST);
            } else {
                Log.d(TAG, "mGyr");
            }

//            if (mHeartrateSensor != null) {
//                mSensorManager.registerListener(this, mHeartrateSensor, SensorManager.SENSOR_DELAY_FASTEST);
//            } else {
//                Log.d(TAG, "mHeart");
//            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Unregister the listener
        if (mSensorManager != null)
            mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if ((event.timestamp - mLasttimestamp) > 100000000) {

            client.sendSensorData(event.sensor.getType(), event.accuracy, event.timestamp, event.values);

            mLasttimestamp = event.timestamp;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void onBeep(View view) {
        client.sendSensorData(0, 1, System.currentTimeMillis(), new float[]{random.nextFloat()});
    }
}
