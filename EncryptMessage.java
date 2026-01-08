import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class EncryptMessage {
    public static void main(String[] args) {

        // Welcome Print
        System.out.println("----------------------------------------");
        System.out.println("           Message Encrypter");
        System.out.println("----------------------------------------");


        // Wait for 2 Seconds
        try {
            Thread.sleep(2000); // wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Create Scanner
        Scanner Scanner1 = new Scanner(System.in);


        // Choose Subfolder inside the Project to look for existing files
        File ordner = new File(System.getProperty("user.dir") + "/Files");


        // List the files inside that folder
        File[] listFiles = ordner.listFiles();


        // Print Available Files, add position of each file in front for visibility
        int i = 1;
        System.out.println("Your existing files are: ");
        for (File file : listFiles) {
            System.out.print(i + ") ");
            System.out.println(file.getName());
            i++;
        }


        // Choose Public Key File
        System.out.print("Enter the name of the public key you want to use for encryption: ");
        String publicKeyFile = Scanner1.nextLine();


        // Choose File to be encrypted
        System.out.print("Enter the name of the file you want to encrypt: ");
        String contentMessage = Scanner1.nextLine();


        // Choose new File Name for encrypted message
        System.out.print("What name should the encrypted file receive: ");
        String encryptedFilename = Scanner1.nextLine();


        // Choose Public Key File in File Folder based on user input
        Path filePathPubKey = Paths.get(ordner.getPath(), publicKeyFile);


        // Need to declare this outside the try/catch so I could use it afterwards
        String contentPubKey = null;


        // Read Content of Public Key File // Throw Error if File doesnt exist
        try {
            contentPubKey = Files.readString(filePathPubKey);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Extract numbers from the string
        String[] lines = contentPubKey.split("\n");


        //Define Lines of File as Variables
        String nLine = lines[0];
        String dLine = lines[1];


        // Get the numbers between { and } for both Lines
        String nValue = nLine.substring(nLine.indexOf("{") + 1, nLine.indexOf("}"));
        String eValue = dLine.substring(dLine.indexOf("{") + 1, dLine.indexOf("}"));


        // Convert Numbers (String) to BigIntegers
        BigInteger bigN = new BigInteger(nValue);
        BigInteger bigE = new BigInteger(eValue);


        // Choose Message File in File Folder based on user input
        Path filePathMessage = Paths.get(ordner.getPath(), contentMessage);


        // Read Conent of Message File, throw error if something doesnt work
        try {
            contentMessage = Files.readString(filePathMessage);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }


        // Get Message Length, Important for Convertion
        int messageLength = contentMessage.length();


        // Loop that converts each char, on by one into binary and glues them together as one String via StringBuilder
        StringBuilder binaryString = new StringBuilder();
        for (int j = 0; j < messageLength; j++) {
            //Convert Char into its Decimal Value
            int messageAsciiValue = (int) contentMessage.charAt(j);

            //Convert Decimal Char into 8 Bit Binary
            String binaryConvStr = Integer.toBinaryString(messageAsciiValue);

            //Pad with leading zeros to make it 8 bits
            String paddedBinary = String.format("%8s", binaryConvStr).replace(' ', '0');

            //Add to string
            binaryString.append(paddedBinary);
        }


        // Convert Binary String to Decimal Value
        BigInteger messageDecimal = new BigInteger(binaryString.toString(), 2);


        // Encrypt Message to Ciphertext via RSA Encryption Formula
        BigInteger ciphertext = messageDecimal.modPow(bigE, bigN);


        // Convert BigInteger into String so I can write it into the File
        String strCiphertext = ciphertext.toString();


        // Create Encrypted Message File and Save it at user.dir/files, throw error if something doesnt work
        try (FileWriter writerMessage = new FileWriter(new File(ordner, encryptedFilename))) {
            writerMessage.write(strCiphertext);
        }
        catch (IOException ex) {
            System.out.println("Error writing public key: " + ex.getMessage());
        }

        // Info Print
        System.out.println("Yay! You encrypted your message! Ciphertext has been saved at " + ordner + "\\" + encryptedFilename);

        // Wait again for 2 Seconds
        try {
            Thread.sleep(2000); // wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}