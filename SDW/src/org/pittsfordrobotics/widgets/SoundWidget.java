/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pittsfordrobotics.widgets;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.*;
import java.io.*;
import java.io.File;
import java.util.logging.*;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioInputStream;
import sun.audio.*;

/**
 *
 * @author Benjamin Pylko <spectare at sourceforge.net>
 */
public class SoundWidget extends StaticWidget {

	@Override
	public void init() {
		new Thread() {
			public void run() {
				try {
					playSound(System.getProperty(System.getProperty("user.home") + "/tf/music/tf_main_theme.wav"));
				}
				catch(UnsupportedAudioFileException | IOException | InterruptedException ex) {
					Logger.getLogger(SoundWidget.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}.start();
	}

	@Override
	public void propertyChanged(Property property) {
	}

	public void playSound(String soundFile) throws UnsupportedAudioFileException, IOException, InterruptedException {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile));
		AudioFormat format = audioInputStream.getFormat();
		long frames = audioInputStream.getFrameLength();
		double durationInSeconds = frames / (double)format.getFrameRate();
		audioInputStream.close();
		InputStream in = new FileInputStream(soundFile);
		AudioStream as = new AudioStream(in);
		durationInSeconds *= 1000;
		AudioPlayer.player.start(as);
		System.out.println(durationInSeconds);
		Thread.sleep((int)(durationInSeconds + 1000));
		AudioPlayer.player.stop(as);
	}
}
