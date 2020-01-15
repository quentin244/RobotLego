package appli;

import java.util.Observable;
import java.util.Observer;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import lejos.utility.PilotProps;

public class Drive implements Observer{
	private RegulatedMotor moteurGauche;
    private RegulatedMotor moteurDroit;
    private Sensor sensor;
   
    public Drive(Sensor s) {
    	moteurGauche = Motor.A;
        moteurDroit = Motor.D;
        sensor = s;
        s.addObserver(this);
    }
    
    public void test(){
    	avancer();
        Button.waitForAnyPress();
        stop();
    }
    
    public void close() {
    	moteurGauche.close();
        moteurDroit.close();
    }
    
    public void stop() {
    	moteurGauche.stop();
    	moteurDroit.stop();
    }
    
    public void avancer() {
    	if(moteurGauche.isMoving() && moteurDroit.isMoving()) {
    		moteurGauche.stop();
    		moteurDroit.stop();
    	}
    	
        moteurGauche.forward();
		moteurDroit.forward();
		//while(sensor.gyroValue <= 5 && sensor.gyroValue >= -5 ) {
		//	if(sensor.gyroValue < 0) {
			//	moteurDroit.rotate(35);
				//while(moteurDroit.isMoving()) {Thread.yield();}
			//}else {
				//moteurGauche.rotate(35);
				//while(moteurGauche.isMoving()) {Thread.yield();}
			//}
			//break;
		//}
    }
    
    public void reculer() {
    	stop();
        moteurGauche.backward();
		moteurDroit.backward();
    }
    
    public void quartDeTourDroite() {
    	stop();
    	sensor.getGyro().reset();
    	moteurGauche.rotate(365);
    	Delay.msDelay(100);
    	while(sensor.gyroValue != -90) {
    		if(sensor.gyroValue > -90.000) {
    			moteurGauche.forward();
    		}
    		else {
    			moteurGauche.backward();
    		}
    		break;
    	}
    	stop();
    	while(moteurGauche.isMoving()) {Thread.yield();}
    }
    
    public void quartDeTourGauche() {
    	stop();
    	sensor.getGyro().reset();
    	moteurDroit.rotate(365);
    	Delay.msDelay(100);
    	while(sensor.gyroValue != 90) {
    		if(sensor.gyroValue < 90.000) {
    			moteurDroit.forward();
    		}
    		else {
    			moteurDroit.backward();
    		}
    		break;
    	}
    	stop();
    	while(moteurDroit.isMoving()) {Thread.yield();}
    }
    
    public void demiTour() {
    	stop();
    	reculer();
    	Delay.msDelay(850);
    	stop();
    	moteurDroit.rotate(365,true);
    	moteurGauche.rotate(-365,true);
    	while(moteurGauche.isMoving()) {Thread.yield();}
    	while(moteurDroit.isMoving()) {Thread.yield();}
    }
    
    public RegulatedMotor getMoteurGauche() {
    	return moteurGauche;
    }
    
    public RegulatedMotor getMoteurDroit() {
    	return moteurDroit;
    }

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Sensor)
        {       
                Sensor s = (Sensor) o;
                if(s.touchValue != 0.0) {
                	decidePress();
                }
                else if(s.distanceLeft <= 0.11 ) {
                	stop();
                	moteurGauche.rotate(75,true);
                	Delay.msDelay(100);
                	//avancer();
                }
                else if(s.distanceRight <= 0.11 ) {
                	stop();
                	moteurDroit.rotate(75,true);
                	Delay.msDelay(100);
                	//avancer();
                }
        }
		
	}
	
	public void decidePress() {
		stop();
		if(sensor.distanceRight >= 0.05 && sensor.distanceLeft <= 0.05) {
			quartDeTourDroite();
		}else if(sensor.distanceRight <= 0.05 && sensor.distanceLeft >= 0.05) {
			quartDeTourGauche();
		}else {
			demiTour();
		}
	}
}
