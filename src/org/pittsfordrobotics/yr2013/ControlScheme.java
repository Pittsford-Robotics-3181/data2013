/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;

/**
 * @author Robbie Markwick
 * @author Liam Middlebrook
 */
public class ControlScheme {
    private static final int shootingIndex = 0;
    private static final int spinningIndex = 1;
    private static final int driveLeftSideIndex = 2;
    private static final int driveRightSideIndex = 3;
    private static final int aimUpIndex = 4;
    private static final int aimDownIndex = 5;
    private static final int beginClimbIndex = 6;
    private static final int climbIndex = 7;

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
     */
    public static void setJoystickAndButtonForFunction(int stick, int button, int function,String driverName,boolean shouldLog){
        for(int i=0; i<=4;i++){
            if(stick==joystickMap[i]&&button==buttonMap[i])return;//don't remap if that button is already in use
        }
        joystickMap[function]=stick;
        buttonMap[function]=button;
        if(shouldLog)ControlScheme.saveDriveFiles(driverName);
    }
    public static String logString(){
        String xmlString="<controlScheme>\n";
        xmlString=xmlString.concat("<driveValues>\n"+
                "<Magnitude value=\""+ControlScheme.driveMagnitude()+"\" />\n"+
                "<Direction value=\""+ControlScheme.driveDirection()+"\" />\n"+
                "<Rotation value=\""+ControlScheme.driveRotation()+"\" />\n"+
                "</driveValues>\n");
        xmlString=xmlString.concat("<shot>\n"+
                "<adjustAngle value=\""+ControlScheme.shotAngle()+"\" />\n"+
                "<shouldSpin value=\""+(ControlScheme.shouldSpin()?"true":"false")+"\" />\n"+
                "<shouldFire value=\""+(ControlScheme.getTrigger()?"true":"false")+"\" />\n"+
                "</driveValues>\n");
        xmlString=xmlString.concat("<climbValues>\n"+
                "<shouldBegin value=\""+(ControlScheme.getClimbStart()?"true":"false")+"\" />\n"+
                "<shouldClimb value=\""+(ControlScheme.shouldClimb()?"true":"false")+"\" />\n"+
                "</climbValues>\n");
        xmlString=xmlString.concat("</controlSchme>");
        return xmlString;
    }
    public static void saveDriveFiles(String driverName){
        String xmlString="<?xml version=\"1.0\" encoding=\"windows-1252\"?>";
        //Log Map Values
        xmlString=xmlString.concat("<controlMap>\n<shooting>\n<joystick num=\""+joystickMap[shootingIndex]+"\" />\n <button num=\""+buttonMap[shootingIndex]+"\" />\n</shooting>");
        xmlString=xmlString.concat("<spinning>\n<joystick num=\""+joystickMap[spinningIndex]+"\" />\n <button num=\""+buttonMap[spinningIndex]+"\" />\n</spinning>");
        xmlString=xmlString.concat("<leftRot>\n<joystick num=\""+joystickMap[driveLeftSideIndex]+"\" />\n <button num=\""+buttonMap[driveLeftSideIndex]+"\" />\n</leftRot>");
        xmlString=xmlString.concat("<rightRot>\n<joystick num=\""+joystickMap[driveRightSideIndex]+"\" />\n <button num=\""+buttonMap[driveRightSideIndex]+"\" />\n</rightRot>");
        xmlString=xmlString.concat("<aimUp>\n<joystick num=\""+joystickMap[aimUpIndex]+"\" />\n <button num=\""+buttonMap[aimUpIndex]+"\" />\n</aimUp>");
        xmlString=xmlString.concat("<aimDown>\n<joystick num=\""+joystickMap[aimDownIndex]+"\" />\n <button num=\""+buttonMap[aimDownIndex]+"\" />\n</aimDown>");
        xmlString=xmlString.concat("<beginClimb>\n<joystick num=\""+joystickMap[beginClimbIndex]+"\" />\n <button num=\""+buttonMap[beginClimbIndex]+"\" />\n</beginClimb>");
        xmlString=xmlString.concat("<climbing>\n<joystick num=\""+joystickMap[climbIndex]+"\" />\n <button num=\""+buttonMap[climbIndex]+"\" />\n</climbing>\n</controlSchme");
        //TODO: KYLE, Please save this text to a file named "driverName.xml"
    }
    public static void loadDriveFiles(String driverName){
        //TODO: KYLE, Please load file named "driverName.xml" and save the files text to a string named xmlString
        
    }
}
