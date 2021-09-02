package net.sb.card;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * 
 * @author Shamsul Bahrin
 *
 */

public class AppPhoto {

	public static void main(String[] args) {

		try {
			CardService cardService = new CardService();

			System.out.println("init getPhotoInBytes...");
			byte[] data = cardService.getPhotoInBytes();
			
			System.out.println("displaying photo...");
			JFrame frame = new JFrame();
			ImageIcon icon = new ImageIcon(data);
			JLabel label = new JLabel(icon);
			frame.add(label);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
