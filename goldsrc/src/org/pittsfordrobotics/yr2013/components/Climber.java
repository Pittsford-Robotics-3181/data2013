/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import edu.wpi.first.wpilibj.*;
import org.pittsfordrobotics.yr2013.*;

/**
 * Handles shooting in a thread.
 * <p/>
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class Climber extends Thread {

	/**
	 * The solenoid to tilt.
	 */
	ToggleTiltSolenoid toggler;

	/**
	 * Main loop for handling hooks.
	 */
	public void run() {
		toggler = new ToggleTiltSolenoid();
		toggler.start();
		while(DriverStation.getInstance().isEnabled()) {
			Hardware.climbingMotor.set(/*
					 * Math.max(Hardware.climberLimit.get() ? 0 : -1 ,
					 */Hardware.auxJoystick.getRawButton(6) ? -1 : Hardware.auxJoystick.getRawButton(7) ? 1 : 0)/*
					 * )
					 */;

			Hardware.hookSolenoid1.set(Hardware.auxJoystick.getRawButton(10));
			Timer.delay(0.1);
			Hardware.hookSolenoid2.set(Hardware.auxJoystick.getRawButton(10));
			Timer.delay(0.005);
		}
	}

	/**
	 * Handles tilting.
	 */
	private class ToggleTiltSolenoid extends Thread {

		private boolean isToggling = false;

		/**
		 * The main loop for tilting.
		 */
		public void run() {
			Hardware.tiltSolenoid.set(false);
			while(DriverStation.getInstance().isEnabled()) {
				if(Hardware.auxJoystick.getRawButton(11)) {
					isToggling = !isToggling;
					Hardware.tiltSolenoid.set(isToggling);
					Timer.delay(0.1);
				}
				Timer.delay(0.005);
			}
		}
	}
}
