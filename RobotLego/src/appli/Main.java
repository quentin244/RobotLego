package appli;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.Button;

public class Main {

	public static void main(String[] args)  throws RemoteException, MalformedURLException, NotBoundException {
		
		System.out.println("Legorinthe is starting now...");
		Robot r = new Robot()  ;
		System.out.println("PRESS ANY BUTTON TO START");
		Button.LEDPattern(4); 
		Button.waitForAnyPress();
		r.start();
	}
}
