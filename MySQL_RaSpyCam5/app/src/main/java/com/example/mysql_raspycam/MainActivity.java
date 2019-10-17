/*
   new SendData("") & GetData("") because:

   https://stackoverflow.com/questions/6373826/execute-asynctask-several-times

*/


package com.example.mysql_raspycam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
//import java.lang.Byte;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;


import static java.lang.Class.*;


public class MainActivity extends AppCompatActivity {


    static String url; //db4free.net
    static String user;
    static String pass;

    static String WhoTalks;
    static String WhoHears;

    java.sql.Connection con;
    java.sql.Statement st;
    java.sql.PreparedStatement pstmt;
    java.sql.ResultSet rs;

    boolean connected = false;

    boolean DATA_Recieved = false;
    byte[] Recieved_DATA = new byte[0];

    String AES_KeyPassword;
    AES_Encryption AES;

    TextView textView1;
    ImageView imageView1;
    EditText editText1;

    Button button1;
    Button button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Button    -- [Initialization]
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button1.setEnabled(false);

        // ImageView -- [Initialization]
        imageView1 = findViewById(R.id.imageView1);

        // TextView  -- [Initialization]
        textView1 = findViewById(R.id.textView1);
        textView1.setMovementMethod(new ScrollingMovementMethod());
        textView1.setText("");

        // EditText  -- [Initialization]
        editText1 = findViewById(R.id.editText1);
        editText1.setText("Username,Password,db4free.net,3306,raspycam,Ra,RaSpy,RaSpyCamKeyPswrd");


        textView1.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
               /*
                // find the amount we need to scroll.  This works by
                // asking the TextView's internal layout for the position
                // of the final line and then subtracting the TextView's height
                final int scrollAmount = textView1.getLayout().getLineTop(textView1.getLineCount()) - textView1.getHeight();
                // if there is no need to scroll, scrollAmount will be <=0
                if (scrollAmount > 0)
                    textView1.scrollTo(0, scrollAmount);
                else
                    textView1.scrollTo(0, 0);

                */
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        // -- [Connect] Button
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringTokenizer st = new StringTokenizer(editText1.getText().toString(), ",");
                if (st.countTokens() == 8) {
                    user = st.nextToken();
                    pass = st.nextToken();
                    url = "jdbc:mysql://" + st.nextToken() + ":" + st.nextToken() + "/" + st.nextToken();
                    WhoTalks = st.nextToken();
                    WhoHears = st.nextToken();
                    AES_KeyPassword = st.nextToken();

                    textView1.append("[✓] Complete Credentials \n");
                } else {
                    textView1.append("[X] incomplete Credentials \n");
                    return;
                }

