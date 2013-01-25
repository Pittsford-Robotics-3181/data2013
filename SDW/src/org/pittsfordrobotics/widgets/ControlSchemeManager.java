/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.io.File;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Element;

/**
 *
 * @author robbiemarkwick
 */
public class ControlSchemeManager  extends StaticWidget{
    public static final int shootingIndex = 0;
    public static final int spinningIndex = 1;
    public static final int driveLeftSideIndex = 2;
    public static final int driveRightSideIndex = 3;
    public static final int aimUpIndex = 4;
    public static final int aimDownIndex = 5;
    public static final int beginClimbIndex = 6;
    public static final int climbIndex = 7;
    static final String directory="/";
    public static boolean NetworkIsIdiot;//The Network is being an idiot
    
    public  int[] joystickMap={1,1,0,0,1,1,1,1}; 
    public  int[] buttonMap={0,7,6,7,4,5,1,2};
      
    private String driverName;
     /**
     * @return the driver's name
     */
    public String getDriverName(){
	return driverName;
    }
    /**
     * changes the drivers name. Also loads their drive files (or the defaults)
     * @param driverName 
     */
    public void setDriverName(String driverName){
	this.driverName=driverName;
	loadDriveFiles();
    }
    
    
    
    @Override
    public void init() {
	NetworkTable.setTeam(3181);//tell the network table which Robot we are interested in
	Moniter watch=new Moniter();//create the Thread to watch for ControlScheme issues
	watch.start();//run the thread
	NetworkMoniter net=new NetworkMoniter();//create the Thread to moniter network issues
	net.start();//run the thread
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void propertyChanged(Property property) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
   
    
    
    /**
     * saves the control map for current driver
     */
    public void saveDriveFiles(){
	try {
	    /*set up the XML document*/
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc= builder.newDocument();
		    Element root=doc.createElement("controlMap");
		    doc.appendChild(root);//make the Root Element
		    /*Create the elements defining the control scheme*/
		    for (int i = 0; i < buttonMap.length; i++) {//loop for each function
			Element item=doc.createElement(elementNameForFunction(i));//create the element for the function
			//set the joystick value
			Element joy=doc.createElement("joystick");
			joy.setTextContent(""+joystickMap[i]);
			//set the button value
			Element butt=doc.createElement("button");
			butt.setTextContent(""+buttonMap[i]);
			//add elements to the root element
			item.appendChild(joy);
			item.appendChild(butt);
			root.appendChild(item);
		    }
		    /*Write the document to the file*/
		    String pathToFile=directory+driverName+".xml";
		    File driverFile=new File(pathToFile);
		    //the rest of this is form the internet
		    TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer();
		    DOMSource source = new DOMSource(doc);
		    StreamResult result = new StreamResult(driverFile);
		    // To Output to console for testing, uncomment next line and comment previous line
		    // StreamResult result = new StreamResult(System.out);
		    transformer.transform(source, result);
 
	} catch (Exception ex) {
	    //Do something
	}
    }
    /**
     * Loads control map for the driver
     */
    private void loadDriveFiles(){
	/*set the map to -1 to avoid conflicts*/
	for (int i = 0; i < buttonMap.length; i++) {
	    joystickMap[i]=-1;
	    buttonMap[i]=-1;
	}
	/*Locate the file*/
	String pathToFile=directory+driverName+".xml";
	File driverFile=new File(pathToFile);
	    try {
		/*Don't parse a file that doesn't exist*/
		if(!driverFile.exists()){
		    Exception ex=new Exception();
		    throw ex;
		}
		/*Load and process the XML*/
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc= builder.parse(driverFile);
		Element root=doc.getDocumentElement();//get the Root Element
		/*Look at the elements defining the control scheme*/
		for (int i = 0; i < buttonMap.length; i++) {//loop for each function
		    Element item = (Element) root.getElementsByTagName(elementNameForFunction(i)).item(0);//element for the function
		    Element joystick = (Element) item.getElementsByTagName("joystick").item(0);
		    int joy=Integer.parseInt(joystick.getTextContent());//read the joystick number
		    Element button = (Element) item.getElementsByTagName("button").item(0);
		    int butt=Integer.parseInt(button.getTextContent());//read the button number
		    setJoystickAndButtonForFunction(joy,butt,i,false);
		}
		
	    } catch (Exception ex) {
		loadDefaultConfig();//if there is a problem loading the config, load the default
	    }
	    /*TODO: tell Ben to redraw based on the new map values*/
    }
    private String elementNameForFunction(int index){
	switch (index){
	    case shootingIndex:return"shooting";
	    case spinningIndex:return"spinning";
	    case driveLeftSideIndex:return"leftRot";
	    case driveRightSideIndex:return"rightRot";
	    case aimUpIndex:return"aimUp";
	    case aimDownIndex:return"aimDown";
	    case beginClimbIndex:return"beginClimb";
	    case climbIndex:return"climb";
	    default :return "";
	}
    }
    /**
     * loads the default configuration if there was a problem (or this is the driver's first time)
     */
    private void loadDefaultConfig(){
	setJoystickAndButtonForFunction(1,0,shootingIndex,false);  
	setJoystickAndButtonForFunction(1,7,spinningIndex,false);  
	setJoystickAndButtonForFunction(0,6,driveLeftSideIndex,false);  
	setJoystickAndButtonForFunction(0,7,driveRightSideIndex,false);  
	setJoystickAndButtonForFunction(1,4,aimUpIndex,false);  
	setJoystickAndButtonForFunction(1,5,aimDownIndex,false);  
	setJoystickAndButtonForFunction(1,1,beginClimbIndex,false);  
	setJoystickAndButtonForFunction(1,2,climbIndex,false);  
	saveDriveFiles();
    }
    
    /**
     * Tells the Robot to remap its control Scheme
     * @param stick 0 if it should be on the driving stick, 1 if it should be on the auxiliary stick
     * @param button 0 if it should be the trigger, otherwise the number of the button (1-12)
     * @param function the index of the function (please refer to the constants)
     * @param shouldSave whether or not the scheme should save. True unless calling from a method that is loading the file.
     * @return remap succeeded
     */
    public boolean setJoystickAndButtonForFunction(int stick, int button, int function,boolean shouldSave){
	 /*make sure that we are not mapping to a button alrady in use*/
	 for(int i=0; i<buttonMap.length;i++){
            if(stick==joystickMap[i]&&button==buttonMap[i])return false;
	 }
	 /*remap the local reference*/
	joystickMap[function]=stick;
        buttonMap[function]=button;
	
	/*Tell the Robot to remap the control*/
	  switch(function){
	    case shootingIndex:{
		  NetworkTable.getTable("Controls").putNumber("ShootingStick", stick);
		  NetworkTable.getTable("Controls").putNumber("ShootingButt", button);
		}break;
	    case spinningIndex:{
		  NetworkTable.getTable("Controls").putNumber("SpinningStick", stick);
		  NetworkTable.getTable("Controls").putNumber("SpinningButt", button);
		}break; 
	    case driveLeftSideIndex:{
		  NetworkTable.getTable("Controls").putNumber("DriveLeftStick", stick);
		  NetworkTable.getTable("Controls").putNumber("DriveLeftButt", button);
		}break;  
	    case driveRightSideIndex:{
		  NetworkTable.getTable("Controls").putNumber("DriveRightStick", stick);
		  NetworkTable.getTable("Controls").putNumber("DriveRightButt", button);
		}break;  
	    case aimUpIndex:{
		  NetworkTable.getTable("Controls").putNumber("AimUpStick", stick);
		  NetworkTable.getTable("Controls").putNumber("AimUpButt", button);
		}break;  
	    case aimDownIndex:{
		  NetworkTable.getTable("Controls").putNumber("AimDownStick", stick);
		  NetworkTable.getTable("Controls").putNumber("AimDownButt", button);
		}break;  
	    case beginClimbIndex:{
		  NetworkTable.getTable("Controls").putNumber("BeginClimbStick", stick);
		  NetworkTable.getTable("Controls").putNumber("BeginClimbButt", button);
		}break;  
	    case climbIndex:{
		  NetworkTable.getTable("Controls").putNumber("ClimbStick", stick);
		  NetworkTable.getTable("Controls").putNumber("ClimbButt", button);
		}break;  
	  }
	  NetworkTable.getTable("Controls").putBoolean("NeedsToRemap", true);//alert the robot that it needs to remap
	  /*Save the file unless it is being loaded*/
	  if(shouldSave)saveDriveFiles();
	  return true;
    }
    /**
     * Thread that makes sure there weren't any remap problems
     */
    class Moniter extends Thread {
	private static final int ticksPerSecond = 5;
	@Override
	public void run() {
	    try {
		while (true) {
		    /*check and see if there were any remap problems*/
		    if (NetworkTable.getTable("Controls").getBoolean("RemapFailure")&&!NetworkIsIdiot) {
			/*Remap everything based on what the Robot says*/
			joystickMap[0] = (int) NetworkTable.getTable("Controls").getNumber("ShootingStick");
			buttonMap[0] = (int) NetworkTable.getTable("Controls").getNumber("ShootingButt");
			joystickMap[1] = (int) NetworkTable.getTable("Controls").getNumber("SpinningStick");
			buttonMap[1] = (int) NetworkTable.getTable("Controls").getNumber("SpinningButt");
			joystickMap[2] = (int)NetworkTable.getTable("Controls").getNumber("DriveLeftStick");
			buttonMap[2] = (int) NetworkTable.getTable("Controls").getNumber("DriveLeftButt");
			joystickMap[3] = (int)NetworkTable.getTable("Controls").getNumber("DriveRightStick");
			buttonMap[3] = (int) NetworkTable.getTable("Controls").getNumber("DriveRightButt");
			joystickMap[4] = (int)NetworkTable.getTable("Controls").getNumber("AimUpStick");
			buttonMap[4] = (int) NetworkTable.getTable("Controls").getNumber("AimUpButt");
			joystickMap[5] = (int)NetworkTable.getTable("Controls").getNumber("AimDownStick");
			buttonMap[5] = (int) NetworkTable.getTable("Controls").getNumber("AimDownButt");
			joystickMap[6] = (int)NetworkTable.getTable("Controls").getNumber("BeginClimbStick");
			buttonMap[6] = (int) NetworkTable.getTable("Controls").getNumber("BeginClimbButt");
			joystickMap[7] = (int)NetworkTable.getTable("Controls").getNumber("ClimbStick");
			buttonMap[7] = (int) NetworkTable.getTable("Controls").getNumber("ClimbButt");
			/*Don't repeat this until another problem arises*/
			NetworkTable.getTable("Controls").putBoolean("RemapFailure", false);
			/*TODO: Tell Ben to redisplay the control map*/
		    }
		    Thread.sleep(1000 / ticksPerSecond);//Delay for some time
		}
	    } catch (Exception ex) {
		//Do somehting
	    }
	}
    }
    class NetworkMoniter extends Thread {
	private static final int ticksPerSecond = 1;
	@Override
	public void run() {
	    try {
		while (true) {
		    /*check and see if there were any remap problems*/
		    if (NetworkTable.getTable("Controls").getBoolean("NetworkIdiot")) {
			NetworkIsIdiot=true;
			/*TODO: Tell Ben that the network is being an idiot*/
		    }
		    NetworkTable.getTable("Controls").putBoolean("NetworkIdiot",true);//ask the robot to reply
		    Thread.sleep(1000 / ticksPerSecond);//Delay for some time
		}
	    } catch (Exception ex) {
		//Do somehting
	    }
	}
    }
}


