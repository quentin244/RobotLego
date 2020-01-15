
package appli;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.utility.Delay;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class Robot {
    private Sensor sensor;
    private Drive pilot ;
    private Explorator exp;

    public Robot() throws RemoteException, MalformedURLException, NotBoundException {
    	sensor = new Sensor();
    	pilot = new Drive(sensor);
    	Thread t = new Thread(sensor);
        t.start();
        exp = new Explorator(sensor,pilot);
    }
    
    public void start() throws RemoteException, MalformedURLException, NotBoundException {
    	exp.explorer();
    	/*sensor.getGyro().reset();
    	System.out.println(sensor.gyroValue);
    	Button.waitForAnyPress();
        pilot.quartDeTourGauche();
        pilot.quartDeTourDroite();
        pilot.avancer();
        Delay.msDelay(10000);
        pilot.reculer();
        Delay.msDelay(10000);
        pilot.close();
        sensor.close();
        System.out.println(sensor.gyroValue);
        Delay.msDelay(15000);*/
    }
}