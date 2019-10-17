package app;

import static uk.co.caprica.picam.CameraConfiguration.cameraConfiguration;
import static uk.co.caprica.picam.PicamNativeLibrary.installTempLibrary;

import java.util.concurrent.TimeUnit;

import uk.co.caprica.picam.ByteArrayPictureCaptureHandler;
import uk.co.caprica.picam.Camera;
import uk.co.caprica.picam.CameraConfiguration;
import uk.co.caprica.picam.CaptureFailedException;
import uk.co.caprica.picam.enums.AutomaticWhiteBalanceMode;
import uk.co.caprica.picam.enums.Encoding;


//Based On https://github.com/caprica/picam

public class RaCamera {

    public Camera camera;
    public CameraConfiguration config;

    public RaCamera(String[] Settings) 
    {
        try {

            // Install picam Temporary Library
            installTempLibrary();

            // Configuration For Camera
            config = cameraConfiguration()
                .width(Integer.parseInt(Settings[0])) //  original 2592
                .height(Integer.parseInt(Settings[1])) // 1944
                .quality(Integer.parseInt(Settings[2]))
                .encoding(Encoding.JPEG)
                .automaticWhiteBalance(AutomaticWhiteBalanceMode.AUTO);

            // Setting-up Camera
            camera = new Camera(config);

            // Wait around 4sec when setting up camera before Starting, capturing Pictures
            TimeUnit.SECONDS.sleep(4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] GetPictureInBinaryForm()
    {
        ByteArrayPictureCaptureHandler ByteDataHandler = new ByteArrayPictureCaptureHandler();
    
        try {

            camera.takePicture(ByteDataHandler);
            return ByteDataHandler.result();

        } catch (CaptureFailedException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}

/*
Based On:

C++
    https://github.com/cedricve/raspicam/blob/master/README.md
    http://www.uco.es/investiga/grupos/ava/node/40
*/