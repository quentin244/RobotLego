package appli;

import java.util.Observable;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class Sensor extends Observable implements Runnable {
	private EV3GyroSensor gyro;
	private EV3UltrasonicSensor senseurGauche;
	private EV3UltrasonicSensor senseurDroit;
	private EV3TouchSensor bouton;
    public float distanceRight;
    public float distanceLeft;
    public float touchValue;
    public float gyroValue;
    private SampleProvider ultrasonicDistSPRight;
    private SampleProvider ultrasonicDistSPLeft;
    private SensorMode touch;
    private float[] ultrasonicDistSampleRight;
    private float[] ultrasonicDistSampleLeft;
    private float[] touchSample;
    private SampleProvider gyroscope;
    private float[] gyroSample;
    
    public Sensor() {
    	bouton = new EV3TouchSensor(SensorPort.S1);
    	senseurGauche = new EV3UltrasonicSensor(SensorPort.S2);
        gyro = new EV3GyroSensor(SensorPort.S3);
        senseurDroit = new EV3UltrasonicSensor(SensorPort.S4);
        ultrasonicDistSPLeft = senseurGauche.getDistanceMode();
        ultrasonicDistSampleLeft = new float[ultrasonicDistSPLeft.sampleSize()];
        ultrasonicDistSPRight = senseurDroit.getDistanceMode();
        ultrasonicDistSampleRight = new float[ultrasonicDistSPRight.sampleSize()];
        touch = bouton.getTouchMode();
        touchSample = new float[touch.sampleSize()];
        gyroscope  = gyro.getAngleMode();
        gyroSample = new float[gyro.sampleSize()];
        gyro.reset();
	}

	@Override
	public void run() {
        while(true) {
        	try {
	        	ultrasonicDistSPRight.fetchSample(this.ultrasonicDistSampleRight, 0);
	        	ultrasonicDistSPLeft.fetchSample(ultrasonicDistSampleLeft, 0);
	            touch.fetchSample(touchSample, 0);
	            gyroscope.fetchSample(gyroSample, 0);
	            
	            distanceRight = ultrasonicDistSampleRight[0];
	            distanceLeft = ultrasonicDistSampleLeft[0];
	            touchValue = touchSample[0];
	            gyroValue = gyroSample[0];
	            if(distanceLeft <= 0.05 || distanceRight <= 0.05) {
	            	notifyObserver();
	            }
        	}
        	catch(Exception e) {
        		break;
        	}
        }
	}
	
	public void close() {
        senseurDroit.close();
        senseurGauche.close();
        bouton.close();
        gyro.close();
    }
	
	public EV3GyroSensor getGyro() {
		return gyro;
	}
	
	public EV3UltrasonicSensor getUltrasonicDroit() {
		return senseurDroit;
	}
	
	public EV3UltrasonicSensor getUltrasonicGauche() {
		return senseurGauche;
	}
	
	public EV3TouchSensor getTouchSensor() {
		return bouton;
		
	}
	
	public SampleProvider getGyroSample() {
		return gyroscope;
	}
	
	public float[] getSampleGyro() {
		return gyroSample;
	}
	
	public void notifyObserver() {
		setChanged();
		notifyObservers();
	}
}


