import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Check if "Files" Folder exists in User Directory. This is important for Storing Files later on

        // Get current working directory
        String ordner = System.getProperty("user.dir");

        // Build the path to the folder
        File folder = new File(ordner + "/Files");

        // Check if it exists and if not, create it
        if (folder.exists() && folder.isDirectory()) {
        } else {

            // Print that Folder doesnt exist
            System.out.println("Files folder does not exist!");

            // Create folder in user.dir
            new File(System.getProperty("user.dir") + File.separator + "Files").mkdirs();

            // Info Print
            System.out.println("Folder \"Files\" has been created at " + ordner + "\\Files");
            System.out.println("All Files will be saved there.");

            // Wait for 2 seconds to output isnt rushed
            try {
                Thread.sleep(2000); // wait for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        // Create Scanner
        Scanner Scanner1 = new Scanner(System.in);

        // Print "RSA" ASCII Art
        System.out.println("   __________  _________   _____    ");
        System.out.println("   \\______   \\/   _____/  /  _  \\  ");
        System.out.println("    |       _/\\_____  \\  /  /_\\  \\  ");
        System.out.println("    |    |   \\/        \\/    |    \\ ");
        System.out.println("    |____|_  /_______  /\\____|__  /");
        System.out.println("           \\/        \\/         \\/  ");

        // Wait a second so Art is displayed in Terminal
        try {
            Thread.sleep(1000); // wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Welcome Print
        System.out.println("----------------------------------------");
        System.out.println("     Welcome to the RSA Encryptor!");

        // Wait again for 2 Seconds
        try {
            Thread.sleep(2000); // wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create Boolean to enable Main Menu Loop
        boolean running = true;

        //Open Main Menu Loop
        while (running) {
            // Open Main Menu Options
            System.out.println("----------------------------------------");
            System.out.println("What do you want to do?");
            System.out.println("1) Generate RSA key pair");
            System.out.println("2) Create a message ");
            System.out.println("3) Encrypt a message ");
            System.out.println("4) Decrypt a message");
            System.out.println("----------------------------------------");

            // Choose Menu Number / What to do
            System.out.print("Please enter the corresponding menu number: ");
            int choose = Scanner1.nextInt();

            // If-clause to open the function the user chooses / throw arrow for invalid input
            if (choose == 1) {
                KeyGen.main(new String[]{});
            } else if (choose == 2) {
                CreateMessage.main(new String[]{});
            } else if (choose == 3) {
                EncryptMessage.main(new String[]{});
            } else if (choose == 4) {
                DecryptMessage.main(new String[]{});
            } else {
                System.out.println("Invalid option. Please try again.");
            }



        }
    }
}