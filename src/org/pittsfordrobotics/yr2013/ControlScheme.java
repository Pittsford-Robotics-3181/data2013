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

    private static int[] joystickMap={1,1,0,0,1,1}; 
    private static int[] buttonMap={0,7,6,7,4,5};
    static Joystick joy1;//driving stick
    static Joystick joy2;//Shooting/Climbing Stick
    private static Joystick[] sticks={joy1,joy2};
    

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
    /**
     * Remap Joystick
     * @param stick 0 for first stick, 1 for second stick
     * @param button 0 for trigger, otherwise button 1-12
     * @param function index matching the function you want to remap (see the constants)
     */
    public static void setJoystickAndButtonForFunction(int stick, int button, int function){
        for(int i=0; i<=4;i++){
            if(stick==joystickMap[i]&&button==buttonMap[i])return;//don't remap if that button is already in use
        }
        joystickMap[function]=stick;
        buttonMap[function]=button;
    }
}
