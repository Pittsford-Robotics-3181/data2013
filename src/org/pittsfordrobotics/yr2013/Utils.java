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
    /**
     * Ramps up the drive system by a certain amount 20 times per second until desired speed has been reached
     * @param targetSpeed
     * @param controller 
     */
    public static void ramp(double targetSpeed,SpeedController controller, int ticksPerSecond, double stepSize){
        RampThread thread=new RampThread(controller,targetSpeed, ticksPerSecond, stepSize);//create the thread
        thread.start();//Run the Thread
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
            while((isUp && Math.abs(currentSpeed) < Math.abs(targetSpeed)) || (!isUp && Math.abs(currentSpeed) > Math.abs(targetSpeed))){
                speed.set(Math.abs(currentSpeed)/currentSpeed == Math.abs(targetSpeed)/targetSpeed ? targetSpeed : currentSpeed + stepSize*Math.abs(targetSpeed)/targetSpeed);//Tell the speed controller to change speed
                Thread.sleep(1000/ticksPerSecond);//Delay for some time
           }
        }
        catch (InterruptedException ex) {
            //Do somehting
        }
    }
}