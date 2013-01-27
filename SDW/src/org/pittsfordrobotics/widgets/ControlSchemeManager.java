/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.*;
import edu.wpi.first.smartdashboard.robot.Robot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.io.File;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;

/**
 *
 * @author robbiemarkwick
 */
public class ControlSchemeManager extends StaticWidget {

    public static final int shootingIndex = 0;
    public static final int spinningIndex = 1;
    public static final int driveLeftSideIndex = 2;
    public static final int driveRightSideIndex = 3;
    public static final int aimUpIndex = 4;
    public static final int aimDownIndex = 5;
    public static final int beginClimbIndex = 6;
    public static final int climbIndex = 7;
    public static final int climbExtendIndex = 8;
    static final String directory = "./driverPrefs";
    public int[] joystickMap = {1, 1, 0, 0, 1, 1, 1, 1, 1};
    public int[] buttonMap = {0, 7, 6, 7, 4, 5, 1, 2, 3};
    private String[] descriptions = {"Shoot Disc", "Spin Up Shooter", "Rotate robot left", "Rotate robot right", "Aim shooter up", "Aim shooter down", "Position For Climbing", "Pull Climbing Arm", "Extend Climbing Arm"};
    public MultiProperty shootButton = new MultiProperty(this, descriptions[0]);
    public MultiProperty spinUpButton = new MultiProperty(this, descriptions[1]);
    public MultiProperty turnLeft = new MultiProperty(this, descriptions[2]);
    public MultiProperty turnRight = new MultiProperty(this, descriptions[3]);
    public MultiProperty aimUp = new MultiProperty(this, descriptions[4]);
    public MultiProperty aimDown = new MultiProperty(this, descriptions[5]);
    public MultiProperty beginClimb = new MultiProperty(this, descriptions[6]);
    public MultiProperty climb = new MultiProperty(this, descriptions[7]);
    public MultiProperty climbExt = new MultiProperty(this, descriptions[8]);
    public String driverName = "default";
    public String auxiliaryName = "default";

    public void initProperties(MultiProperty mp) {
	for (int i = 0; i < 2; i++) {
	    for (int j = 0; j <= 12; j++) {
		mp.add((j != 0 ? ("Joystick:" + (i + 1) + " Button:" + (j)) : ("Joystick:" + (i + 1) + " Trigger")), i * 0x10 + j);
	    }
	}
    }

    /**
     * changes the drivers name.
     * <p/>
     * @param driverName
     */
    public void setDriverName(String driverName) {
	this.driverName = driverName;
	Robot.getPreferences().putString("DriverName", driverName);
    }
    /**
     * changes Auxiliary driver's name.
     * <p/>
     * @param driverName 
     */
    public void setAuxiliaryName(String driverName) {
	this.auxiliaryName = driverName;
	Robot.getPreferences().putString("AUXName", driverName);
    }
    @Override
    public void init() {
	NetworkTable.setTeam(3181);//tell the network table which Robot we are interested in
	initProperties(shootButton);
	initProperties(spinUpButton);
	initProperties(turnLeft);
	initProperties(turnRight);
	initProperties(aimUp);
	initProperties(aimDown);
	initProperties(beginClimb);
	initProperties(climb);
	initProperties(climbExt);
    }

    @Override
    public void propertyChanged(Property property) {
	if (property == shootButton) {
	    setJoystickAndButtonForFunction((Integer) (property.getValue()) / 0x10, (Integer) (property.getValue()) % 0x10, shootingIndex, true);
	} else if (property == spinUpButton) {
	    setJoystickAndButtonForFunction((Integer) property.getValue() / 0x10, (Integer) property.getValue() % 0x10, spinningIndex, true);
	} else if (property == turnLeft) {
	    setJoystickAndButtonForFunction((Integer) property.getValue() / 0x10, (Integer) property.getValue() % 0x10, driveLeftSideIndex, true);
	} else if (property == turnRight) {
	    setJoystickAndButtonForFunction((Integer) property.getValue() / 0x10, (Integer) property.getValue() % 0x10, driveRightSideIndex, true);
	} else if (property == aimUp) {
	    setJoystickAndButtonForFunction((Integer) property.getValue() / 0x10, (Integer) property.getValue() % 0x10, aimUpIndex, true);
	} else if (property == aimDown) {
	    setJoystickAndButtonForFunction((Integer) property.getValue() / 0x10, (Integer) property.getValue() % 0x10, aimDownIndex, true);
	} else if (property == beginClimb) {
	    setJoystickAndButtonForFunction((Integer) property.getValue() / 0x10, (Integer) property.getValue() % 0x10, beginClimbIndex, true);
	} else if (property == climb) {
	    setJoystickAndButtonForFunction((Integer) property.getValue() / 0x10, (Integer) property.getValue() % 0x10, climbIndex, true);
	} else if (property == climbExt) {
	    setJoystickAndButtonForFunction((Integer) property.getValue() / 0x10, (Integer) property.getValue() % 0x10, climbExtendIndex, true);
	}
    }

