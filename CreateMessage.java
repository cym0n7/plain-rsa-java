import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class CreateMessage {
    public static void main(String[] args) {

        // Welcome Print
        System.out.println("----------------------------------------");
        System.out.println("            Message Creator");
        System.out.println("----------------------------------------");


        // Create Scanner
        Scanner Scanner1 = new Scanner(System.in);


        // Enter message
        System.out.print("Type your message: ");
        String message = Scanner1.nextLine();


        // Enter Filename
        System.out.print("Enter filename for your message: ");
        String filenameMessage = Scanner1.nextLine();


        // Define Folder that the file should be saved to
        File ordner = new File(System.getProperty("user.dir") + "/Files");


        // Create File containg message and Save it at user.dir/files, catch error if somethings doesnt work
        try (FileWriter writerMessage = new FileWriter(new File(ordner, filenameMessage))) {
            writerMessage.write(message);
            System.out.println("Yay! You successfully created your Message at " + ordner + "\\" + filenameMessage);
        }
        catch (IOException ex) {
            System.out.println("Error writing public key: " + ex.getMessage());
        }


        // Wait for 2 Seconds
        try {
            Thread.sleep(2000); // wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}