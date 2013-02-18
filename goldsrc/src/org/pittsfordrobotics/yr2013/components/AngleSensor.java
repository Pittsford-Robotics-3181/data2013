/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.components;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogChannel;

/**
 * Gets an angle based on x and y acceleration.
 * <p/>
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class AngleSensor {

	/**
	 * The two AnalogChannels for the x and y acceleration.
	 */
	AnalogChannel x, y;

	/**
	 * Constructs the sensor with the given AnalogChannels.
	 * <p/>
	 * @param xChannel the AnalogChannel for x acceleration.
	 * @param yChannel the AnalogChannel for y acceleration.
	 */
	public AngleSensor(int xChannel, int yChannel) {
		x = new AnalogChannel(xChannel);
		y = new AnalogChannel(yChannel);
	}

	/**
	 * Performs atan2 on x and y, centered.
	 * <p/>
	 * @return the angle.
	 */
	public double getAngle() {
		return MathUtils.atan2(y.getVoltage() - 2.5, x.getVoltage() - 2.5) + Math.PI;
	}

	/**
	 * Converts the angle to degrees.
	 * <p/>
	 * @return degrees, which are stupid.
	 */
	public double getAngleInStupidDegrees() {
		return getAngle() * 180.0 / Math.PI;
	}
}
