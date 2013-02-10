/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;

/**
 *
 * @author liammiddlebrook
 */
final class AbsoluteDirection{
    public static final AbsoluteDirection FORWARD = new AbsoluteDirection(0);
    public static final AbsoluteDirection BACKWARD = new AbsoluteDirection(1);
    public static final AbsoluteDirection STRAFE_LEFT = new AbsoluteDirection(2);
    public static final AbsoluteDirection STRAFE_RIGHT = new AbsoluteDirection(3);
    private int id = 0;
    private AbsoluteDirection(int id){this.id=id;}
}
public class ControlScheme {
    public static AbsoluteDirection perfectStrafe()
    {
        if(Hardware.driveJoystick.getRawButton(3))
        {
            return AbsoluteDirection.FORWARD;
        }
        if(Hardware.driveJoystick.getRawButton(2))
        {
            return AbsoluteDirection.BACKWARD;
        }
        if(Hardware.driveJoystick.getRawButton(4))
        {
            return AbsoluteDirection.STRAFE_LEFT;
        }
        if(Hardware.driveJoystick.getRawButton(5))
        {
            return AbsoluteDirection.STRAFE_RIGHT;
        4
    }
}
