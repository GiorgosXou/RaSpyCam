package app;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    private static MySQL server;
    private static RaCamera camera;
    private static AES_Encryption encrypter;

    public static String[] Camera_Settings = new String[] {"648", "486","77"};
    public static String[] MySQL_Settings = new String[] {"Username", "Password" ,"db4free.net","raspycam","3306","false","RaSpy","Ra"};
    public static String[] AES_Settings = new String[] {"RaSpyCamKeyPswrd"};

    public static void main(String[] args) throws Exception {

        try {

            // If not argUMENTs's length is not 0 then ... 
            if(args.length != 0){ ChangeDefaultSettingsFrom(args);}
            
            server = new MySQL(MySQL_Settings); 
            camera = new RaCamera(Camera_Settings);
            encrypter = new AES_Encryption(AES_Settings);

            Timer Loop = new Timer();
            Loop.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    switch (ReadDectyptedMessage()) {
                    case "TakeAPicture":

                        SendEncryptedMessage(camera.GetPictureInBinaryForm());
                        break;
                        
                    }
                    
                }
            }, 100, 900); // 900 + 100 = 1000 ms = 1 sec

        } catch (Exception e) {
            // something like reboot?
        }

    }


    private static String ReadDectyptedMessage() {
        return (new String(encrypter.DecryptAES(server.RequestBinaryMessage())));
    }
    private static void SendEncryptedMessage(byte[] Message) {
       server.SendBinaryMessage(encrypter.EncryptAES(Message));
    }


    private static void ChangeDefaultSettingsFrom(String[] args)
    {

        String ArgsToSTR = Arrays.toString(args);

        final Pattern pattern = Pattern.compile("(Camera|AES|MySQL)=\\[(.+?)\\]");
        final Matcher match = pattern.matcher(ArgsToSTR.substring(1, ArgsToSTR.length()-1).replace(",,", ","));
        

        while (match.find()) {

            //System.out.println(match.group());
            
            if (match.group().startsWith("Camera=["))
            {
                RetriveSettingsFrom(match.group(), Camera_Settings, "Camera=[");
            }
            else if(match.group().startsWith("MySQL=["))
            {
                RetriveSettingsFrom(match.group(), MySQL_Settings, "MySQL=[");
            }
            else if(match.group().startsWith("AES=["))
            {
                RetriveSettingsFrom(match.group(), AES_Settings, "AES=[");
            }
            else
            {
                //#Unkonown
            }
        }
            
    }
    public static void RetriveSettingsFrom(String Group, String[] DefaultSettings , String CaseName)
    {
        String[] NewSettings = Group.substring(CaseName.length(), Group.length()-1).replace(" ", "").split(",");
        
        if (DefaultSettings.length >= NewSettings.length)
        {
            for (int i = 0; i < NewSettings.length; i++) { 
                DefaultSettings[i] = NewSettings[i]; 
            }
        }else{
            //Default
        }
        
    }
}
// https://db4free.net/conditions.php

/*
 * installTempLibrary();
 * 
 * CameraConfiguration config = cameraConfiguration().width(2592).height(1944)
 * .automaticWhiteBalance(AutomaticWhiteBalanceMode.AUTO).encoding(Encoding.JPEG
 * ).quality(100);
 * 
 * try (Camera camera = new Camera(config)) {
 * 
 * // Wait around 4sec when setting up camera before Starting to get Pictures
 * TimeUnit.SECONDS.sleep(4);
 * 
 * ByteArrayPictureCaptureHandler b = new ByteArrayPictureCaptureHandler(); //
 * camera.open();
 * 
 * // camera.takePicture(b,5);// 5 milliseconds
 * 
 * camera.takePicture(b);
 * 
 * OutputStream os = new FileOutputStream("iAm.jpg");
 * 
 * os.write(b.result()); os.close();
 * 
 * camera.close();
 * 
 * // b.pictureData(data);
 * 
 * } catch (CaptureFailedException e) { e.printStackTrace(); }
 */
