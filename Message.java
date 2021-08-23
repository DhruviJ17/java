import java.util.*;

class Message {
  String message;
  String crcString;
  String messageToSend;
  String redundantBits;

  Message(String message) {
    this.message = message;
    this.crcString = "";
    this.messageToSend = message;
    this.redundantBits = "";
  }

  public void setCrc(String genStr) {
    int remainderIntAfterCRC = this.calculateRemainder(genStr, false);
    String remainderStrAfterCRC = Integer.toBinaryString(remainderIntAfterCRC);
    int neededSizeOfCRCstring = genStr.length() - 1;
    int addZeros = neededSizeOfCRCstring - remainderStrAfterCRC.length();
    String crc = "";
    while (addZeros > 0) {
      addZeros--;
      crc += "0";
    }
    crc = crc.concat(remainderStrAfterCRC);
    this.crcString = crc;
    this.setMessageToSend();
  }

  public void setMessageToSend() {
    this.messageToSend += crcString;
  }

  public void setRedundantBits(String g) {
    int noOfRedundantBits = g.length();
    while (noOfRedundantBits >= 0) {
      this.redundantBits += "0";
      noOfRedundantBits--;
    }
  }

  int calculateRemainder(String genStr, boolean forDetection) {
    int lenOfgenStr = genStr.length(); // 4
    int genInt = Integer.parseInt(genStr, 2); // 1101 // 13
    int messageInt = Integer.parseInt(this.message, 2); // 100100 // 36

    int messageIntDividend = messageInt; // 100100 // 36
    if (!forDetection)
      messageIntDividend = messageInt << (lenOfgenStr - 1); // 100100|000 //

    String messageStrDividend = Integer.toBinaryString(messageIntDividend);
    int messageStrDividendSize = messageStrDividend.length();
    int shiftDividendBy = messageStrDividendSize - genStr.length();

    int currentIntDividend = messageIntDividend >> shiftDividendBy;
    int currentIntRemainder = 0;

    while ((messageIntDividend >= genInt) || (shiftDividendBy >= 0)) {
      currentIntRemainder = currentIntDividend ^ genInt;
      int xx = messageIntDividend & (1 << shiftDividendBy) - 1;
      messageIntDividend = (currentIntRemainder << shiftDividendBy) + xx;

      // reset
      messageStrDividend = Integer.toBinaryString(messageIntDividend);
      messageStrDividendSize = messageStrDividend.length();
      shiftDividendBy = messageStrDividendSize - genStr.length();
      currentIntDividend = messageIntDividend >> shiftDividendBy;
    }
    return messageIntDividend;
  }
}

class ThirdParty {
  public static String toggleBit(int bitToToggle, String data) {
    String manipulatedData = "";
    for (int i = 0; i < data.length(); i++) {
      char ch = data.charAt(i);
      if (i == bitToToggle) {
        if (data.charAt(i) == '1')
          ch = '0';
        else
          ch = '1';
      }
      manipulatedData += ch;
    }
    return manipulatedData;
  }

  public static String manipulation(String dataToSend, int noOfBitsToChange) {
    String manipulatedData = dataToSend;
    Random rand = new Random();
    for (int i = 0; i < noOfBitsToChange; i++) {
      int n = rand.nextInt(dataToSend.length());
      manipulatedData = toggleBit(n, manipulatedData);
    }
    return manipulatedData;
  }
}

class GeneratorFunction {
  String str;

  GeneratorFunction(String g) {
    this.str = g;
  }
}

class Sender {
  Message message;
  String dataToSend;
  GeneratorFunction generator;

  Sender(String data, String generatorStr) {
    System.out.println("\nSender");
    this.message = new Message(data);
    this.dataToSend = "";
    this.generator = new GeneratorFunction(generatorStr);
  }

  String setSendingData() {
    this.message.setRedundantBits(generator.str);
    this.message.setCrc(generator.str);
    this.dataToSend = this.message.messageToSend;
    System.out.printf("Data = %s\n", this.message.message);
    System.out.printf("CRC string = %s\n", this.message.crcString);
    System.out.printf("Data to send = %s\n", dataToSend);
    return this.dataToSend;
  }
}

class Receiver {
  String dataReceived;
  String decodedData;
  boolean isDataManipulated;
  GeneratorFunction generator;

  Receiver(String dataReceived, String generatorStr) {
    System.out.println("\nReceiver");
    this.dataReceived = dataReceived;
    this.decodedData = "x";
    this.isDataManipulated = false;
    this.generator = new GeneratorFunction(generatorStr);
  }

  boolean isErrorInReceivedData() {
    Message m = new Message(this.dataReceived);
    int remainder = m.calculateRemainder(generator.str, true);
    if (remainder != 0)
      this.isDataManipulated = true;
    return this.isDataManipulated;
  }

  String decode() {
    this.decodedData = "";
    System.out.printf("data received = %s\n", dataReceived);
    if (!this.isDataManipulated) {
      int shiftBy = generator.str.length() - 1;

      int decodedDataInt = Integer.parseInt(dataReceived, 2) >> shiftBy;
      decodedData = Integer.toBinaryString(decodedDataInt);
      System.out.printf("data after decoding = %s\n\n", decodedData);
    } else
      System.out.println("Data is manipulated!");

    return this.decodedData;
  }
}

public class CRC {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.printf("Input: data = ");
    String data = sc.nextLine();
    System.out.printf("Input: generator = ");
    String generatorStr = sc.nextLine();

    // Communication 1
    Sender s = new Sender(data, generatorStr);
    String dataForReceiver = s.setSendingData();
    Receiver r = new Receiver(dataForReceiver, generatorStr);
    r.isErrorInReceivedData();
    r.decode();

    // Communication 2
    Sender s2 = new Sender(data, generatorStr);
    dataForReceiver = s2.setSendingData();
    // manipulation
    dataForReceiver = ThirdParty.manipulation(dataForReceiver, 3);
    Receiver r2 = new Receiver(dataForReceiver, generatorStr);
    r2.isErrorInReceivedData();

    r2.decode();

    sc.close();
  }
}