    /**
     * saves the control map for current driver
     */
    public void saveDriveFiles() {
	/*Driver*/
	try {
	    /*
	     * set up the XML document
	     */
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.newDocument();
	    Element root = doc.createElement("controlMap");
	    doc.appendChild(root);//make the Root Element
			/*
	     * Create the elements defining the control scheme
	     */
	    for (int i = 0; i < buttonMap.length; i++) {//loop for each function
		if (joystickMap[i] == 0) {
		    Element item = doc.createElement(elementNameForFunction(i));//create the element for the function
		    //set the button value
		    item.setTextContent("" + buttonMap[i]);
		    //add element to the root element
		    root.appendChild(item);
		}
	    }
	    /*
	     * Write the document to the file
	     */
	    String pathToFile = directory + auxiliaryName + "DRIVER.xml";
	    File driverFile = new File(pathToFile);
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
	/*Auxiliary*/
	try {
	    /*
	     * set up the XML document
	     */
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.newDocument();
	    Element root = doc.createElement("controlMap");
	    doc.appendChild(root);//make the Root Element
			/*
	     * Create the elements defining the control scheme
	     */
	    for (int i = 0; i < buttonMap.length; i++) {//loop for each function
		if (joystickMap[i] == 1) {
		    Element item = doc.createElement(elementNameForFunction(i));//create the element for the function
		    //set the button value
		    item.setTextContent("" + buttonMap[i]);
		    //add element to the root element
		    root.appendChild(item);
		}
	    }
	    /*
	     * Write the document to the file
	     */
	    String pathToFile = directory + driverName + "AUX.xml";
	    File driverFile = new File(pathToFile);
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
     * Separate files for Driver and Auxiliary
     * if both have a function mapped to their joystick, Auxiliary wins
     * if a function is mapped to neither, it is mapped to the default
     * if this causes a conflict, it finds another button on that joystick
     * DO NOT LET THE DRIVERS DO ANYTHING BEFORE YOU CALL THIS
     */
    private void loadDriveFiles() {
	/*
	 * set the map to -1 to avoid conflicts
	 */
	for (int i = 0; i < buttonMap.length; i++) {
	    joystickMap[i] = -1;
	    buttonMap[i] = -1;
	}
	/*Driver*/
	try {
	    /*
	     * Locate the file
	     */
	    String pathToFile = directory + driverName + "DRIVER.xml";
	    File driverFile = new File(pathToFile);
	    /*
	     * Don't parse a file that doesn't exist
	     */
	    if (!driverFile.exists()) {
		Exception ex = new Exception();
		throw ex;
	    }
	    /*
	     * Load and process the XML
	     */
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.parse(driverFile);
	    Element root = doc.getDocumentElement();//get the Root Element
		/*
	     * Look at the elements defining the control scheme
	     */
	    for (int i = 0; i < buttonMap.length; i++) {//loop for each function
		try {
		    Element item = (Element) root.getElementsByTagName(elementNameForFunction(i)).item(0);//element for the function
		    int butt = Integer.parseInt(item.getTextContent());//read the button number
		    setJoystickAndButtonForFunction(0, butt, i, false);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	    }

	} catch (Exception ex) {
	}
	/*Auxiliary Driver*/
	try {
	    /*
	     * Locate the file
	     */
	    String pathToFile = directory + auxiliaryName + "AUX.xml";
	    File driverFile = new File(pathToFile);
	    /*
	     * Don't parse a file that doesn't exist
	     */
	    if (!driverFile.exists()) {
		Exception ex = new Exception();
		throw ex;
	    }
	    /*
	     * Load and process the XML
	     */
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.parse(driverFile);
	    Element root = doc.getDocumentElement();//get the Root Element
			/*
	     * Look at the elements defining the control scheme
	     */
	    for (int i = 0; i < buttonMap.length; i++) {//loop for each function
		try {
		    Element item = (Element) root.getElementsByTagName(elementNameForFunction(i)).item(0);//element for the function
		    int butt = Integer.parseInt(item.getTextContent());//read the button number
		    setJoystickAndButtonForFunction(1, butt, i, false);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	    }

	} catch (Exception ex) {
	}
	loadDefaultConfig();//if something is not mapped, load the default
		/*
	 * TODO: tell Ben to redraw based on the new map values
	 */
    }

    private String elementNameForFunction(int index) {
	switch (index) {
	    case shootingIndex:
		return "shooting";
	    case spinningIndex:
		return "spinning";
	    case driveLeftSideIndex:
		return "leftRot";
	    case driveRightSideIndex:
		return "rightRot";
	    case aimUpIndex:
		return "aimUp";
	    case aimDownIndex:
		return "aimDown";
	    case beginClimbIndex:
		return "beginClimb";
	    case climbIndex:
		return "climb";
	    case climbExtendIndex:
		return "climbExtend";
	    default:
		return "";
	}
    }

    /**
     * loads the default configuration if there was a problem (or this is the
     * driver's first time)
     */
    private void loadDefaultConfig() {
	if (joystickMap[0] == -1 || buttonMap[0] == -1) {
	    setJoystickAndButtonForFunction(1, 0, shootingIndex, false);
	}
	if (joystickMap[1] == -1 || buttonMap[1] == -1) {
	    setJoystickAndButtonForFunction(1, 7, spinningIndex, false);
	}
	if (joystickMap[2] == -1 || buttonMap[2] == -1) {
	    setJoystickAndButtonForFunction(0, 6, driveLeftSideIndex, false);
	}
	if (joystickMap[3] == -1 || buttonMap[3] == -1) {
	    setJoystickAndButtonForFunction(0, 7, driveRightSideIndex, false);
	}
	if (joystickMap[4] == -1 || buttonMap[4] == -1) {
	    setJoystickAndButtonForFunction(1, 4, aimUpIndex, false);
	}
	if (joystickMap[5] == -1 || buttonMap[5] == -1) {
	    setJoystickAndButtonForFunction(1, 5, aimDownIndex, false);
	}
	if (joystickMap[6] == -1 || buttonMap[6] == -1) {
	    setJoystickAndButtonForFunction(1, 1, beginClimbIndex, false);
	}
	if (joystickMap[7] == -1 || buttonMap[7] == -1) {
	    setJoystickAndButtonForFunction(1, 2, climbIndex, false);
	}
	if (joystickMap[8] == -1 || buttonMap[8] == -1) {
	    setJoystickAndButtonForFunction(1, 3, climbExtendIndex, false);
	}
	saveDriveFiles();
    }

    /**
     * Tells the Robot to remap its control Scheme
     * if we are loading and there is a conflict, find an open button
     * <p/>
     * @param stick 0 if it should be on the driving stick, 1 if it should be on
     * the auxiliary stick
     * @param button 0 if it should be the trigger, otherwise the number of the
     * button (1-12)
     * @param function the index of the function (please refer to the constants)
     * @param shouldSave whether or not the scheme should save. True unless
     * calling from a method that is loading the file.
     * <p/>
     * @return remap succeeded
     */
    public boolean setJoystickAndButtonForFunction(int stick, int button, int function, boolean shouldSave) {
	/*
	 * make sure that we are not mapping to a button alrady in use
	 */
	if (isOccupied(stick, button)) {
	    if (shouldSave) return false;
	    for (int i = 0; i <= 12; i++) {
		if (!isOccupied(stick, i)) {
		    setJoystickAndButtonForFunction(stick, i, function, shouldSave);
		    break;
		}
	    }
	}
	/*
	 * remap the local reference
	 */
	joystickMap[function] = stick;
	buttonMap[function] = button;

	/*
	 * Tell the Robot to remap the control
	 */
	switch (function) {
	    case shootingIndex: {
		Robot.getPreferences().putNumber("ShootingStick", stick);
		Robot.getPreferences().putNumber("ShootingButt", button);
		shootButton.setValue(stick * 0x10 + button);
	    }
	    break;
	    case spinningIndex: {
		Robot.getPreferences().putNumber("SpinningStick", stick);
		Robot.getPreferences().putNumber("SpinningButt", button);
		spinUpButton.setValue(stick * 0x10 + button);
	    }
	    break;
	    case driveLeftSideIndex: {
		Robot.getPreferences().putNumber("DriveLeftStick", stick);
		Robot.getPreferences().putNumber("DriveLeftButt", button);
		turnLeft.setValue(stick * 0x10 + button);
	    }
	    break;
	    case driveRightSideIndex: {
		Robot.getPreferences().putNumber("DriveRightStick", stick);
		Robot.getPreferences().putNumber("DriveRightButt", button);
		turnRight.setValue(stick * 0x10 + button);
	    }
	    break;
	    case aimUpIndex: {
		Robot.getPreferences().putNumber("AimUpStick", stick);
		Robot.getPreferences().putNumber("AimUpButt", button);
		aimUp.setValue(stick * 0x10 + button);
	    }
	    break;
	    case aimDownIndex: {
		Robot.getPreferences().putNumber("AimDownStick", stick);
		Robot.getPreferences().putNumber("AimDownButt", button);
		aimDown.setValue(stick * 0x10 + button);
	    }
	    break;
	    case beginClimbIndex: {
		Robot.getPreferences().putNumber("BeginClimbStick", stick);
		Robot.getPreferences().putNumber("BeginClimbButt", button);
		beginClimb.setValue(stick * 0x10 + button);

	    }
	    break;
	    case climbIndex: {
		Robot.getPreferences().putNumber("ClimbStick", stick);
		Robot.getPreferences().putNumber("ClimbButt", button);
		climb.setValue(stick * 0x10 + button);

	    }
	    break;
	    case climbExtendIndex: {
		Robot.getPreferences().putNumber("ClimbExtendStick", stick);
		Robot.getPreferences().putNumber("ClimbExtendButt", button);
		climbExt.setValue(stick * 0x10 + button);

	    }
	    break;
	}
	/*
	 * Save the file unless it is being loaded
	 */
	if (shouldSave) {
	    saveDriveFiles();
	}
	return true;
    }

    private boolean isOccupied(int stick, int button) {
	for (int i = 0; i < buttonMap.length; i++) {
	    if (stick == joystickMap[i] && button == buttonMap[i]) {
		return true;
	    }

	}
	return false;
    }
}
