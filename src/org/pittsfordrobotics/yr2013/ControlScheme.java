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
    public static final int climbExtendIndex = 8;
    public static boolean isAutonomous;
    private static int[] joystickMap={1,1,0,0,1,1,1,1,1}; 
    private static int[] buttonMap={0,7,6,7,4,5,1,2,3};
    static Joystick joy0;//left driving stick in tank only
    static Joystick joy1;//driving stick (right in tank)
    static Joystick joy2;//Shooting/Climbing Stick
    private static Joystick[] sticks={joy1,joy2};//does not include the left if tank-Driving (lefty drivers can physically swap them)
    

    public static boolean shouldShoot(){
	if(isAutonomous)return Hardware.aiDriver.functionValues[shootingIndex];
        return valueForButtonOnJoystick(joystickMap[shootingIndex],buttonMap[shootingIndex]);
    }
    public static boolean shouldSpin(){
	if(isAutonomous)return Hardware.aiDriver.functionValues[spinningIndex];
        return valueForButtonOnJoystick(joystickMap[spinningIndex],buttonMap[spinningIndex]);
    }
    public static double shotAngle(){
	double num=0;
	if(isAutonomous) num+=Hardware.aiDriver.functionValues[aimDownIndex]?-.5:0;
        else num+=valueForButtonOnJoystick(joystickMap[aimDownIndex],buttonMap[aimDownIndex])?-.5:0;
	if(isAutonomous) num+=Hardware.aiDriver.functionValues[aimUpIndex]?.5:0;
        else num+=valueForButtonOnJoystick(joystickMap[aimUpIndex],buttonMap[aimUpIndex])?.5:0;
	return num;
    }
     public static boolean shouldStartClimb(){
        if(isAutonomous)return Hardware.aiDriver.functionValues[beginClimbIndex];
        return valueForButtonOnJoystick(joystickMap[beginClimbIndex],buttonMap[beginClimbIndex]);
    }
    public static double climbDir(){
        double num=0;
	if(isAutonomous) num+=Hardware.aiDriver.functionValues[climbIndex]?-1:0;
        else num+=valueForButtonOnJoystick(joystickMap[climbIndex],buttonMap[climbIndex])?-1:0;
	if(isAutonomous) num+=Hardware.aiDriver.functionValues[climbExtendIndex]?1:0;
        else num+=valueForButtonOnJoystick(joystickMap[climbExtendIndex],buttonMap[climbExtendIndex])?1:0;
	return num;
    }
    public static double driveMagnitude(){
	if(isAutonomous)return Hardware.aiDriver.driveMag;
        return Utils.checkClearance(joy1.getMagnitude(), .1);
    }
    public static double driveDirection(){
	if(isAutonomous)return Hardware.aiDriver.driveDir;
        return Utils.checkAngle(joy1.getDirectionDegrees(),Utils.kDefaultDegreeClearnace,true);
    }
    public static double driveRotation(){
        double num=0;
	if(isAutonomous) num+=Hardware.aiDriver.functionValues[driveLeftSideIndex]?-1:0;
        else num+=valueForButtonOnJoystick(joystickMap[driveLeftSideIndex],buttonMap[driveLeftSideIndex])?-1:0;
	if(isAutonomous) num+=Hardware.aiDriver.functionValues[driveRightSideIndex]?1:0;
        else num+=valueForButtonOnJoystick(joystickMap[driveRightSideIndex],buttonMap[driveRightSideIndex])?1:0;
	return num;
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
        joystickMap[function]=stick;
        buttonMap[function]=button;
    }
    /**
     * reads the button for a given joystick and 
     * @param joy which joystick
     * @param butt 0 if trigger, 1-12 if any other button
     * @return is the button pressed
     */
    private static boolean valueForButtonOnJoystick(int joy, int butt){
	return butt==0?(sticks[joy]).getTrigger():(sticks[joy]).getRawButton(butt);
    }
    /**
     * simulates joysticks in Autonomous
     * @param function the index of the function
     * @return is the function active
     */
    private static boolean simulatedValueForFunction(int function){
	return Hardware.aiDriver.functionValues[function];
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
                "<shouldFire>"+(ControlScheme.shouldShoot()?"true":"false")+"</shouldFire>\n"+
                "</shotValues>\n");
        xmlString=xmlString.concat("<climbValues>\n"+
                "<shouldBegin>"+(ControlScheme.shouldStartClimb()?"true":"false")+"</shouldBegin>\n"+
                "<climb>"+ControlScheme.climbDir()+"</climb>\n"+
                "</climbValues>\n");
        xmlString=xmlString.concat("</controlSchme>");
        return xmlString;
    }
    
}
