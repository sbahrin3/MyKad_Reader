package net.sb.card;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.json.simple.JSONObject;

import my.test.card.SendData;


/**
 * 
 * @author Shamsul Bahrin
 *
 */
public class MyKadReader extends JFrame {
	
	JButton buttonRead = new JButton("Read MyKad");
	JButton buttonSend = new JButton("Send Data to Server");
	JLabel photoHolder;
	JLabel appTitle = new JLabel("MMDIS MYKAD READER");
	JLabel 	nameLabel = new JLabel("NAME"), 
			newICLabel = new JLabel("IC NO"), 
			oldICLabel = new JLabel("OLD IC"), 
			genderLabel = new JLabel("GENDER"), 
			birthLabel = new JLabel("BIRTH DATE"), 
			citizenLabel = new JLabel("CITIZEN"), 
			raceLabel = new JLabel("RACE"), 
			religionLabel = new JLabel("RELIGION"),
			addressLabel1 = new JLabel("ADDRESS 1"),
			addressLabel2 = new JLabel("ADDRESS 2");
	
	
	JProgressBar progress = new JProgressBar();
	CardService cardService;
	MyKad mykad;
	
	int fwidth = 800, fheight = 450;
	
	public MyKadReader() {
		
		buttonRead.setBounds(10, 10, 152, 50);
		buttonRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emptyFields();
				if ( !read() ) {
					System.out.println("ERROR: Can't read Card...");
				}
			}
			
		});
		
		buttonSend.setBounds(10, 350, 152, 50);
		buttonSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendDataToServer();
			}
			
		});
		
		photoHolder = new JLabel();
		photoHolder.setBounds(10, 100, 152, 200);
		photoHolder.setBorder(BorderFactory.createLineBorder(Color.black));
	
		add(photoHolder);
		add(buttonRead);
		add(buttonSend);
		
		appTitle.setBounds(540, 0, 400, 40);
		appTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD,  20));
		
		nameLabel.setBounds(250, 90, 500, 40);
		newICLabel.setBounds(250, 120, 500, 40);
		oldICLabel.setBounds(250, 150, 500, 40);
		genderLabel.setBounds(250, 180, 500, 40);
		citizenLabel.setBounds(250, 210, 500, 40);
		raceLabel.setBounds(250, 240, 500, 40);
		religionLabel.setBounds(250, 270, 500, 40);
		addressLabel1.setBounds(250, 300, 500, 40);
		addressLabel2.setBounds(250, 330, 500, 40);
		
		add(appTitle);
		
		add(nameLabel);
		add(newICLabel);
		add(oldICLabel);
		add(genderLabel);
		add(citizenLabel);
		add(raceLabel);
		add(religionLabel);
		add(addressLabel1);
		add(addressLabel2);
		
		
		setBounds(200, 100, fwidth, fheight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		this.getContentPane().setBackground(Color.WHITE);
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MyKadReader();
	}
	
	public boolean read() {
		
		boolean readOK = true;

		progress.setUI(new ProgressCircleUI());
		progress.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		progress.setStringPainted(true);
		progress.setFont(progress.getFont().deriveFont(24f));
		progress.setBackground(Color.WHITE);
		progress.setForeground(Color.ORANGE);
		progress.setBounds(1,1,100,100);
		progress.setIndeterminate(true);
		progress.setValue(0);
		photoHolder.add(progress);
		
		
		try {
			cardService = new CardService();
			Thread progressCheck = new Thread(new PhotoProgressCheck());
			progressCheck.start();
			
			Thread readCard = new Thread(new ReadCard());
			readCard.start();
			
		} catch (Exception e1) {
			readOK = false;
			System.out.println("INITIALIZING CARD SERVICE FAILED...");
			e1.printStackTrace();
		}
		
		
		
		return readOK;

	}
	
	private void updateFields() {
		nameLabel.setText(mykad.getName());
		newICLabel.setText(mykad.getIcno());
		oldICLabel.setText(mykad.getOldnum());
		genderLabel.setText(mykad.getGender2());
		citizenLabel.setText(mykad.getCitizenship());
		raceLabel.setText(mykad.getRace());
		religionLabel.setText(mykad.getReligion());
		addressLabel1.setText(mykad.getAddress1() + ", " + mykad.getAddress2() + ", " + mykad.getAddress3());
		addressLabel2.setText(mykad.getPostcode() + ", " + mykad.getCity() + ", " + mykad.getState());
	}
	
	private void refresh() {
		//refresh
		setPreferredSize(new Dimension(fwidth, fheight));
		pack();
	}
	
	class ReadCard implements Runnable {

		public void run() {
			
			buttonRead.setText("Reading...");
			buttonRead.setEnabled(false);
			
			try {
				
				mykad = cardService.getMyKad();
				mykad.logInfo();
				
				updateFields();
				
				ImageIcon icon = mykad.getPhoto();
				photoHolder.setIcon(icon);
				
			} catch (Exception e) {
				System.out.println("CAN'T READ CARD...");
				e.printStackTrace();
			}
			
			photoHolder.remove(progress);
			
			buttonRead.setText("Read MyKad");
			buttonRead.setEnabled(true);
			
			refresh();

		}
		
	}
	
	class PhotoProgressCheck implements Runnable {

		public void run() {
			while ( cardService.getPhotoProgress() < 100 ) {
				progress.setValue(cardService.getPhotoProgress());
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			progress.setValue(100);
		}
		
	}
	
	
	private void emptyFields() {
		nameLabel.setText("NAME");
		newICLabel.setText("IC NO");
		oldICLabel.setText("OLD IC");
		genderLabel.setText("GENDER");
		birthLabel.setText("BIRTH DATE");
		citizenLabel.setText("CITIZEN");
		raceLabel.setText("RACE");
		religionLabel.setText("RELIGION");
		addressLabel1.setText("ADDRESS 1");
		addressLabel2.setText("ADDRESS 2");
		
		photoHolder.setIcon(null);
		
		refresh();
	}
	
	private void sendDataToServer() {
		System.out.println("Send Data");
		
		String url = "http://localhost:8080/my.web/mykad/readmykad";
		System.out.println("api url: " + url);
		
		JSONObject obj = new JSONObject();       
		obj.put("name", mykad.getName());  
		obj.put("icno", mykad.getIcno());
		obj.put("gender", mykad.getGender());
		
		try {
			SendData.post(url, obj.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("done");
	}

}
