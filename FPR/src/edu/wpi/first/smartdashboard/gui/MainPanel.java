/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.smartdashboard.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.*;
import javax.swing.JPanel;
import org.jnativehook.*;
import org.jnativehook.keyboard.*;

/**
 * Contains the DashboardPanels of the SmartDashboard and
 * gives functionality to swap between them.
 * @author Sam
 */
public final class MainPanel extends JPanel {
    
    public static final HashMap<String, DashboardPanel> panels = new HashMap();
    private static DashboardPanel currentPanel;
	
	private static double originX;
	private static double originY;
	private static boolean running = false;
	private static double xPixelsPerSecond;
	private static double yPixelsPerSecond;
	private long oldMillis = System.currentTimeMillis();
	private Robot r;
	BufferedImage invisibleCursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(invisibleCursorImg, new Point(0, 0), "blank cursor");

	private NativeKeyListener nkl = new NativeKeyListener(){

		@Override
		public void nativeKeyPressed(NativeKeyEvent nke) {
		}

		@Override
		public void nativeKeyReleased(NativeKeyEvent nke) {
			if(nke.getKeyCode()==NativeKeyEvent.VK_ESCAPE){
				running = !running;
			}
		}

		@Override
		public void nativeKeyTyped(NativeKeyEvent nke) {
		}
		
	};
	
	{	try {
			GlobalScreen.registerNativeHook();
		}
		catch(NativeHookException ex) {
			Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
		}
}
	GlobalScreen globalScreen = GlobalScreen.getInstance();
	private Thread m_mouseVelocityThread = new Thread(){
		@Override
		public void run(){
			try {
				Thread.sleep(100);
			} catch(InterruptedException ex) {
				Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
			}
			while(true){
			if(running){
				r.mouseMove((int)originX, (int)originY);
				oldMillis = System.currentTimeMillis();
				try{
		originX = getLocationOnScreen().getX()+getWidth ()/2;
		originY = getLocationOnScreen().getY()+getHeight()/2;
				} catch(Exception e){}
			}
				try {
					Thread.sleep(50);
				} catch(InterruptedException ex) {
					Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
				}
				if(running){
					currentPanel.setCursor(blankCursor);
				xPixelsPerSecond=(MouseInfo.getPointerInfo().getLocation().getX()-originX)/(System.currentTimeMillis()-oldMillis);
				yPixelsPerSecond=(MouseInfo.getPointerInfo().getLocation().getY()-originY)/(System.currentTimeMillis()-oldMillis);
					edu.wpi.first.smartdashboard.robot.Robot.getTable().putBoolean("IsFPR",true);
				edu.wpi.first.smartdashboard.robot.Robot.getTable().putNumber("MouseXVelocity",xPixelsPerSecond);
				edu.wpi.first.smartdashboard.robot.Robot.getTable().putNumber("MouseYVelocity",yPixelsPerSecond);
				} else {
					currentPanel.setCursor(Cursor.getDefaultCursor());
					xPixelsPerSecond = 0;
					yPixelsPerSecond = 0;
					edu.wpi.first.smartdashboard.robot.Robot.getTable().putBoolean("IsFPR",false);
					try {
						Thread.sleep(200);
					}
					catch(InterruptedException ex) {
						Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
		}
		}
	};
    
    public MainPanel(LayoutManager layout, DashboardPanel defaultPanel, DashboardPanel... panels) {
        super(layout);
        for(DashboardPanel panel : panels)
            MainPanel.panels.put(panel.getName(), panel);
        currentPanel = defaultPanel;
		globalScreen.addNativeKeyListener(nkl);
		try {
				r = new Robot();
			} catch(AWTException ex) {
				Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
			}
		r.setAutoDelay(0);
		m_mouseVelocityThread.start();
    }
	
	public static double getXPixelsPerSecond(){
		return xPixelsPerSecond;
	}
	
	public static double getYPixelsPerSecond(){
		return yPixelsPerSecond;
	}
	
	public static boolean isFirstPerson(){
		return running;
	}
    
    public static DashboardPanel getCurrentPanel() {
        return currentPanel;
    }
    
    public static DashboardPanel getPanel(String name) {
        return panels.get(name);
    }
    
    public void setCurrentPanel(DashboardPanel panel) {
        if(panels.containsValue(panel))
            currentPanel = panel;
        else throw new IllegalArgumentException("Not a valid panel");
    }
    
    public void addPanel(String name, DashboardPanel panel) {
        if(!panels.containsValue(panel))
            panels.put(name, panel);
        else throw new IllegalArgumentException("That panel already exists");
    }
    
}
