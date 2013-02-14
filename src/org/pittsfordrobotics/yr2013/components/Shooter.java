/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import org.pittsfordrobotics.yr2013.ControlScheme;
import edu.wpi.first.wpilibj.*;
import org.pittsfordrobotics.yr2013.*;

/**
 *
 * @author liam
 */
public class Shooter implements Loggable{

	SpeedController frontMotor, backMotor, angleMotor;
	Solenoid discPusher;
	 private double fireSpeed = -1;
     private static final double kAdjust = -.75;
	private LaunchThread launcher = new LaunchThread();

	public Shooter(SpeedController frontMotor, SpeedController backMotor, SpeedController angleMotor, Solenoid discPusher) {
		this.frontMotor = frontMotor;
		this.backMotor = backMotor;
		this.angleMotor = angleMotor;
		this.discPusher = discPusher;
		launcher.start();
	}

    public void shoot() {
	angleMotor.set(ControlScheme.shotAngle() * kAdjust);//Adjust Angle
	//Hardware.dsOutput.say(3, "TRYING TO SHOOT");
	if (ControlScheme.doSpin()) {
	   // Hardware.dsOutput.say(2, "SPINNING");
	   Utils.ramp(fireSpeed, frontMotor, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
	   Utils.ramp(fireSpeed, backMotor, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
	   // fireMotor1.set(fireSpeed1);
	   // fireMotor2.set(fireSpeed1);
	}//Shoot if needed
	else {
	    Utils.ramp(0, frontMotor, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
	    Utils.ramp(0, backMotor, Utils.kDefaultTicksPerSecond, Utils.kDefaultRampStepSize);
	    //fireMotor1.set(0);
	    //fireMotor2.set(0);
	}//Stop Shooting if needed
    }
    public String logString() {
	String xmlString = "<shooter>\n";
	xmlString = xmlString.concat("<angleMotor>" + angleMotor.get() + "</angleMotor>\n");
	xmlString = xmlString.concat("<fireMotor1>\n"
		+ "<target>" + (ControlScheme.doSpin()?-1:0) + "</target>\n"
		+ "<current>" + frontMotor.get() + "</current>\n"
		+ "</fireMotor1>\n");
	xmlString = xmlString.concat("<fireMotor2>\n"
		+ "<target>" + (ControlScheme.doSpin()?-1:0) + "</target>\n"
		+ "<current>" + backMotor.get() + "</current>\n"
		+ "</fireMotor1>\n");
	xmlString = xmlString.concat("<pusher>" + discPusher.get() + "</pusher>\n");
	xmlString = xmlString.concat("</shooter>");
	return xmlString;
    }
    class LaunchThread extends Thread {

	public void run() {
	    try {
		for (;;) {
		    if (DriverStation.getInstance().isEnabled()) {
			if (ControlScheme.doShoot()) {
			    discPusher.set(true);//Launch Disks if and only if the driver says so
			    Thread.sleep(1000/4);//keep solenoid on for desired amount of time
			    discPusher.set(false);
			    Thread.sleep(1000 / 4);//no more shots for .25 seconds
			} else {
			    discPusher.set(false);
			    Thread.sleep(50); //avoid noise
			}
		    }
		}
	    } catch (InterruptedException ex) {
	    }

	}
    }
}
