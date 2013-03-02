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
	double coefficient = 35.522;
	double exponent = -0.868;
	//R^2 = 0.9981

	/**
	 * Constructs the sensor with the given AnalogChannels.
	 * <p/>
	 * @param xChannel the AnalogChannel for x acceleration.
	 * @param yChannel the AnalogChannel for y acceleration.
	 */
	public AngleSensor(int voltsInChan) {
		x = new AnalogChannel(voltsInChan);
	}

	/**
	 * Performs atan2 on x and y, centered.
	 * <p/>
	 * @return the angle.
	 */
	public double getAngle() {
		return getAngleInStupidDegrees() * Math.PI / 180.0;
	}

	/**
	 * Converts the angle to degrees.
	 * <p/>
	 * @return degrees, which are stupid.
	 */
	public double getAngleInStupidDegrees() {
		return coefficient*MathUtils.pow(x.getVoltage(),exponent);
	}
	public double getVoltage(){
		return x.getVoltage();
	}
}
