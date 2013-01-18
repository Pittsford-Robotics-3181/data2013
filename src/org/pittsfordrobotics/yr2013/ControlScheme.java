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
    static Joystick joy1;
    static Joystick joy2;
    public static boolean leftTrigger(){
        return joy1.getTrigger();
    }
    
}
