/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.properties.*;
import edu.wpi.first.smartdashboard.types.*;
import java.awt.*;
import java.io.*;
import java.util.logging.*;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class TimerWidget extends Widget{
	public static final String NAME = "Countdown Timer for the match";
	public static final DataType[] TYPES = { DataType.NUMBER };
	long initialTime = System.currentTimeMillis();
	char colorMask = 0b1000000000000000;
	int timeRemaining = 0;
	boolean teamColor;
	Font tf2 = null;

	@Override
	public void setValue(Object value) {
		teamColor = ((Character)value & colorMask) == colorMask;
		timeRemaining = ((Character)value & ~colorMask);
		initialTime = System.currentTimeMillis();
	}

	@Override
	public void init() {
		setPreferredSize(new Dimension(96,32));
		try {
			tf2 = Font.createFont (Font.TRUETYPE_FONT,this.getClass().getResourceAsStream("TF2.ttf")).deriveFont(32f);
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
		g2.setColor(Color.WHITE);
		g2.drawRect(0, 0, WIDTH, HEIGHT);
		g2.setColor(teamColor ? Color.RED : Color.BLUE);
		g2.drawRect(1,1,WIDTH-2,HEIGHT-2);
		g2.setColor(new Color(100,100,100));
		g2.drawOval(48,4,24,24);
		g2.setColor(new Color(245,234,204));
		g2.fillArc(48,4,24,24,90,(int)(360*(System.currentTimeMillis() - initialTime)/(System.currentTimeMillis() - initialTime + timeRemaining)));
		g2.setColor(new Color(100,100,100));
		g2.drawString("" + timeRemaining  / 60 + ":" + timeRemaining%60,4,0);
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException ex) {
			Logger.getLogger(TimerWidget.class.getName()).log(Level.SEVERE, null, ex);
		}
		repaint();
	}
}
