import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class Sensor implements Runnable {
	private EV3GyroSensor gyro;
    private EV3UltrasonicSensor senseurGauche;
    private EV3UltrasonicSensor senseurDroit;
    private EV3TouchSensor bouton;
    public float distanceRight;
    public float distanceLeft;
    public float touchValue;

    public Sensor() {
        gyro = new EV3GyroSensor(SensorPort.S3);
        senseurDroit = new EV3UltrasonicSensor(SensorPort.S4);
        senseurGauche = new EV3UltrasonicSensor(SensorPort.S2);
        bouton = new EV3TouchSensor(SensorPort.S1);
	}

	@Override
	public void run() {
		SampleProvider ultrasonicDistSPRight = senseurGauche.getDistanceMode();
        float[] ultrasonicDistSampleRight = new float[ultrasonicDistSPRight.sampleSize()];

        SampleProvider ultrasonicDistSPLeft = senseurDroit.getDistanceMode();
        float[] ultrasonicDistSampleLeft = new float[ultrasonicDistSPLeft.sampleSize()];

        SensorMode touch = bouton.getTouchMode();
        float[] touchSample = new float[touch.sampleSize()];
        
        while(true) {
        	ultrasonicDistSPRight.fetchSample(ultrasonicDistSampleRight, 0);
        	ultrasonicDistSPLeft.fetchSample(ultrasonicDistSampleLeft, 0);
            touch.fetchSample(touchSample, 0);
            
            distanceRight = ultrasonicDistSampleRight[0];
            distanceLeft = ultrasonicDistSampleLeft[0];
            touchValue = touchSample[0];
            
            System.out.println("Right :" + distanceRight);
            System.out.println("Left :" + distanceLeft);
            System.out.println("Touche :" + touchValue);
        }
	}
	
	public void close() {
        senseurDroit.close();
        senseurGauche.close();
        bouton.close();
        gyro.close();
    }
}
