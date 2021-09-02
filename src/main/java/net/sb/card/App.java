package net.sb.card;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
public class App {
	
	public static void main(String[] args) {
		
		test();
		
	}
	
	public static void test() {
		try {
			CardService cardService = new CardService();
			MyKad mykad = cardService.getMyKad();
			mykad.logInfo();
			mykad.displayPhotoInFrame();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
