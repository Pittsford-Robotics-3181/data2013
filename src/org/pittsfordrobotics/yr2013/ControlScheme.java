/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * @author Robbie Markwick
 * @author Liam Middlebrook
 */
public class ControlScheme {
    public static final int shootingIndex = 0;
    public static final int spinningIndex = 1;
    public static final int driveLeftSideIndex = 2;
    public static final int driveRightSideIndex = 3;
    public static final int aimUpIndex = 4;
    public static final int aimDownIndex = 5;
    public static final int beginClimbIndex = 6;
    public static final int climbIndex = 7;

    private static int[] joystickMap={1,1,0,0,1,1,1,1}; 
    private static int[] buttonMap={0,7,6,7,4,5,1,2};
    static Joystick joy0;//left driving stick in tank only
    static Joystick joy1;//driving stick (right in tank)
    static Joystick joy2;//Shooting/Climbing Stick
    private static Joystick[] sticks={joy1,joy2};//does not include the left if tank-Driving (lefty drivers can physically swap them)
    

    public static boolean getTrigger(){
        if(buttonMap[shootingIndex]==0)return (sticks[joystickMap[shootingIndex]]).getTrigger();
        return (sticks[joystickMap[shootingIndex]]).getRawButton(buttonMap[shootingIndex]);
    }
    public static boolean shouldSpin(){
        if(buttonMap[spinningIndex]==0)return (sticks[joystickMap[spinningIndex]]).getTrigger();
        return (sticks[joystickMap[spinningIndex]]).getRawButton(buttonMap[spinningIndex]);
    }
    public static double shotAngle(){
       if((buttonMap[aimUpIndex]==0)?((sticks[joystickMap[aimUpIndex]]).getTrigger()):((sticks[joystickMap[aimUpIndex]]).getRawButton(buttonMap[aimUpIndex])))return -.5;
        else if((buttonMap[aimDownIndex]==0)?((sticks[joystickMap[aimDownIndex]]).getTrigger()):((sticks[joystickMap[aimDownIndex]]).getRawButton(buttonMap[aimDownIndex])))return .5;
        return 0;
    }
     public static boolean getClimbStart(){
        if(buttonMap[beginClimbIndex]==0)return (sticks[joystickMap[beginClimbIndex]]).getTrigger();
        return (sticks[joystickMap[beginClimbIndex]]).getRawButton(buttonMap[beginClimbIndex]);
    }
    public static boolean shouldClimb(){
        if(buttonMap[climbIndex]==0)return (sticks[joystickMap[climbIndex]]).getTrigger();
        return (sticks[joystickMap[climbIndex ]]).getRawButton(buttonMap[climbIndex]);
    }
    public static double driveMagnitude(){
        return Utils.checkClearance(joy1.getMagnitude(), .1);
    }
    public static double driveDirection(){
        return Utils.checkAngle(joy1.getDirectionDegrees(),Utils.kDefaultDegreeClearnace,true);
    }
    public static double driveRotation(){
        if((buttonMap[driveLeftSideIndex]==0)?((sticks[joystickMap[driveLeftSideIndex]]).getTrigger()):((sticks[joystickMap[driveLeftSideIndex]]).getRawButton(buttonMap[driveLeftSideIndex])))return -1;
        else if((buttonMap[driveRightSideIndex]==0)?((sticks[joystickMap[driveRightSideIndex]]).getTrigger()):((sticks[joystickMap[driveRightSideIndex]]).getRawButton(buttonMap[driveRightSideIndex])))return 1;
        return 0;
    }
    public static double tankLeft(){
        return Utils.checkClearance(joy0.getY(), .1);
    }
    public static double tankRight(){
        return Utils.checkClearance(joy1.getY(),.1);
    }
    /**
     * Remap Joystick
     * @param stick 0 for first stick, 1 for second stick
     * @param button 0 for trigger, otherwise button 1-12
     * @param function index matching the function you want to remap (see the constants)
     * @return was remap successful
     */
    public static void setJoystickAndButtonForFunction(int stick, int button, int function){
        for(int i=0; i<buttonMap.length;i++){
            if(stick==joystickMap[i]&&button==buttonMap[i]){
		switch (function) {
		    case shootingIndex: {
			NetworkTable.getTable("Controls").putNumber("ShootingStick", joystickMap[function]);
			NetworkTable.getTable("Controls").putNumber("ShootingButt", buttonMap[function]);
		    }
		    break;
		    case spinningIndex: {
			NetworkTable.getTable("Controls").putNumber("SpinningStick", joystickMap[function]);
			NetworkTable.getTable("Controls").putNumber("SpinningButt", buttonMap[function]);
		    }
		    break;
		    case driveLeftSideIndex: {
			NetworkTable.getTable("Controls").putNumber("DriveLeftStick", joystickMap[function]);
			NetworkTable.getTable("Controls").putNumber("DriveLeftButt", buttonMap[function]);
		    }
		    break;
		    case driveRightSideIndex: {
			NetworkTable.getTable("Controls").putNumber("DriveRightStick", joystickMap[function]);
			NetworkTable.getTable("Controls").putNumber("DriveRightButt", buttonMap[function]);
		    }
		    break;
		    case aimUpIndex: {
			NetworkTable.getTable("Controls").putNumber("AimUpStick", joystickMap[function]);
			NetworkTable.getTable("Controls").putNumber("AimUpButt", buttonMap[function]);
		    }
		    break;
		    case aimDownIndex: {
			NetworkTable.getTable("Controls").putNumber("AimDownStick", joystickMap[function]);
			NetworkTable.getTable("Controls").putNumber("AimDownButt", buttonMap[function]);
		    }
		    break;
		    case beginClimbIndex: {
			NetworkTable.getTable("Controls").putNumber("BeginClimbStick", joystickMap[function]);
			NetworkTable.getTable("Controls").putNumber("BeginClimbButt", buttonMap[function]);
		    }
		    break;
		    case climbIndex: {
			NetworkTable.getTable("Controls").putNumber("ClimbStick", joystickMap[function]);
			NetworkTable.getTable("Controls").putNumber("ClimbButt", buttonMap[function]);
		    }
		    break;
		}
		NetworkTable.getTable("Controls").putBoolean("RemapFailure", true);//alert the driveStation that the remap failed
		return;
	    }//don't remap if that button is already in use		

        }
        joystickMap[function]=stick;
        buttonMap[function]=button;
    }
    public static String logString(boolean Autonomous){
        String xmlString=Autonomous?"<controlScheme mode=\"autonomous\">\n":"<controlScheme mode=\"teleop\">\n";
        xmlString=xmlString.concat("<driveValues>\n"+
                "<Magnitude>"+ControlScheme.driveMagnitude()+"</Magnitude>\n"+
                "<Direction>"+ControlScheme.driveDirection()+"</Direction>\n"+
                "<Rotation>"+ControlScheme.driveRotation()+"</Rotation>\n"+
                "</driveValues>\n");
        xmlString=xmlString.concat("<shotValues>\n"+
                "<adjustAngle>"+ControlScheme.shotAngle()+"</adjustAngle>\n"+
                "<shouldSpin>"+(ControlScheme.shouldSpin()?"true":"false")+"</shouldSpin>\n"+
                "<shouldFire>"+(ControlScheme.getTrigger()?"true":"false")+"</shouldFire>\n"+
                "</shotValues>\n");
        xmlString=xmlString.concat("<climbValues>\n"+
                "<shouldBegin>"+(ControlScheme.getClimbStart()?"true":"false")+"</shouldBegin>\n"+
                "<shouldClimb>"+(ControlScheme.shouldClimb()?"true":"false")+"</shouldClimb>\n"+
                "</climbValues>\n");
        xmlString=xmlString.concat("</controlSchme>");
        return xmlString;
    }
    
}
