/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package witribe.brm;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author PKANawaz
 */
public class CommonFunctions {
    public CommonFunctions() {
        
    }
    public static boolean copyFile(String fileName, String pathName) throws java.
            io.IOException {
          boolean isCopy = false;
          try {
            if (!new java.io.File(pathName).exists()) {
              if (!new java.io.File(pathName).mkdirs()) {
                return false;
              }
            }
            java.io.FileInputStream fin = new java.io.FileInputStream(fileName);
		    String tempPath = fileName.substring(fileName.lastIndexOf(getPathSeparator()) + 1, fileName.length());
		    pathName += getPathSeparator();
		    pathName += tempPath;
		    java.io.FileOutputStream fout = new java.io.FileOutputStream(pathName);
		    java.nio.channels.FileChannel inc = fin.getChannel();
		    java.nio.channels.FileChannel outc = fout.getChannel();
		    java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocateDirect( (int)
		        inc.size());
		    int ret = inc.read(buffer);
		    if (ret == -1) {
		      isCopy = false;
		    }
		    buffer.flip();
		    outc.write(buffer);
		    fout.close();
		    fin.close();
		    isCopy = true;
		  }
		  catch (java.io.IOException e) {
		    isCopy = false;
		    System.out.println("Error Occured : Unable to copy file");
		  }
		  return isCopy;
	}


	public static boolean moveFile(String fileName, String pathName) {
	    try {
	      return (copyFile(fileName, pathName) && deleteFile(fileName));
	    }
	    catch (java.io.IOException ex) {
	      System.out.println("Error Occured: Unable to move file (" + fileName +
	                         ").");
	      return false;
	    }
	}

	public static boolean deleteFile(String fileName) {
	    return new java.io.File(fileName).delete();
	}

        public static String getPathSeparator() {
		return java.io.File.separator;
	}

        public static String readFromFile(String fileName) {
	    String strData = "";
	    if (!new java.io.File(fileName).exists()) {
	      return strData;
	    }
	    try {
	      java.io.FileInputStream fin = new java.io.FileInputStream(fileName);
	      java.nio.channels.FileChannel fChannelReader = fin.getChannel();
	      java.nio.ByteBuffer readBuffer = java.nio.ByteBuffer.allocateDirect( (int)
	          fChannelReader.size());
	      fChannelReader.read(readBuffer);
	      byte[] bytesRead = new byte[ (int) fChannelReader.size()];
	      readBuffer.position(0);
	      readBuffer.get(bytesRead, 0, readBuffer.limit());
	      strData = new String(bytesRead);
	      fChannelReader.close();
	      readBuffer.clear();
	      fin.close();
	      readBuffer = null;
	      fChannelReader = null;
	      fin = null;
	    }
	    catch (java.io.IOException ex) {
	      System.out.println("Error Occured: Unable to read from file (" + fileName +
	                         ")");
	    }
	    return strData;
	  }

        public static boolean fileExists(String fileName) {
		if (!new java.io.File(fileName).exists()) {
			return false;
		}
		return true;
	}

        private static final String[] tensNames = {
    "",
    " ten",
    " twenty",
    " thirty",
    " forty",
    " fifty",
    " sixty",
    " seventy",
    " eighty",
    " ninety"
  };

  private static final String[] numNames = {
    "",
    " one",
    " two",
    " three",
    " four",
    " five",
    " six",
    " seven",
    " eight",
    " nine",
    " ten",
    " eleven",
    " twelve",
    " thirteen",
    " fourteen",
    " fifteen",
    " sixteen",
    " seventeen",
    " eighteen",
    " nineteen"
  };

  private static String convertLessThanOneThousand(int number) {
    String soFar;

    if (number % 100 < 20){
      soFar = numNames[number % 100];
      number /= 100;
    }
    else {
      soFar = numNames[number % 10];
      number /= 10;

      soFar = tensNames[number % 10] + soFar;
      number /= 10;
    }
    if (number == 0) return soFar;
    return numNames[number] + " hundred" + soFar;
  }


  public static String convert(long number) {
    // 0 to 999 999 999 999
    if (number == 0) { return "zero"; }

    String snumber = Long.toString(number);

    // pad with "0"
    String mask = "000000000000";
    DecimalFormat df = new DecimalFormat(mask);
    snumber = df.format(number);

    // XXXnnnnnnnnn
    int billions = Integer.parseInt(snumber.substring(0,3));
    // nnnXXXnnnnnn
    int millions  = Integer.parseInt(snumber.substring(3,6));
    // nnnnnnXXXnnn
    int hundredThousands = Integer.parseInt(snumber.substring(6,9));
    // nnnnnnnnnXXX
    int thousands = Integer.parseInt(snumber.substring(9,12));

    String tradBillions;
    switch (billions) {
    case 0:
      tradBillions = "";
      break;
    case 1 :
      tradBillions = convertLessThanOneThousand(billions)
      + " billion ";
      break;
    default :
      tradBillions = convertLessThanOneThousand(billions)
      + " billion ";
    }
    String result =  tradBillions;

    String tradMillions;
    switch (millions) {
    case 0:
      tradMillions = "";
      break;
    case 1 :
      tradMillions = convertLessThanOneThousand(millions)
      + " million ";
      break;
    default :
      tradMillions = convertLessThanOneThousand(millions)
      + " million ";
    }
    result =  result + tradMillions;

    String tradHundredThousands;
    switch (hundredThousands) {
    case 0:
      tradHundredThousands = "";
      break;
    case 1 :
      tradHundredThousands = "one thousand ";
      break;
    default :
      tradHundredThousands = convertLessThanOneThousand(hundredThousands)
      + " thousand ";
    }
    result =  result + tradHundredThousands;

    String tradThousand;
    tradThousand = convertLessThanOneThousand(thousands);
    result =  result + tradThousand;

    // remove extra spaces!
    return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
  }

public static void main(String[] args) {
    System.out.println("*** " + CommonFunctions.convert(0));
    System.out.println("*** " + CommonFunctions.convert(1));
    System.out.println("*** " + title(CommonFunctions.convert(1106)));
    //System.out.println("*** " + CommonFunctions.convert(2710).toUpperCase(CommonFunctions.convert(2710).charAt(0)) + CommonFunctions.convert(2710).substring(1));

    }
public static String title(String str){
     String result = "";
        Scanner scn = new Scanner(str);
        while (scn.hasNext()) {
            String next = scn.next();
            result += Character.toString(next.charAt(0)).toUpperCase(); //make the first letter uppercase
           for (int i = 1; i < next.length(); i++) {
                result += Character.toString(next.charAt(i)); //do nothing with all the other letters
            }
            result += " "; 
        }
        return result;
	}

}
