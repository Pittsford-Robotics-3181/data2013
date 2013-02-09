/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.robbieComponents;

import org.pittsfordrobotics.yr2013.*;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author robbiemarkwick
 */
public class Shooter implements Loggable {

    public SpeedController angleMotor;
    public SpeedController fireMotor1;
    public SpeedController fireMotor2;
    public Solenoid bump;
    private double fireSpeed1 = -1;
    private double fireSpeed2 = -1;
    private static final double kAdjust = -.25;
    private LaunchThread launcher = new LaunchThread();
    public Shooter(SpeedController Fire, SpeedController Fire2, SpeedController angle, Solenoid launch) {
	fireMotor1 = Fire;
	fireMotor2 = Fire2;
	angleMotor = angle;
	bump = launch;
	launcher.start();
    }

    public void shoot() {
	angleMotor.set(ControlScheme.shotAngle() * kAdjust);//Adjust Angle
	Hardware.dsOutput.say(3, "TRYING TO SHOOT");
	if (ControlScheme.doSpin()) {
	    Hardware.dsOutput.say(2, "SPINNING");
	    Utils.ramp(fireSpeed1, fireMotor1, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
	    Utils.ramp(fireSpeed2, fireMotor2, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
	}//Shoot if needed
	else {
	    fireMotor1.set(0);
	    Utils.ramp(0, fireMotor1, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
	    Utils.ramp(0, fireMotor2, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
	}//Stop Shooting if needed
    }

    public String logString() {
	String xmlString = "<shooter>\n";
	xmlString = xmlString.concat("<angleMotor>" + angleMotor.get() + "</angleMotor>\n");
	xmlString = xmlString.concat("<fireMotor1>\n"
		+ "<target>" + fireSpeed1 + "</target>\n"
		+ "<current>" + fireMotor1.get() + "</current>\n"
		+ "</fireMotor1>\n");
	xmlString = xmlString.concat("<fireMotor2>\n"
		+ "<target>" + fireSpeed2 + "</target>\n"
		+ "<current>" + fireMotor2.get() + "</current>\n"
		+ "</fireMotor1>\n");
	xmlString = xmlString.concat("<servo>" + bump.get() + "</servo>\n");
	xmlString = xmlString.concat("</shooter>");
	return xmlString;
    }

    class LaunchThread extends Thread {

	public void run() {
	    try {
		for (;;) {
		    if (ControlScheme.doShoot()) {
			bump.set(true);//Launch Disks if and only if the driver says so
			Thread.sleep(50);//keep solenoid on for desired amount of time
			bump.set(false);
			Thread.sleep(1000 / 4);//no more shots for .25 seconds
		    } else {
			bump.set(false);
			Thread.sleep(50); //avoid noise
		    }
		}
	    } catch (InterruptedException ex) {
	    }
	}
    }
}
