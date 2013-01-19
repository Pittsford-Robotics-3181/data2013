/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Has a bunch of methods available or use throughout the robot.
 * @author robbiemarkwick
 */
public class Utils{
    public static final double kDefaultRampStepSize=0.02;
    public static final int kDefaultTicksPerSecond=20;
    public static final double kDefaultDegreeClearnace=10;

    /**
     * Ramps up the drive system by a certain amount 20 times per second until desired speed has been reached
     * @param targetSpeed
     * @param controller 
     */
    public static void ramp(double targetSpeed,SpeedController controller, int ticksPerSecond, double stepSize){
        RampThread thread=new RampThread(controller,targetSpeed, ticksPerSecond, stepSize);//create the thread
        thread.start();//Run the Thread
    }
    public static double checkClearance(double inputValue,double clearance){
        return Math.abs(inputValue)>clearance?inputValue:0;
    }
    public static double checkAngle(double inputAngle,double clearance,boolean include45){
        if(Math.abs(inputAngle)<clearance)return 0;
        if(Math.abs(inputAngle-45)<clearance&&include45)return 45;
        if(Math.abs(inputAngle-90)<clearance)return 90;
        if(Math.abs(inputAngle-135)<clearance&&include45)return 135;
        if(Math.abs(inputAngle-180)<clearance)return 180;
        if(Math.abs(inputAngle-225)<clearance&&include45)return 225;
        if(Math.abs(inputAngle-270)<clearance)return 270;
        if(Math.abs(inputAngle-315)<clearance&&include45)return 315;
        if(Math.abs(inputAngle-360)<clearance)return 0;
        return inputAngle;
    }
}
/**
 * Thread used by ramp method in utils to do the ramping
 * @author robbiemarkwick
 */
class RampThread extends Thread{
    private double stepSize=0.02;//the constant that the motor increases 20 times/second
    private SpeedController speed;//The SpeedController being adjusted
    private double targetSpeed;//The Desired Speed
	private int ticksPerSecond = 20;
    private boolean isUp;//Is the motor ramping up or down?
    /**
     * Creates A new Ramping Thread
     * @param control SpeedController in focus
     * @param target Target Speed
     */
    RampThread(SpeedController control, double target, int ticksPerSecond, double stepSize){
        speed=control;
        targetSpeed=target;
		this.ticksPerSecond=ticksPerSecond;
		this.stepSize = stepSize;
		
    }
    /**
     * Run method used by every thread
     * Actually does the ramping
     */
    public void run(){
		double currentSpeed = speed.get();
        isUp=targetSpeed>speed.get();//determine whether increasing or decreasing
        /*
         * Ramps up the drive system by a certain amount 20 times per second
         * Thread stops for one of two conditions:
         *  Target speed has been reached or passed 
         *  Something else has set the speed of the speed controller
         */
        try {
            while((isUp && currentSpeed < targetSpeed) || (!isUp && currentSpeed > targetSpeed)){
                if(Math.abs(targetSpeed)<Math.abs(currentSpeed))currentSpeed=targetSpeed;//We don't need to Ramp towards zero
                else{
                    currentSpeed+=isUp?stepSize:-stepSize;
                    if((isUp && currentSpeed < targetSpeed) || (!isUp && currentSpeed > targetSpeed))currentSpeed=targetSpeed;
                }
                speed.set(currentSpeed);
                Thread.sleep(1000/ticksPerSecond);//Delay for some time
           }
        }
        catch (Exception ex) {
            //Do somehting
        }
    }
}