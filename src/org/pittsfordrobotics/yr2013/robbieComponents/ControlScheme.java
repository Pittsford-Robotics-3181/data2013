/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013.robbieComponents;
import org.pittsfordrobotics.yr2013.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
/**
 *
 * @author robbiemarkwick
 */
public class ControlScheme {
    /*/indexes of arrays for labeled controls*/
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
	if(isAutonomous)return Hardware.aiDriver.functionValues[shootingIndex];
        return joy2.getTrigger();
    }
    public static boolean doSpin(){
	if(isAutonomous)return Hardware.aiDriver.functionValues[spinningIndex];
        return joy2.getRawButton(2);
    }
    public static double shotAngle(){
	double num=0;
	if(!isAutonomous){
	    num+=joy2.getRawButton(6)?-kShotAngleAdjust:0;
	    num+=joy2.getRawButton(4)?kShotAngleAdjust:0;
	}
	if(num==0||isAutonomous){
	    num+=Hardware.aiDriver.functionValues[aimDownIndex]?-kShotAngleAdjust:0;
	    num+=Hardware.aiDriver.functionValues[aimUpIndex]?kShotAngleAdjust:0;
	}
	return num;
    }
     public static boolean shouldStartClimb(){
        if(isAutonomous)return Hardware.aiDriver.functionValues[beginClimbIndex];
        return joy1.getTrigger();
    }
    public static double climbDir(){
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
	    num+=Hardware.aiDriver.functionValues[climbExtendIndex]?kShotAngleAdjust:0;
	    num+=Hardware.aiDriver.functionValues[climbIndex]?-kShotAngleAdjust:0;
	}
	return num;
    }
    public static double driveX(){
	if(isAutonomous||doSpin())return Hardware.aiDriver.driveX;
	int num=0;
	if(joy1.getRawButton(4))num+= -.7;
	if(joy1.getRawButton(5))num+= .7;
	if(num!=0)return num;
        return Utils.checkClearance(joy1.getX(), .05);
    }
    public static double driveY(){
	if(isAutonomous||doSpin())return Hardware.aiDriver.driveY;
	int num=0;
	if(joy1.getRawButton(2))num+= -.7;
	if(joy1.getRawButton(3))num+= .7;
	if(num!=0)return num;
        return Utils.checkClearance(joy1.getY(), .05);
    }
    public static double driveRotation(){
        double num=0;
	if(isAutonomous||doSpin()){
	    num+=Hardware.aiDriver.functionValues[driveLeftSideIndex]?-1:0;
	    num+=Hardware.aiDriver.functionValues[driveRightSideIndex]?1:0;
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
    
}
