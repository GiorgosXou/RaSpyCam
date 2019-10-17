package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.MysqlDataSource;

public class MySQL {

    public MysqlDataSource SourceCredentials;
    private Connection conn;

    private PreparedStatement PreparedStmt;
    private Statement Stmt;

    private String Talks;
    private String Hears;

    private byte[] Result;

    public MySQL(String[] Settings) {
        try {

            // Setting-up the Credentials of MySQL Server [...]
            SourceCredentials = new MysqlDataSource();

            SourceCredentials.setUser(Settings[0]); //                         Username
            SourceCredentials.setPassword(Settings[1]); //                     Password
            SourceCredentials.setServerName(Settings[2]); //                   Server's Link
            SourceCredentials.setDatabaseName(Settings[3]); //                 Name of The Database
            SourceCredentials.setPort(Integer.parseInt(Settings[4])); //       MySQL-Server's Port Number (usually 3306)
            SourceCredentials.setUseSSL(Boolean.parseBoolean(Settings[5])); // This server doesn't support SSL

            Talks = Settings[6];
            Hears = Settings[7];

            // Connecting to Server
            conn = SourceCredentials.getConnection();

            // Create A Statement which will be used to execute a query for Readding data
            Stmt = conn.createStatement();

            //Clean Left Over Data
            Stmt.executeUpdate("UPDATE `RaSpy0` SET `" + Hears + "` = ''");

            // Preparing Statement For Talking to "Master"/"Admin"
            PreparedStmt = conn.prepareStatement("UPDATE `RaSpy0` SET `" + Talks + "` = ?"); // RaspberrySpy Says/(SETs its "mouth" equal to "?")

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SendBinaryMessage(byte[] Message) {
        try {
            
            PreparedStmt.setBytes(1, Message);
            PreparedStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    public void SendTextMessage(byte[] Message)
    {

    }

    public byte[] RequestBinaryMessage()
    {
        try {

            // Request What Master Has to tell you and then Read
            ResultSet rs = Stmt.executeQuery("SELECT " + Hears + " FROM `RaSpy0`");
            rs.next();

            // Save Result Before Executing Update because ResultSet throws exception else.
            Result = rs.getBytes(1);

            if (rs.getBytes(1).length != 0){

                // Update-clear Master, "such as it "knows" that you "heared" what has to say to you"
                Stmt.executeUpdate("UPDATE `RaSpy0` SET `" + Hears + "` = ''");
                
            }

            return Result;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     this function is used for letting Admin/Master/User know that you are done with what he/she/it sent you
     Simply it Updates/cleans what sent you setting it to '' [...] it is used after Data is Readen/(Read?)
    */
    /*
    public void Let_Master_Know_That_You_Are_Done_With_The_Processing_Of_Data()
    {
        // Update-clear Master, such as it knows that you heared what has to say to you
        //Stmt.executeUpdate("UPDATE `RaSpy0` SET `Ra` = ''");
    }
    */
}