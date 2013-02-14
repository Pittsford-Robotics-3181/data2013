/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 *
 * @author liammiddlebrook
 */

public class ControlScheme {
    //indexes of arrays for labeled controls
    private static final int shootingIndex = 0; //launching a disk
    private static final int spinningIndex = 1; //spinning shooter wheels
    private static final int driveLeftSideIndex = 2;//rotate left
    private static final int driveRightSideIndex = 3;//rotate right
    private static final int aimUpIndex = 4;//aim shooter up
    private static final int aimDownIndex = 5;//aim shooter down
    private static final int beginClimbIndex = 6;//set up for climbing
    private static final int climbIndex = 7;//pull the robot up
    private static final int climbExtendIndex = 8;//extend the climbing arm, might be automated
    
    public static boolean isAutonomous;
    public static final boolean isAutoClimb=false;
    private static final int kShotAngleAdjust=1;
    
    public static Joystick joy1 = Hardware.driveJoystick;//driveSystem stick
    public static Joystick joy2 = Hardware.auxJoystick;//Shooting+Climbing Stick
    

    public static boolean doShoot(){
	if(SmartDashboard.getBoolean("IsFPR")){
	    return ((int)(SmartDashboard.getNumber("MouseButtons")) & 1) == 1;
	}
	if(isAutonomous)return Data.ai.functionValues[shootingIndex];
        return joy2.getTrigger();
    }
    public static boolean doSpin(){
	if(SmartDashboard.getBoolean("IsFPR")){
	    return ((int)(SmartDashboard.getNumber("MouseButtons")) & 2) == 2;
	}
	if(isAutonomous)return Data.ai.functionValues[spinningIndex];
        return joy2.getRawButton(5)||joy2.getRawButton(4);
    }
    public static double shotAngle(){
	if(SmartDashboard.getBoolean("IsFPR")){
		return -SmartDashboard.getNumber("MouseYVelocity")/7.0;
	}
	double num=0;
	if(!isAutonomous){
	    num+=joy2.getRawButton(2)?-kShotAngleAdjust:0;
	    num+=joy2.getRawButton(3)?kShotAngleAdjust:0;
	}
	if(num==0||isAutonomous){
	    num+=Data.ai.functionValues[aimDownIndex]?-kShotAngleAdjust:0;
	    num+=Data.ai.functionValues[aimUpIndex]?kShotAngleAdjust:0;
	}
	return num;
    }
     public static boolean shouldStartClimb(){
	if(SmartDashboard.getBoolean("IsFPR")){
		return false;//IMPLEMENT ME!
	}
        if(isAutonomous)return Data.ai.functionValues[beginClimbIndex];
        return joy1.getTrigger();
    }
    public static double climbDir(){
	if(SmartDashboard.getBoolean("IsFPR")){
		return 0;//IMPLEMENT ME!
	}
        /*double num=0;
	num+=valueForButtonOnJoystick(joystickMap[climbIndex],buttonMap[climbIndex])?-1:0;
	if(isAutonomous) num+=Hardware.aiDriver.functionValues[climbExtendIndex]?1:0;
        else num+=valueForButtonOnJoystick(joystickMap[climbExtendIndex],buttonMap[climbExtendIndex])?1:0;
	return num;*/
	double num=0;
	if(!isAutoClimb){
	    num+=joy2.getRawButton(5)?kShotAngleAdjust:0;
	    num+=joy2.getRawButton(3)?-kShotAngleAdjust:0;
	}
	if(isAutoClimb&&joy2.getRawButton(3)){
	    num+=Data.ai.functionValues[climbExtendIndex]?kShotAngleAdjust:0;
	    num+=Data.ai.functionValues[climbIndex]?-kShotAngleAdjust:0;
	}
	return num;
    }
    public static double driveX(){
	if(SmartDashboard.getBoolean("IsFPR")){
	    return (((int)SmartDashboard.getNumber("Keyboard")) & 4) == 4 ? 0.5 : ((int)(SmartDashboard.getNumber("Keyboard")) & 8) == 8 ? -0.5 : 0;
	}
	if(isAutonomous||doSpin())return Data.ai.driveX;
	int num=0;
	if(joy1.getRawButton(4))num+= -1;
	if(joy1.getRawButton(5))num+= 1;
	if(num!=0)return num;
        return Utils.checkClearance(joy1.getX(), .05);
    }
    public static double driveY(){
	if(SmartDashboard.getBoolean("IsFPR")){
	    return (((int)SmartDashboard.getNumber("Keyboard")) & 1) == 1 ? 0.5 : ((int)(SmartDashboard.getNumber("Keyboard")) & 2) == 2 ? -0.5 : 0;
	}
	if(isAutonomous||doSpin())return Data.ai.driveY;
	int num=0;
	if(joy1.getRawButton(3))num+= -1;
	if(joy1.getRawButton(2))num+= 1;
	if(num!=0)return num;
        return Utils.checkClearance(joy1.getY(), .05);
    }
    public static double driveRotation(){
	if(SmartDashboard.getBoolean("IsFPR")){
		return SmartDashboard.getNumber("MouseXVelocity")/7.0;
	}
        double num=0;
	if(isAutonomous||doSpin()){
	    num+=Data.ai.functionValues[driveLeftSideIndex]?-1:0;
	    num+=Data.ai.functionValues[driveRightSideIndex]?1:0;
	}
        else {
	    num+=joy1.getRawButton(8)?-1:0;
	    num+=joy1.getRawButton(9)?1:0;
	}
	return num;
    }
    public static String logString(boolean Autonomous){
        String xmlString=Autonomous?"<controlScheme mode=\"autonomous\">\n":"<controlScheme mode=\"teleop\">\n";
        xmlString=xmlString.concat("<driveValues>\n"+
                "<X>"+ControlScheme.driveX()+"</X>\n"+
                "<Y>"+ControlScheme.driveY()+"</Y>\n"+
                "<Rotation>"+ControlScheme.driveRotation()+"</Rotation>\n"+
                "</driveValues>\n");
        xmlString=xmlString.concat("<shotValues>\n"+
                "<adjustAngle>"+ControlScheme.shotAngle()+"</adjustAngle>\n"+
                "<shouldSpin>"+(ControlScheme.doSpin()?"true":"false")+"</shouldSpin>\n"+
                "<shouldFire>"+(ControlScheme.doShoot()?"true":"false")+"</shouldFire>\n"+
                "</shotValues>\n");
        xmlString=xmlString.concat("<climbValues>\n"+
                "<shouldBegin>"+(ControlScheme.shouldStartClimb()?"true":"false")+"</shouldBegin>\n"+
                "<climb>"+ControlScheme.climbDir()+"</climb>\n"+
                "</climbValues>\n");
        xmlString=xmlString.concat("</controlSchme>");
        return xmlString;
    }
    /*
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
			return 0;//Math.max(0,SmartDashboard.getNumber("MouseYVelocity")/14.0);
		}
            return Hardware.auxJoystick.getRawButton(3) ? 1 : 0;
        }
        public static double angleDown()
        {
			if(SmartDashboard.getBoolean("IsFPR")){
			return SmartDashboard.getNumber("MouseYVelocity")/7.0;
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
		* */
		public static double getZ(){
			return Hardware.driveJoystick.getZ();
		}
}
