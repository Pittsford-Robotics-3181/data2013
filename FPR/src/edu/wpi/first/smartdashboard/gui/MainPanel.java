/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.smartdashboard.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.*;
import javax.swing.JPanel;

/**
 * Contains the DashboardPanels of the SmartDashboard and
 * gives functionality to swap between them.
 * @author Sam
 */
public final class MainPanel extends JPanel {
    
    public static final HashMap<String, DashboardPanel> panels = new HashMap();
    private static DashboardPanel currentPanel;
	
	private double originX;
	private double originY;
	private boolean running = true;
	private double xPixelsPerSecond;
	private double yPixelsPerSecond;
	private long oldMillis = System.currentTimeMillis();
	Robot r;
	
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
		originX = getLocationOnScreen().getX()+(double)getWidth ()/2;
		originY = getLocationOnScreen().getY()+(double)getHeight()/2;
				} catch(Exception e){}
			}
				try {
					Thread.sleep(50);
				} catch(InterruptedException ex) {
					Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
				}
				if(running){
				xPixelsPerSecond=(MouseInfo.getPointerInfo().getLocation().getX()-originX)/(System.currentTimeMillis()-oldMillis);
				yPixelsPerSecond=(MouseInfo.getPointerInfo().getLocation().getY()-originY)/(System.currentTimeMillis()-oldMillis);
				System.out.println("(" + xPixelsPerSecond + "," + yPixelsPerSecond + ")");
			} else {
					xPixelsPerSecond = 0;
					yPixelsPerSecond = 0;
				}
		}
		}
	};
    
    public MainPanel(LayoutManager layout, DashboardPanel defaultPanel, DashboardPanel... panels) {
        super(layout);
        for(DashboardPanel panel : panels)
            MainPanel.panels.put(panel.getName(), panel);
        currentPanel = defaultPanel;
		
		try {
				r = new Robot();
			} catch(AWTException ex) {
				Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
			}
		r.setAutoDelay(0);
		m_mouseVelocityThread.start();
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
