import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DecryptMessage {
    public static void main(String[] args) {


        // Welcome Print
        System.out.println("----------------------------------------");
        System.out.println("           Message Decrypter");
        System.out.println("----------------------------------------");


        // Wait again for 2 Seconds
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


        // Choose Private Key File
        System.out.print("Enter the name of the private key you want to use for decryption: ");
        String privateKeyFile = Scanner1.nextLine();


        // Choose File to be decrypted
        System.out.print("Enter the name of the file you want to decrypt: ");
        String encryptedMessage = Scanner1.nextLine();


        // Choose new File Name for decrypted message
        System.out.print("What name should the decrypted file receive: ");
        String decryptedFilename = Scanner1.nextLine();


        // Choose Private Key File in File Folder based on user input
        Path filePathPrivKey = Paths.get(ordner.getPath(), privateKeyFile);


        // Need to declare this outside the try/catch so I could use it afterwards
        String contentPrivKey = null;


        // Read Content of Private Key File // Throw Error if File doesnt exist
        try {
            contentPrivKey = Files.readString(filePathPrivKey);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }


        // Extract numbers from the string
        String[] lines = contentPrivKey.split("\n");


        //Define Lines of File as Variables
        String nLine = lines[0];
        String dLine = lines[1];


        // Get the numbers between { and } for both Lines
        String nValue = nLine.substring(nLine.indexOf("{") + 1, nLine.indexOf("}"));
        String dValue = dLine.substring(dLine.indexOf("{") + 1, dLine.indexOf("}"));


        // Convert Numbers (String) to BigIntegers
        BigInteger bigN = new BigInteger(nValue);
        BigInteger bigD = new BigInteger(dValue);


        // Choose Encrypted Message File in File Folder based on user input
        Path filePathMessage = Paths.get(ordner.getPath(), encryptedMessage);


        // Read Content of Encrypted Message File
        try {
            encryptedMessage = Files.readString(filePathMessage);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }


        // Convert String into BigInteger
        BigInteger ciphertextBig = new BigInteger(encryptedMessage);


        //Decrypt Ciphertext into Decimal via RSA Decryption Formula
        BigInteger decryptedMessageDecimal = ciphertextBig.modPow(bigD, bigN);


        //Convert Decimal into Binary
        String decryptedMessageBinary = decryptedMessageDecimal.toString(2);


        // Pad length to multiple of 8 by adding zeros to the left if needed
        // This is important so the split in the next step has the right order
        int remainder = decryptedMessageBinary.length() % 8;
        if (remainder != 0) {
            int padding = 8 - remainder;
            decryptedMessageBinary = "0".repeat(padding) + decryptedMessageBinary;
        }


        // Split into 8-bit groups (left to right)
        List<String> groups = new ArrayList<>();
        for (int l = 0; l < decryptedMessageBinary.length(); l += 8) {
            groups.add(decryptedMessageBinary.substring(l, l + 8));
        }


        // Convert each 8-bit group to its ASCII character
        StringBuilder asciiString = new StringBuilder();
        for (String group : groups) {
            int decimalValue = Integer.parseInt(group, 2); // binary -> decimal
            asciiString.append((char) decimalValue);       // decimal -> ASCII char
        }


        // Create String containing decrypted Message
        String decryptedMessage = asciiString.toString();


        // Print decrypted message
        System.out.println("Your decrypted message says: " + decryptedMessage);


        // Create File containing decrypted Message using Filename based on user input
        try (FileWriter writerMessage = new FileWriter(new File(ordner, decryptedFilename))) {
            writerMessage.write(decryptedMessage);
        }
        catch (IOException ex) {
            System.out.println("Error writing public key: " + ex.getMessage());
        }


        // Info Print
        System.out.println("Yay! You decrypted your message! Message has been saved at " + ordner + "\\" + decryptedFilename);


        // Wait again for 2 Seconds
        try {
            Thread.sleep(2000); // wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}