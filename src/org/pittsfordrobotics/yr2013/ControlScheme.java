/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Liam Middlebrook
 */
public class ControlScheme {
    static Joystick joy1;//driving stick
    static Joystick joy2;//Shooting/Climbing Stick
    public static boolean getTrigger(){
        return joy2.getTrigger();
    }
    public static double shotAngle(){
        return Utils.checkClearance(joy2.getMagnitude()*((joy2.getDirectionDegrees()<=180)?1:-1),.2);
    }
    public static double driveMagnitude(){
        return Utils.checkClearance(joy1.getMagnitude(), .1);
    }
    public static double driveDirection(){
        return Utils.checkAngle(joy1.getDirectionDegrees(),Utils.kDefaultDegreeClearnace,true);
    }
    public static double driveRotation(){
        if(joy1.getRawButton(6))return -1;
        else if(joy1.getRawButton(7))return 1;
        return 0;
    }
}
