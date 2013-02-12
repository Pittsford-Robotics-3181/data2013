/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

import edu.wpi.first.smartdashboard.gui.*;
import edu.wpi.first.smartdashboard.properties.*;
import edu.wpi.first.smartdashboard.types.*;
import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.logging.*;
import org.jnativehook.*;
import org.jnativehook.mouse.*;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class TimerWidget extends Widget{
	public static final String NAME = "Countdown Timer";
	public static final DataType[] TYPES = { DataType.NUMBER };
	long initialTime = System.currentTimeMillis();
	char colorMask = 0b1000000000000000;
	int timeRemaining = 120;
	boolean teamColor;
	Font tf2 = null;
	{	try {
			GlobalScreen.registerNativeHook();
		}
		catch(NativeHookException ex) {
			Logger.getLogger(TimerWidget.class.getName()).log(Level.SEVERE, null, ex);
		}
}
	@Override
	public void setValue(Object value) {
		teamColor = (((Double)value).intValue() & colorMask) == colorMask;
		timeRemaining = (char)(((Double)value).intValue() & ~colorMask);
		initialTime = System.currentTimeMillis();
	}

	@Override
	public void init() {
		setPreferredSize(new Dimension(128,48));
		try {
			tf2 = Font.createFont (Font.TRUETYPE_FONT,this.getClass().getResourceAsStream("TF2.ttf")).deriveFont(24f);
		}
		catch(FontFormatException ex) {
			Logger.getLogger(HealthHudItem.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch(IOException ex) {
			Logger.getLogger(HealthHudItem.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void propertyChanged(Property property) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.addRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB));
		g2.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
		g2.setColor(Color.WHITE);
		g2.fillRoundRect(0, 0, getWidth(), getHeight(),4,4);
		g2.setColor(teamColor ? Color.RED : Color.BLUE);
		g2.fillRoundRect(3,3,getWidth()-6,getHeight()-6,4,4);
		g2.setColor(new Color(100,100,100));
		g2.fillOval(80,8,32,32);
		g2.setColor(new Color(245,234,204));
		try{
		g2.fillArc(80,8,32,32,90,-(int)(360*(System.currentTimeMillis()/1000 - initialTime/1000)/(System.currentTimeMillis()/1000 - initialTime/1000 + timeRemaining)));
		} catch(Exception e){}
		g2.setFont(tf2);
		g2.drawString(timeFormat(timeRemaining),10,32);
	}
	private String timeFormat(int seconds){
		return (seconds/60 < 10 ? "0" : "") + seconds/60 + ":" + (seconds%60 < 10 ? "0" : "") + seconds%60;
	}
	{new Thread(){
		public void run(){
			while(true){
				try {
					Thread.sleep(1000);
				}
				catch(InterruptedException ex) {
					Logger.getLogger(TimerWidget.class.getName()).log(Level.SEVERE, null, ex);
				}
				repaint();
			}
		}
	}.start();}
}
