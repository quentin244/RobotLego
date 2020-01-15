package appli;

import lejos.hardware.Button;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

public class Explorator {
	private Sensor sensors;
	private Drive driver;
	
	public Explorator(Sensor s, Drive d) {
		sensors = s;
		driver = d;
	}
	
	public void explorer() {
    	driver.avancer();
        while ((driver.getMoteurGauche().isMoving() || driver.getMoteurDroit().isMoving())) {
        	if(sensors.distanceRight >= 0.60 && sensors.distanceRight >= 0.60) {
        		driver.stop();
            	break;
        	}
        	if (sensors.distanceRight > 0.04) {//Une porte a été trouvée à droite
        		driver.quartDeTourDroite();
        		driver.avancer();
            } else if (sensors.touchValue == 1) {//Un obstacle frontal à été rencontré
            	driver.reculer();
            	driver.decidePress();
            } else if (sensors.distanceLeft > 0.04) {//Une porte à été trouvée a gauche
            	driver.quartDeTourGauche();
            	driver. avancer();
            } else {
        
            }
        }
        Button.waitForAnyPress();
        driver.close();
        sensors.close();
    }
}
