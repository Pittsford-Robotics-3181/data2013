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
public class AmmoWidget extends Widget{
	public static final String NAME = "Countdown Timer for the match";
	public static final DataType[] TYPES = { DataType.NUMBER };
	char colorMask = 0b1000000000000000;
	Font tf2 = null;
	boolean teamColor;
	char disks = 0;
	@Override
	public void setValue(Object value) {
		teamColor = ((Character)value & colorMask) == colorMask;
		disks = (char)((Character)value & ~colorMask);
	}

	@Override
	public void init() {
		setPreferredSize(new Dimension(32,96));
		try {
			tf2 = Font.createFont (Font.TRUETYPE_FONT,this.getClass().getResourceAsStream("TF2.ttf")).deriveFont(32f);
		}
		catch(FontFormatException ex) {
			Logger.getLogger(AmmoWidget.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch(IOException ex) {
			Logger.getLogger(AmmoWidget.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void propertyChanged(Property property) {
		
	}
	@Override
	public void paintComponents(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.WHITE);
		g2.fillRoundRect(0,0,WIDTH,HEIGHT,4,4);
		g2.setColor(Color.RED);
		g2.fillRoundRect(1,1,WIDTH-2,HEIGHT-2,4,4);
		g2.setFont(tf2);
		g2.drawString(""+disks,0,0);
	}
}
