/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;

import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 *
 * @author liammiddlebrook
 */

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
        }
        return AbsoluteDirection.UNSPECIFIED;
    }
    public static boolean driveRotateCCW()
    {
        return Hardware.driveJoystick.getRawButton(8);
    }
    public static boolean driveRotateCW()
    {
        return Hardware.driveJoystick.getRawButton(9);
    }
	static final class AbsoluteDirection{
		public static final AbsoluteDirection FORWARD = new AbsoluteDirection(0);
		public static final AbsoluteDirection BACKWARD = new AbsoluteDirection(1);
		public static final AbsoluteDirection STRAFE_LEFT = new AbsoluteDirection(2);
		public static final AbsoluteDirection STRAFE_RIGHT = new AbsoluteDirection(3);
		public static final AbsoluteDirection UNSPECIFIED = new AbsoluteDirection(5);
		public static final int DIR_FORWARD = 0;
		public static final int DIR_BACKWARD = 1;
		public static final int DIR_STRAFE_LEFT = 2;
		public static final int DIR_STRAFE_RIGHT = 3;
		public static final int ERROR_UNSPECIFIED = 5;
		private int id = 0;
		private AbsoluteDirection(int id){this.id=id;}
        }
        public static boolean doShoot()
        {
			if(SmartDashboard.getBoolean("IsFPR")){
				return ((int)(SmartDashboard.getNumber("MouseButtons")) & 1) == 1;
			}
            return Hardware.auxJoystick.getTrigger();
        }
        public static boolean doShooterSpin()
        {
			if(SmartDashboard.getBoolean("IsFPR")){
				return ((int)(SmartDashboard.getNumber("MouseButtons")) & 2) == 2;
			}
            return Hardware.auxJoystick.getRawButton(4) || Hardware.auxJoystick.getRawButton(5);
        }
        public static double angleUp()
        {
			if(SmartDashboard.getBoolean("IsFPR")){
			return Math.max(0,SmartDashboard.getNumber("MouseYVelocity")/14.0);
		}
            return Hardware.auxJoystick.getRawButton(3) ? 1 : 0;
        }
        public static double angleDown()
        {
			if(SmartDashboard.getBoolean("IsFPR")){
			return Math.min(0,SmartDashboard.getNumber("MouseYVelocity")/14.0);
		}
            return Hardware.auxJoystick.getRawButton(2) ? 1 : 0;
        }
		public static double strafeY(){
			if(SmartDashboard.getBoolean("IsFPR")){
				return (((int)SmartDashboard.getNumber("Keyboard")) & 1) == 1 ? 0.5 : ((int)(SmartDashboard.getNumber("Keyboard")) & 2) == 2 ? -0.5 : 0;
			}
			return Hardware.driveJoystick.getY();
		}
		public static double strafeX(){
			if(SmartDashboard.getBoolean("IsFPR")){
				return (((int)SmartDashboard.getNumber("Keyboard")) & 4) == 4 ? 0.5 : ((int)(SmartDashboard.getNumber("Keyboard")) & 8) == 8 ? -0.5 : 0;
			}
			return Hardware.driveJoystick.getX();
		}
		public static double getZ(){
			return Hardware.driveJoystick.getZ();
		}
}