                try {

                    textView1.append("[...] Trying To Connect \n");
                    if (new Connect_To_MYSQL_Server().execute().get() == "Complete") {
                        textView1.append("[✓] Connected \n");

                        new SendData("").execute().get(); // cleaning left Data  // not sure if get() is need, (i know it doesnt return anything there but i think of it as "delay" until the asynctast end)

                        button1.setEnabled(true);
                        button2.setEnabled(false);
                    }

                    AES = new AES_Encryption(AES_KeyPassword);

                } catch (ExecutionException | InterruptedException e) {
                    textView1.append("[X] Connected \n\n" + e.getMessage() + "\n\n");
                } catch (Exception e) {

                }
            }
        });

        // -- [TakeAPicture] Button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    textView1.append("[...] Sending Request \n");

                    String SendMessage_FeedBack = new SendData("TakeAPicture").execute().get();

                    if (SendMessage_FeedBack != "Sent") {
                        return; /* exit */
                    }

                    textView1.append("[✓] Request Sent \n");
                    textView1.append("[...] Waiting for Data \n");


                    String ReceivedData_FeedBack = new GetData().execute().get(); // Complete Loop Check

                    if (ReceivedData_FeedBack == "Complete") {
                        textView1.append("[✓] Responded \n");

                        byte[] image = AES.DecryptAES(Recieved_DATA);
                        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                        imageView1.setImageBitmap(Bitmap.createScaledBitmap(bmp, imageView1.getWidth(), imageView1.getHeight(), false));

                        new SendData("").execute().get(); // cleaning left Data

                        //textView1.append(new String(AES.DecryptAES(Recieved_DATA)));
                        textView1.append("\n");
                        Recieved_DATA = new byte[0];
                    } else if (ReceivedData_FeedBack == "TimeOut") {
                        new SendData("").execute().get(); // cleaning left Data
                        textView1.append("[X] Time Out \n");
                    }
                    //
                } catch (ExecutionException | InterruptedException e) {
                    textView1.append("[X] Error: \n");
                    textView1.append(e.getMessage() + "\n");
                }


            }

        });
    }

    //while (!connected) { try { Thread.sleep(100); } catch (InterruptedException e) {}}


    //for(String st : strss)
    //{
    //   textView1.setText(textView1.getText() + st + "\n");

    //}


    public class SendData extends AsyncTask<Void, Void, String> {

        private String Message;

        public SendData(String Message) {
            this.Message = Message;
        }

        protected void onPreExecute() {

        }

        protected String doInBackground(Void... params) {
            try {

                if (!Message.equals("")) {
                    pstmt.setBytes(1, AES.EncryptAES(Message.getBytes()));
                    pstmt.execute();
                } else {
                    st.executeUpdate("UPDATE `RaSpy0` SET `" + WhoHears + "` = ''");
                }

            } catch (SQLException e) {
                //return e.getMessage();
            }

            return "Sent";
        }

        protected void onPostExecute(String result) {

        }
    }

    public class GetData extends AsyncTask<Void, Void, String> {

        public GetData() {

        }

        protected void onPreExecute() {

        }

        protected String doInBackground(Void... params) {
            try {
                long tStart = System.currentTimeMillis();

                while (Recieved_DATA.length == 0) {
                    rs = st.executeQuery("select " + WhoHears + " from RaSpy0"); // Hear What "WhoHears" has to tell you about what you requested.
                    rs.next();
                    Recieved_DATA = rs.getBytes(1);

                    if ((System.currentTimeMillis() - tStart) > 29000) { // 20 seconds #MakeItVar
                        return "TimeOut";
                    }
                }
            } catch (SQLException e) {
            }

            return "Complete";
        }

        protected void onPostExecute(String result) {
            if (result.equals("Complete")) {
                DATA_Recieved = true;
                //connected = true;
            }
        }
    }


    public class Connect_To_MYSQL_Server extends AsyncTask<Void, Void, String> {
        //ProgressDialog mProgressDialog;
        //Context context;
        //private String url;

        //Context context, String url
        public Connect_To_MYSQL_Server() {
            // this.context = context;
            //this.url = url;
        }

        protected void onPreExecute() {
            // mProgressDialog = ProgressDialog.show(context, "",
            //         "Please wait, getting database...");
        }

        String Error = "";

        protected String doInBackground(Void... params) {
            try {

                forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, pass); // + "?characterEncoding=latin1&useConfigs=maxPerformance"
                st = con.createStatement();
                pstmt = con.prepareStatement("UPDATE `RaSpy0` SET `" + WhoTalks + "` = ?");

                //textView1.setText(field);
            } catch (SQLException e) {
                Error += " 1 " + e.getMessage();

            } catch (ClassNotFoundException e) {
                Error += " 2 " + e.getMessage();

            }
            return "Complete";
        }

        protected void onPostExecute(String result) {
            //textView1.setText(field);
            if (result.equals("Complete")) {
                connected = true;
                //textView1.setText("Done?\n ");
                // mProgressDialog.dismiss();
            }

        }
    }

}

/*

https://stackoverflow.com/questions/26470117/can-we-connect-remote-mysql-database-in-android-using-jdbc
<uses-permission android:name="android.permission.INTERNET" />
https://stackoverflow.com/questions/50855622/unknown-initial-character-set-index-255-received-from-server
https://jar-download.com/artifacts/mysql/mysql-connector-java/5.1.44/source-code

 */
