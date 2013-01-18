/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

import edu.wpi.first.smartdashboard.gui.*;
import edu.wpi.first.smartdashboard.properties.*;
import java.awt.*;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class CrossHairs extends CrossHairsWidget{
    public static final String NAME = "Cross Hairs";

	@Override
	public void init() {
		setPreferredSize(new Dimension(32,32));
	}

	@Override
	public void propertyChanged(Property property) {
		//throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.GREEN);
		g2.fillRect(0,16,14,1);
		g2.fillRect(15, 16, 1, 1);
		g2.fillRect(17,16,15,1);
		g2.fillRect(15,0,1,15);
		g2.fillRect(15,18,1,15);
		setLocation(centerReference.getWidth()/2,centerReference.getHeight()/2);
	}
	
}
