/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import org.pittsfordrobotics.yr2013.ControlScheme;

/**
 *
 * @author liam
 */
public class Climber extends Thread {

	Solenoid preClimbSolenoid;
	Solenoid switchGearSolenoid;
	Solenoid tiltRobotSolenoid;
	SpeedController climbMotor;
	boolean robotTilted;
	boolean gearsSwitched;

	public Climber(Solenoid tiltRobotSolenoid, Solenoid preClimbSolenoid, Solenoid switchGearSolenoid, SpeedController climbMotor) {
		this.climbMotor = climbMotor;
		this.preClimbSolenoid = preClimbSolenoid;
		this.switchGearSolenoid = switchGearSolenoid;
		this.tiltRobotSolenoid = tiltRobotSolenoid;
	}

	public void run() {
		if(ControlScheme.tiltRobot()) {
			tiltRobotSolenoid.set(true);
			robotTilted = true;
		}
		if(robotTilted && ControlScheme.switchGears()) {
			preClimbSolenoid.set(true);
			Timer.delay(0.1);
			switchGearSolenoid.set(true);
			gearsSwitched = true;
		}
		if(gearsSwitched && ControlScheme.doClimb()) {
			climbMotor.set(1.0);
		}
	}
}
