import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.io.FileWriter;

public class KeyGen {
    public static void main(String[] args) {

        // Welcome Print
        System.out.println("----------------------------------------");
        System.out.println("        RSA Key Pair Generator");
        System.out.println("----------------------------------------");


        // Create Scanner
        Scanner Scanner1 = new Scanner(System.in);


        // Enter Prime Number p
        System.out.print("Enter your p: ");
        String pStr = Scanner1.next(); // read as a string
        BigInteger pBig = new BigInteger(pStr); // convert directly into bigint


        // Enter Prime Number q
        System.out.print("Enter your q: ");
        String qStr  = Scanner1.next();
        BigInteger qBig = new BigInteger(qStr);


        // Multiplay p x q to receive n
        BigInteger nBig = pBig.multiply(qBig);
        System.out.println("Your n is: " + nBig);


        // Calculate Euler's Totient of n
        BigInteger nBigEuler = (pBig.subtract(BigInteger.ONE)).multiply(qBig.subtract(BigInteger.ONE));
        System.out.println("Your Euler Totient (φ) of n is: " + nBigEuler);


        // Enter exponent e
        System.out.print("Enter your e: ");
        String eStr = Scanner1.next();
        BigInteger eBig = new BigInteger(eStr);


        //Calculate d based on e and euler n
        BigInteger dBig = eBig.modInverse(nBigEuler);
        System.out.println("Your d is: " + dBig);


        //Create New Scanner - It somehow doesnt work otherwise
        Scanner Scanner2 = new Scanner(System.in);


        // Specify Folder Location for Files
        File ordner = new File(System.getProperty("user.dir") + "/Files");


        // Choose Name for Public Key File
        System.out.print("Enter filename for public key: ");
        String filenamePub = Scanner2.nextLine();


        // Choose Name for Private Key File
        System.out.print("Enter filename for private key: ");
        String filenamePriv = Scanner2.nextLine();


        // Create Public Key File, catch error in case it doesnt work
        try (FileWriter writerPub = new FileWriter(new File(ordner, filenamePub))) {
            writerPub.write("n = {" + nBig + "}\n");
            writerPub.write("e = {" + eBig + "}");
        } catch (IOException ex) {
            System.out.println("Error writing public key: " + ex.getMessage());
        }


        // Create Private Key File, catch error in case it doesnt work
        try (FileWriter writerPriv = new FileWriter(new File(ordner, filenamePriv))) {
            writerPriv.write("n = {" + nBig + "}\n");
            writerPriv.write("d = {" + dBig + "}");
        } catch (IOException ex) {
            System.out.println("Error writing private key: " + ex.getMessage());
        }


        // Info Print
        System.out.println("Public key has been created at " + ordner + "\\" + filenamePub);
        System.out.println("Private key has been created at " + ordner + "\\" + filenamePriv);
        System.out.println("----------------------------------------");


        // Wait for 2 Seconds
        try {
            Thread.sleep(2000); // wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}