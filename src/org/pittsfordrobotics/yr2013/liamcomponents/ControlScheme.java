/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pittsfordrobotics.yr2013.liamcomponents;

import edu.wpi.first.wpilibj.Joystick.ButtonType;
import org.pittsfordrobotics.yr2013.Hardware;

/**
 *
 * @author Team3181_User
 */
public class ControlScheme {
    public static boolean doDiscPush(){
        return    Hardware.auxJoystick.getTrigger();
    }
    public static boolean doShooterSpinUp(){
        return    Hardware.auxJoystick.getRawButton(5);
    }
    public static boolean angleUp()
    {
        return Hardware.auxJoystick.getRawButton(6);
    }
    public static boolean angleDown()
    {
        return Hardware.auxJoystick.getRawButton(4);
    }
}
