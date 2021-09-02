package net.sb.card;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
public class MyKadApdu {
	
	//JPN 1.1 - NAME 
    public static String CMD_NAME[] = {
            "00 A4 04 00 0A A0 00 00 00 74 4A 50 4E 00 10",
            "00 C0 00 00 05",
            "C8 32 00 00 05 08 00 00 FA 00",
            "CC 00 00 00 08 01 00 01 00 03 00 FA 00",
            "CC 06 00 00 FA"
    };
    
    //JPN 1.1 - BIODATA
    public static String CMD_BIODATA[] = {
            "00 A4 04 00 0A A0 00 00 00 74 4A 50 4E 00 10",
            "00 C0 00 00 05",
            "C8 32 00 00 05 08 00 00 BF 00",
            "CC 00 00 00 08 01 00 01 00 E9 00 BF 00",
            "CC 06 00 00 BF"
    };
    
    public static String CMD_BIODATA2[] = {
            "00 A4 04 00 0A A0 00 00 00 74 4A 50 4E 00 10",
            "00 C0 00 00 05",
            "C8 32 00 00 05 08 00 00 BF 00",
            "CC 00 00 00 08 01 00 01 00 E9 00 BF 00",
            "CC 06 00 00 BF"
    };
    
    //- address
    public static String CMD_ADDRESS[] = {
            "00 A4 04 00 0A A0 00 00 00 74 4A 50 4E 00 10",
            "00 C0 00 00 05",
            "C8 32 00 00 05 08 00 00 94 00",
            "CC 00 00 00 08 04 00 01 00 03 00 94 00",
            "CC 06 00 00 94"
    };
   
  
    public static String[] getPhotoCommandAt(int index) {

    	//0 - 15
        String[] lines = {
                "CC 00 00 00 08 02 00 01 00 03 00 FF 00", //FF
                "CC 00 00 00 08 02 00 01 00 02 01 FF 00",
                "CC 00 00 00 08 02 00 01 00 01 02 FF 00",
                "CC 00 00 00 08 02 00 01 00 00 03 FF 00",
                "CC 00 00 00 08 02 00 01 00 FF 03 FF 00",
                "CC 00 00 00 08 02 00 01 00 FE 04 FF 00",
                "CC 00 00 00 08 02 00 01 00 FD 05 FF 00",
                "CC 00 00 00 08 02 00 01 00 FC 06 FF 00",
                "CC 00 00 00 08 02 00 01 00 FB 07 FF 00",
                "CC 00 00 00 08 02 00 01 00 FA 08 FF 00",
                "CC 00 00 00 08 02 00 01 00 F9 09 FF 00",
                "CC 00 00 00 08 02 00 01 00 F8 0A FF 00",
                "CC 00 00 00 08 02 00 01 00 F7 0B FF 00",
                "CC 00 00 00 08 02 00 01 00 F6 0C FF 00",
                "CC 00 00 00 08 02 00 01 00 F5 0D FF 00",
                "CC 00 00 00 08 02 00 01 00 F4 0E AC 00" //AC
        };
        
        String dataLine = lines[index];
        String dataLength = index < lines.length - 1 ? "FF" : "AC";
        String[] cmd = {
                "00 A4 04 00 0A A0 00 00 00 74 4A 50 4E 00 10",
                "00 C0 00 00 05",
                "C8 32 00 00 05 08 00 00 " + dataLength + " 00",
                dataLine,
                "CC 06 00 00 " + dataLength

        };
    	return cmd;
    }
    


}
