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
    public static void ramp(double targetSpeed,SpeedController controller){
        RampThread thread=new RampThread(controller,targetSpeed);//create the thread
        thread.start();//Run the Thread
    }
    
}
/**
 * Thread used by ramp method in utils to do the ramping
 * @author robbiemarkwick
 */
class RampThread extends Thread{
    private static final double rampConstant=0.02;//the constant that the motor increases 20 times/second
    public SpeedController speed;//The SpeedController being adjusted
    public double targetSpeed;//The Desired Speed
    private boolean isUp;//Is the motor ramping up or down?
    /**
     * Creates A new Ramping Thread
     * @param control SpeedController in focus
     * @param target Target Speed
     */
    RampThread(SpeedController control, double target){
        speed=control;
        targetSpeed=target;
    }
    /**
     * Run method used by every thread
     * Actually does the ramping
     */
    public void run(){
        double currentSpeed=speed.get();//set read the current speed
        isUp=targetSpeed>speed.get();//determine wether increasing or decreasing
        /*
         * Ramps up the drive system by a certain amount 20 times per second
         * Thread stops for one of two conditions:
         *  Target speed has been reached or passed 
         *  Something else has set the speed of the speed controller
         */
        try {
            for(;currentSpeed==speed.get()&&((isUp&&currentSpeed<targetSpeed)||(!isUp&&currentSpeed>targetSpeed));){
                currentSpeed+=isUp?rampConstant:(-rampConstant);//Increase or Decrease the current speed
                if((isUp&&currentSpeed>targetSpeed)||(!isUp&&currentSpeed<targetSpeed)) currentSpeed=targetSpeed;//Dont Pass the target
                speed.set(currentSpeed);//Tell the speed controller to change speed
                Thread.sleep(50);//Delay for 50 milliseconds
           }
        }
        catch (InterruptedException ex) {
            //Do somehting
        }
    }
}