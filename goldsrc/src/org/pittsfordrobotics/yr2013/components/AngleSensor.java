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
	double a,b,h;

	/**
	 * Constructs the sensor with the given AnalogChannels.
	 * <p/>
	 * @param xChannel the AnalogChannel for x acceleration.
	 * @param yChannel the AnalogChannel for y acceleration.
	 */
	public AngleSensor(int voltsInChan) {
		x = new AnalogChannel(voltsInChan);
		a = 4347.1851889408;
		b = -0.9313153512;
		h = 72.398;
	}

	/**
	 * Performs atan2 on x and y, centered.
	 * <p/>
	 * @return the angle.
	 */
	public double getAngle() {
		return MathUtils.asin(MathUtils.pow(map(x.getVoltage(),0,1023,0,5)/a,1/b)/h);
	}
	
	double map(double x, double in_min, double in_max, double out_min, double out_max){
		return (x - in_min) * (out_max - out_min) /(in_max - in_min) + out_min;
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
