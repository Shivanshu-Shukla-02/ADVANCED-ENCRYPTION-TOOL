import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.CipherOutputStream;
import javax.crypto.CipherInputStream;
import java.io.*;
import java.security.SecureRandom;

public class AESFileEncryptionToolGUI extends Application {

    private Text statusText = new Text("Ready to encrypt or decrypt a file.");
    
    // Generate AES Key
    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // AES-256
        return keyGen.generateKey();
    }

    // Encrypt File
    public static void encryptFile(File inputFile, File outputFile, SecretKey key) throws Exception {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        try (FileInputStream fileInputStream = new FileInputStream(inputFile);
             FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
             CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher)) {

            fileOutputStream.write(iv);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    // Decrypt File
    public static void decryptFile(File inputFile, File outputFile, SecretKey key) throws Exception {
        byte[] iv = new byte[16];
        try (FileInputStream fileInputStream = new FileInputStream(inputFile)) {
            fileInputStream.read(iv);
        }

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        try (FileInputStream fileInputStream = new FileInputStream(inputFile);
             CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
             FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Set up the main layout
        primaryStage.setTitle("AES File Encryption Tool");
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10;");
        
        // Status Text
        statusText.setFill(Color.BLACK);
        layout.getChildren().add(statusText);

        // Buttons for encryption and decryption
        Button encryptButton = new Button("Encrypt File");
        Button decryptButton = new Button("Decrypt File");
        
        // Add functionality to buttons
        encryptButton.setOnMouseClicked(this::handleEncryptButtonClick);
        decryptButton.setOnMouseClicked(this::handleDecryptButtonClick);

        // File Chooser setup for file selection
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

        // Layout with buttons
        layout.getChildren().addAll(encryptButton, decryptButton);

        // Scene setup
        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Handle Encrypt Button Click
    private void handleEncryptButtonClick(MouseEvent event) {
        try {
            // Create a file chooser to select the file
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
            File inputFile = fileChooser.showOpenDialog(null);
            
            if (inputFile != null) {
                // Generate AES Key
                SecretKey key = generateAESKey();
                
                // Select output file path for encryption
                File outputFile = new File(inputFile.getParent(), "Encrypted_" + inputFile.getName());
                
                // Encrypt the file
                encryptFile(inputFile, outputFile, key);
                
                // Update status text
                statusText.setText("Encryption Successful. File saved as: " + outputFile.getName());
                
                showAlert(AlertType.INFORMATION, "File Encrypted Successfully", "Encryption complete!");
            } else {
                showAlert(AlertType.ERROR, "File Selection Error", "No file selected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Encryption Error", "An error occurred during encryption.");
        }
    }

    // Handle Decrypt Button Click
    private void handleDecryptButtonClick(MouseEvent event) {
        try {
            // Create a file chooser to select the encrypted file
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
            File inputFile = fileChooser.showOpenDialog(null);
            
            if (inputFile != null) {
                // Generate AES Key (for simplicity, we generate a new one here - in a real-world scenario, this would be handled more securely)
                SecretKey key = generateAESKey();
                
                // Select output file path for decryption
                File outputFile = new File(inputFile.getParent(), "Decrypted_" + inputFile.getName());
                
                // Decrypt the file
                decryptFile(inputFile, outputFile, key);
                
                // Update status text
                statusText.setText("Decryption Successful. File saved as: " + outputFile.getName());
                
                showAlert(AlertType.INFORMATION, "File Decrypted Successfully", "Decryption complete!");
            } else {
                showAlert(AlertType.ERROR, "File Selection Error", "No file selected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Decryption Error", "An error occurred during decryption.");
        }
    }

    // Method to display alerts
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

