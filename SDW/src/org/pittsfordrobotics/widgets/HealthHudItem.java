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
import java.util.logging.*;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class HealthHudItem extends Widget {

	public static final String NAME = "Battery (Health) Widget";
	public static final DataType[] TYPES = {DataType.NUMBER};
	private Double voltage = 12.0;
	private Double voltageMultiplier = .5;
	private Font tf2 = null;

	@Override
	public void init() {
		setPreferredSize(new Dimension(100, 100));
		try {
			tf2 = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("TF2.ttf")).deriveFont(32f);
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
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		voltageMultiplier = Math.max(Math.min((voltage - 11) / 2, 1), 0);
		g2.addRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB));
		g2.setColor(new Color(54, 54, 54));
		g2.fillRect(getWidth() / 3, 0, getWidth() / 3, getHeight());
		g2.fillRect(0, getHeight() / 3, getWidth(), getHeight() / 3);
		g2.setPaint(new GradientPaint(getWidth() / 2, 0, new Color(245, (int)(234 * voltageMultiplier), (int)(204 * voltageMultiplier)), getWidth() / 2, getHeight(), new Color(172, (int)(165 * voltageMultiplier), (int)(146 * voltageMultiplier)), true));
		g2.fillRect(getWidth() * 7 / 18, getHeight() - (int)Math.round((getHeight() * 1 / 18 + voltageMultiplier * getHeight() * 8 / 9)), getWidth() * 2 / 9, (int)Math.round(voltageMultiplier * getHeight() * 8 / 9));
		g2.fillRect(getWidth() * 1 / 18, Math.max((getHeight() - (int)Math.round((getHeight() * 1 / 18 + voltageMultiplier * getHeight() * 8 / 9))), getHeight() * 7 / 18), getWidth() * 8 / 9, getHeight() * 2 / 9 - (Math.max((getHeight() - (int)Math.round((getHeight() * 1 / 18 + voltageMultiplier * getHeight() * 8 / 9))), getHeight() * 7 / 18) - getHeight() * 7 / 18));
		g2.setColor(new Color(100, (int)(100 * voltageMultiplier), (int)(100 * voltageMultiplier)));
		setFont(tf2);
		g2.drawString("" + Math.round(voltage * 10.0) / 10.0, getWidth() * 5f / 18f, getHeight() * 11 / 18);
		try {
			Thread.sleep(10);
		}
		catch(InterruptedException ex) {
			Logger.getLogger(HealthHudItem.class.getName()).log(Level.SEVERE, null, ex);
		}
		repaint();
	}

	@Override
	public void setValue(Object value) {
		voltage = (Double)value;
	}
}
