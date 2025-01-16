# ADVANCED-ENCRYPTION-TOOL

**COMPANY NAME**: CODTECH IT SOLUTIONS 

**NAME**:  SHIVANSHU SHUKLA

**INTERN ID**: CT08NFT

**DOMAIN**: CYBER SECURITY AND ETHICAL HACKING

**BATCH DURATION**: January 15th, 2025 to February 15th, 2025

**MENTOR NAME**: Neela Santhosh Kumar

# ENTER DESCRIPTION OF TASK PERFORMED NOT LESS THAN 500 WORDS

Description of Task Performed for Building an AES-256 Encryption and Decryption Tool
The task was to design and implement an encryption tool that could securely encrypt and decrypt files using the Advanced Encryption Standard (AES) with a key size of 256 bits, also known as AES-256. This encryption algorithm is widely regarded for its high security and is used across various industries for data protection. The project was intended to deliver a robust, secure, and user-friendly application that would allow users to easily encrypt their files, store them securely, and decrypt them when necessary, using a graphical user interface (GUI).

Objective
The primary objective of this project was to build a file encryption and decryption tool that uses the AES-256 algorithm for encrypting and decrypting files, ensuring that the tool is both secure and easy to use for a wide range of users. The application should allow users to encrypt any type of file and decrypt them back into their original form with ease, providing an interface that simplifies the interaction for non-technical users.

Requirements Analysis and Design
Security Requirements: The tool needed to employ AES-256, which is a symmetric key encryption algorithm. This means the same key is used for both encryption and decryption. AES-256 is widely known for its resistance to brute force attacks, making it an ideal choice for this project.

User Interface (UI): The application needed to feature a user-friendly interface that would guide users in encrypting and decrypting files easily. The interface had to include options for selecting files, specifying the key (or loading it from a secure source), and providing feedback on the success or failure of the encryption or decryption process.

Cross-Platform Compatibility: The tool should be functional across multiple platforms, such as Windows, macOS, and Linux, to accommodate a wide user base.

Key Management: As encryption requires a secret key, the tool needed a secure mechanism for key generation, storage, and retrieval. This would include options to generate random secure keys and allow the user to store or retrieve keys in a secure manner, perhaps using a password or hardware key management solution.

Implementation Steps
AES-256 Algorithm Integration:

The core of the tool is the implementation of the AES-256 encryption and decryption algorithm. For this, Python was chosen as the programming language due to its support for cryptography libraries such as PyCryptodome, which provides an easy interface for AES encryption.
The AES algorithm was implemented using AES.MODE_CBC (Cipher Block Chaining) to ensure that the encryption process is secure. CBC mode requires an Initialization Vector (IV) that is random for each encryption operation to prevent patterns from being observed in the ciphertext.
To ensure that the file size is a multiple of the block size, padding was added to the data being encrypted. After encryption, the resulting ciphertext is saved as a file that can only be decrypted with the correct key.
File Handling:

The application needed to handle any type of file, so the tool first reads the file in binary mode to ensure it is properly processed regardless of its format (e.g., text, image, or video files).
The encryption or decryption was done in chunks of data to accommodate large files without consuming excessive memory.
Graphical User Interface (GUI):

For the user interface, the Python library Tkinter was selected. It offers a simple way to create GUI applications without much overhead.
The main window contains buttons for "Encrypt", "Decrypt", "Browse Files", and "Generate Key". Users can browse their file system to select files for encryption or decryption, and they can provide the password for key generation or key entry.
There is a status bar to show feedback on the progress of the encryption or decryption process, as well as error messages in case something goes wrong (such as entering an incorrect key).
Key Generation and Management:

The tool allows users to either input their encryption key manually or generate a strong, random 256-bit key using the os.urandom function for cryptographic purposes.
The key is securely handled in memory and is not saved on disk. If users opt to store the key for future use, the tool provides a mechanism to securely store it in a password-protected file using AES encryption.
Testing and Debugging:

Extensive testing was carried out to verify that both encryption and decryption functions were working as expected. Sample files were encrypted, and the encrypted files were then decrypted using the same key to ensure that the original file was recovered correctly.
The application was tested for edge cases such as handling empty files, corrupted files, and invalid keys.
Security Features:

A password-based key derivation function (PBKDF2) was used to derive a secure key from a user-entered password. This adds an extra layer of security, as passwords are typically easier to remember than random strings of characters.
To further enhance security, the tool used a salt value when deriving keys from passwords to ensure that even if two users use the same password, the derived keys are different.
Final Deliverables
Source Code: The final product included the source code for the AES-256 encryption and decryption tool, written in Python, which was well-documented for easy understanding and modification.
Executable Files: The tool was packaged into executables for Windows, macOS, and Linux, allowing users to run the program without needing to install Python or additional libraries.
User Manual: A comprehensive user manual was created to guide users through the installation, key generation, file encryption, and decryption processes.
Testing Reports: Detailed testing reports were provided, documenting various test cases, their results, and any issues that were resolved during the development process.
Conclusion
In conclusion, the task of building an AES-256 file encryption and decryption tool was successfully completed. The final product is secure, efficient, and easy to use, providing users with a reliable way to protect their sensitive data. The encryption tool meets all requirements, including strong security with AES-256, a user-friendly interface, and cross-platform compatibility. The project also demonstrates the importance of secure key management, error handling, and testing in building a robust encryption solution.

**OUTPUT**

