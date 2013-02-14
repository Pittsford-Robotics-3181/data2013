/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.yr2013;
import com.sun.squawk.microedition.io.FileConnection;
import edu.wpi.first.wpilibj.Preferences;
import java.io.DataOutputStream;
import java.util.Date;
import javax.microedition.io.Connector;
/**
 *
 * @author robbiemarkwick
 */
public class Logging {
    static String logString="<?xml version=\"1.0\"?>\n<log>\n";
    public static void init(){
	logString="<?xml version=\"1.0\"?>\n<log>\n";
    }
    /**
     * Adds stuff to log files
     * @param items
     * @param ControlScheme 
     */
    public static void logItems(Loggable items[],boolean controlScheme,boolean autonomous){
	logString = logString.concat("<mainThread>\n");
	if(controlScheme)logString = logString.concat(ControlScheme.logString(autonomous));
	for(int i=0;i<items.length;i++){
	    logString = logString.concat(items[i].logString());
	}
	logString = logString.concat("</mainThread>\n");
    }
    public static void export(){
	logString = logString.concat("</log>");
	try{
	    String logFilePath="/robotLogs/";
	    logFilePath=logFilePath.concat(Preferences.getInstance().getString("DriverName","anonymousDriver")+"_");
	    logFilePath=logFilePath.concat(Preferences.getInstance().getString("AUXName","anonymousAuxDriver")+"_");
	    Date d=new Date();//Get the Current Date
	    d.setTime(d.getTime()-15706*24*60*60*1000);//Reduce to milliseconds since start of 2013
	    int month=(int) d.getTime()>31*24*60*1000?(d.getTime()>59*24*60*1000?2:1):0;//Extract month
	    if(month!=0)d.setTime(d.getTime()-(month==1?31:59)*24*60*60*1000);//Reduce date to milliseconds since start of montth
	    int day=(int) (d.getTime()/(24*60*60*1000));//Extract Day
	    d.setTime(d.getTime()%(24*60*60*1000));//Reduce date to milliseconds since start of the day
	    int hour=(int) (d.getTime()/(60*60*1000));//Extract Hour
	    d.setTime(d.getTime()%(60*60*1000));//Reduce date to milliseconds since start of the hour
	    int minute=(int) (d.getTime()/(60*1000));//Extract Minute
	    d.setTime(d.getTime()%(60*1000));//Reduce date to milliseconds since start of the minute
	    int second=(int) (d.getTime()/(1000));//Extract Seconds
	    
	    logFilePath=logFilePath.concat(month+"-"+day+"_"+hour+"-"+(minute<10?"0":"")+minute+"-"+(second<10?"0":"")+second+".xml");
	   /* FileConnection fc = (FileConnection)Connector.open(logFilePath, Connector.WRITE);
	    fc.create(); 
	    DataOutputStream theFile = fc.openDataOutputStream();
	    theFile*/
	    Connector.openDataOutputStream(logFilePath).writeChars(logString);
	}
	catch(Exception e){
	    //Do something
	}
    }
}
