import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.CipherOutputStream;
import javax.crypto.CipherInputStream;
import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;

public class AESFileEncryptionTool {

    // Method to generate a random AES-256 key
    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // AES-256
        return keyGen.generateKey();
    }

    // Method to encrypt the file
    public static void encryptFile(File inputFile, File outputFile, SecretKey key) throws Exception {
        // Create an initialization vector (IV)
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Set up AES Cipher in CBC mode
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        // Write the IV and the encrypted file
        try (FileInputStream fileInputStream = new FileInputStream(inputFile);
             FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
             CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher)) {

            // Write the IV at the beginning of the encrypted file
            fileOutputStream.write(iv);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    // Method to decrypt the file
    public static void decryptFile(File inputFile, File outputFile, SecretKey key) throws Exception {
        // Read the IV from the beginning of the file
        byte[] iv = new byte[16];
        try (FileInputStream fileInputStream = new FileInputStream(inputFile)) {
            fileInputStream.read(iv);
        }

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Set up AES Cipher in CBC mode
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        // Decrypt the file and write the result
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

    public static void main(String[] args) {
        try {
            // Generate AES-256 key
            SecretKey key = generateAESKey();

            // Ask user for the input file and output file paths
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the path of the file to encrypt: ");
            String inputFilePath = reader.readLine();
            File inputFile = new File(inputFilePath);

            System.out.print("Enter the output file path for the encrypted file: ");
            String encryptedFilePath = reader.readLine();
            File encryptedFile = new File(encryptedFilePath);

            // Encrypt the file
            encryptFile(inputFile, encryptedFile, key);
            System.out.println("File encrypted successfully.");

            System.out.print("Enter the output file path for the decrypted file: ");
            String decryptedFilePath = reader.readLine();
            File decryptedFile = new File(decryptedFilePath);

            // Decrypt the file
            decryptFile(encryptedFile, decryptedFile, key);
            System.out.println("File decrypted successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
