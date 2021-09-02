package net.sb.card;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
public class MyKad {
	
	CardService cardService;
	
	String name = "";
	String firstName = "";
	String lastName = "";
    String icno = "";
    String gender = "";
    String oldnum = "";
    String birthDate = "";
    String birthPlace = "";
    String dateIssued = "";
    String citizenship = "";
    String race = "";
    String religion = "";
    String eastMalaysian = "";
	
	
    String address1 = "";
    String address2 = "";
    String address3 = "";
    String postcode = "";
    String city = "";
    String state = "";
    
    byte[] photoInBytes;
    
    public MyKad(CardService cardService) {
    	this.cardService = cardService;
    }
    
	public String getName() {
		return name;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getIcno() {
		return icno;
	}
	public String getGender() {
		return gender;
	}
	public String getGender2() {
		if ( "L".equals(gender) ) return "LELAKI";
		else if ( "P".equals(gender)) return "PEREMPUAN";
		return "";
	}
	public String getOldnum() {
		return oldnum;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public String getDateIssued() {
		return dateIssued;
	}
	public String getCitizenship() {
		return citizenship;
	}
	public String getRace() {
		return race;
	}
	public String getReligion() {
		return religion;
	}
	public String getEastMalaysian() {
		return eastMalaysian;
	}
	public String getAddress1() {
		return address1;
	}
	public String getAddress2() {
		return address2;
	}
	public String getAddress3() {
		return address3;
	}
	public String getPostcode() {
		return postcode;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
    
    
	public void logInfo() {
        
        System.out.println("Name       : " + name);
		System.out.println("IC No      : " + icno);
		System.out.println("Old No     : " + oldnum);
		System.out.println("Gender     : " + gender);
		System.out.println("Birth      : " + birthDate);
		System.out.println("Issued     : " + dateIssued);
		System.out.println("Citizen    : " + citizenship);
		System.out.println("Race       : " + race);
		System.out.println("Religion   : " + religion);
		System.out.println("East My    : " + eastMalaysian);
        
        System.out.println("Address    : " + address1 + ", " + address2 + ", " + address3);
        System.out.println("             " + postcode + ", " + city + ", " + state);
        
	
	}
	
	public void retrievePhotoData() {
		cardService.retrievePhotoData();
	}
    	
	public void displayPhotoInFrame() throws Exception {
		
		if ( photoInBytes == null )
			cardService.retrievePhotoData();
				
		System.out.println("displaying photo...");
		JFrame frame = new JFrame();
		ImageIcon icon = new ImageIcon(photoInBytes);
		JLabel label = new JLabel(icon);
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public ImageIcon getPhoto() {
		cardService.retrievePhotoData();
		return new ImageIcon(photoInBytes);
	}
    
	public byte[] getPhotoInBytes() {
		cardService.retrievePhotoData();
		return photoInBytes;
	}
}
