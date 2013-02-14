/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.*;
import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.net.URL;
import java.util.logging.*;

import javax.imageio.*;

/**
 * Given an extended JPanel and URL read and create BufferedImages to be
 * displayed from a MJPEG stream
 * <p/>
 * @author shrub34 Copyright 2012
 * Free for reuse, just please give me a credit if it is for a redistributed
 * package
 */
public class MJPEGWidget extends StaticWidget {

	BufferedImage image = null;

	@Override
	public void init() {
		setPreferredSize(new Dimension(640, 360));
	}

	@Override
	public void propertyChanged(Property property) {
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		try {
			image = ImageIO.read(new URL("http://10.31.81.10/axis-cgi/jpg/image.cgi?resolution=640x360&.jpg"));
			System.out.println(image);
		}
		catch(IOException ex) {
			Logger.getLogger(MJPEGWidget.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			Thread.sleep(50);
		}
		catch(InterruptedException ex) {
			Logger.getLogger(MJPEGWidget.class.getName()).log(Level.SEVERE, null, ex);
		}
		g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		repaint();
	}
}