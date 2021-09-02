package my.test.card;

import java.util.List;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

import org.apache.commons.codec.binary.Hex;

import net.sb.card.HexUtil;


/**
 * 
 * @author Shamsul Bahrin
 *
 */

public class AppTest {

	// Gemalto PC Twin Reader

	public static void main(String[] args) throws Exception {
		
		//JPN 1.1 - NAME 
        String cmd[] = {
                "00 A4 04 00 0A A0 00 00 00 74 4A 50 4E 00 10",
                "00 C0 00 00 05",
                "C8 32 00 00 05 08 00 00 FA 00",
                "CC 00 00 00 08 01 00 01 00 03 00 FA 00",
                "CC 06 00 00 FA"
        };
        
        //JPN 1.1 - BIODATA
        String cmd2[] = {
                "00 A4 04 00 0A A0 00 00 00 74 4A 50 4E 00 10",
                "00 C0 00 00 05",
                "C8 32 00 00 05 08 00 00 BF 00",
                "CC 00 00 00 08 01 00 01 00 E9 00 BF 00",
                "CC 06 00 00 BF"
        };

		TerminalFactory f = TerminalFactory.getDefault();

		CardTerminals terminals = f.terminals();
		List<CardTerminal> cardTerminals = terminals.list();
		if (cardTerminals.size() > 0) {
			CardTerminal terminal = cardTerminals.get(0);
			terminal.waitForCardPresent(0); //this will throw a CardException
			
			System.out.println(terminal.getName());
			Card card = terminal.connect("T=0");
			System.out.println(card);
			CardChannel cardChannel = card.getBasicChannel();
			
			ResponseAPDU responseApdu = null;
			for ( String str : cmd ) {
				str = str.replaceAll(" ", "");
				byte[] cmd1 = HexUtil.toByteArray(str);
				responseApdu = cardChannel.transmit(new CommandAPDU(cmd1));
			}
			
			
			String res = HexUtil.toHexString(responseApdu.getBytes());
			res = res.replaceAll(" ", "");
			byte[] bytes = Hex.decodeHex(res.toCharArray());
			String myKadData = new String(bytes, "UTF-8");
			System.out.println("Response: " + myKadData);
		}

	}

}
