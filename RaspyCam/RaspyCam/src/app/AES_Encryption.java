package app;

import java.util.Arrays;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AES_Encryption {

    private String KeyPassword = "";

    public void SetPassword(String Password) 
    {
        KeyPassword = Password;
    }
    public String GetPassword() 
    {
        return KeyPassword;
    }

    private Key aesKey;
    private Cipher cipher;

    public void InitAES() 
    {
        try {
            aesKey = new SecretKeySpec(Arrays.copyOf(KeyPassword.getBytes(), 16 ), "AES"); // why not 24 or 32?
            cipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AES_Encryption()
    {

    }
    public AES_Encryption(String[] Settings)
    {
        KeyPassword = Settings[0];
        InitAES();
    }

    public byte[] EncryptAES(byte[] Message) 
    {

        try {

            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            return cipher.doFinal(Message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    public byte[] DecryptAES(byte[] Encrypted_Message) 
    {

        try {

            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            return cipher.doFinal(Encrypted_Message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

}


//Based On https://stackoverflow.com/questions/23561104/how-to-encrypt-and-decrypt-string-with-my-passphrase-in-java-pc-not-mobile-plat/32583766