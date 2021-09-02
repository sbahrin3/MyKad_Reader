package net.sb.card;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.codec.binary.Hex;


/**
 * 
 * @author Shamsul Bahrin
 *
 */

public class CardService {
	
	CardTerminal terminal;
	String hexData;
	MyKad mykad = null;
	
	private int photoProgress = 0;
	
	public CardService() throws Exception {
		
		mykad = new MyKad(this);
		
		TerminalFactory f = TerminalFactory.getDefault();

		CardTerminals terminals = f.terminals();
		List<CardTerminal> cardTerminals = terminals.list();
		if (cardTerminals.size() > 0) {
			terminal = cardTerminals.get(0);
			terminal.waitForCardPresent(3000);
		} else {
			throw new Exception("Card Terminal Error...");
		}
	}
	
	public int getPhotoProgress() {
		return photoProgress;
	}
	
	public String responseData(String[] cmd) throws Exception {
		Card card = terminal.connect("T=0");
		CardChannel cardChannel = card.getBasicChannel();
		ResponseAPDU responseApdu = null;
		for ( String str : cmd ) {
			str = str.replaceAll(" ", "");
			byte[] bytes = HexUtil.toByteArray(str);
			responseApdu = cardChannel.transmit(new CommandAPDU(bytes));
		}
		String res = HexUtil.toHexString(responseApdu.getBytes());
		res = res.replaceAll(" ", "");
		hexData = res;
		byte[] bytes = Hex.decodeHex(res.toCharArray());
		return new String(bytes, "UTF-8");
		
	}
	
	public String responsePhotoHexData(String[] cmd) throws Exception {
		Card card = terminal.connect("T=0");
		CardChannel cardChannel = card.getBasicChannel();
		ResponseAPDU responseApdu = null;
		for ( String str : cmd ) {
			str = str.replaceAll(" ", "");
			byte[] bytes = HexUtil.toByteArray(str);
			responseApdu = cardChannel.transmit(new CommandAPDU(bytes));
		}
		String res = HexUtil.toHexString(responseApdu.getBytes());
		res = res.replaceAll(" ", "");
		res = res.substring(0, res.length() - 4);
		hexData = res;
		return hexData;
	}
	
	public byte[] getPhotoInBytes() throws Exception {
		String hexData = "";
		double progress = 0.0d;
		for (int i = 0; i < 16; i++) {
			
			String hex = responsePhotoHexData(MyKadApdu.getPhotoCommandAt(i));
			hexData += hex;
			
			progress = (double) i/15 * 100;
			photoProgress = (int) progress;
			System.out.println("processing hex data line " + i + ", " + photoProgress);
		}
		System.out.println("ok..");
		return Hex.decodeHex(hexData.toCharArray());
	}
	
	public void savePhotoToFile(String photoFileName) throws Exception {
		byte[] data = getPhotoInBytes();
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		BufferedImage image = ImageIO.read(bis);
		System.out.println("saving image as jpg: ");
		ImageIO.write(image, "jpg", new File(photoFileName));
	}
	
	public void displayPhotoInFrame() throws Exception {
		
		System.out.println("init getPhotoInBytes...");
		byte[] data = getPhotoInBytes();
		
		System.out.println("displaying photo...");
		JFrame frame = new JFrame();
		ImageIcon icon = new ImageIcon(data);
		JLabel label = new JLabel(icon);
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public String getHexData() {
		return hexData;
	}
	
	
	public MyKad getMyKad() throws Exception {
		
		String dataName = responseData(MyKadApdu.CMD_NAME);
		mykad.firstName = dataName.substring(150, 180).trim();
		mykad.lastName = dataName.substring(180, 200).trim();
		mykad.name = mykad.firstName + " " + mykad.lastName;
		
		
		
		String dataBio = responseData(MyKadApdu.CMD_BIODATA);
		
		String hex = getHexData();
		
		System.out.println(dataBio.length());
		System.out.println(hex.length());
		
		String birthDate = hex.substring(124, 132);
		String dateIssued = hex.substring(182, 190);
		
		mykad.birthDate = birthDate;
		mykad.dateIssued = dateIssued;
		
		
		mykad.icno = dataBio.substring(40, 53).trim();
        mykad.gender = dataBio.substring(53, 54).trim();
        mykad.oldnum = dataBio.substring(54, 62).trim();
        mykad.citizenship = dataBio.substring(95, 113).trim();
        mykad.race = dataBio.substring(113, 138).trim();
        mykad.religion = dataBio.substring(138, 149).trim();
        mykad.eastMalaysian = dataBio.substring(149, 150).trim();

		
		String dataAddress = responseData(MyKadApdu.CMD_ADDRESS);
		String postcode = getHexData().substring(180,185);
		
        mykad.address1 = dataAddress.substring(0, 30).trim();
        mykad.address2 = dataAddress.substring(30, 60).trim();
        mykad.address3 = dataAddress.substring(60, 90).trim();
        mykad.city = dataAddress.substring(93, 118).trim();
        mykad.state = dataAddress.substring(118, 148).trim();
        mykad.postcode = postcode;
        
        return mykad;
		
	}
	
	public void retrievePhotoData() {
		try {
			byte[] data = getPhotoInBytes();
			mykad.photoInBytes = data;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
