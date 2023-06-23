/*
 * 
 * System.err: android.os.NetworkOnMainThreadException
 * 	--> https://www.geeksforgeeks.org/how-to-fix-android-os-networkonmainthreadexception/ 
 *  (méthode 4)
 * 
 * 
adb uninstall vvnx.FtpKs && \
adb install out/target/product/generic_arm64/system/app/FtpKs/FtpKs.apk 
 */

package vvnx.FtpKs;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

import android.widget.Toast; 



public class FtpKs extends Activity {
	
	public static String TAG = "vvnx";

	FTPClient ftpClient = new FTPClient();
	FTPFile[] files;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getLayoutInflater().inflate(R.layout.FtpKs, null);
        setContentView(view);
                
        Thread gfgThread = new Thread(new Runnable() {
		@Override
		public void run() {
        try  {
			String audio_files = "";
			
			ftpClient.connect("5.135.183.126", 21);
			ftpClient.login("anonymous", "");
			//Pas intuitif: par défaut le type est ASCII et sur des audio files ça donne des micro coupures très régulières et très chiantes
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			Log.d(TAG, "status  :: " + ftpClient.getStatus()); 
			
			// lists files and directories in the current working directory https://www.codejava.net/java-se/ftp/java-ftp-list-files-and-directories-example
			files = ftpClient.listFiles();
			for (FTPFile file : files) {
			    String details = file.getName();
			    Log.d(TAG, "details = "+details);
			    
			    if (Pattern.matches(".*mp3", details) || Pattern.matches(".*ogg", details)) {
					Log.d(TAG, "Pattern.matches = YES"); 
					audio_files += details;		 
					audio_files += " ";	
					}
			    
			    
			}
			Log.d(TAG, "audio_files = " + audio_files);
			afficheToast(audio_files);
	
			} catch (Exception e) {
            e.printStackTrace();
            //Le plus simple pour affichage mais je doute d'avoir qqe chose d'informatif...
            //https://stackoverflow.com/questions/1149703/how-can-i-convert-a-stack-trace-to-a-string
            afficheToast(Log.getStackTraceString(e));
			}
		}
		});
 
		gfgThread.start();
        
        
 
		
		
    }
    
    
		public void DownloadFile(String ks_file) {
       
        Thread dwnldThread = new Thread(new Runnable() {
		@Override
		public void run() {
		
			
			
			File dest_file = new File(Environment.getExternalStorageDirectory() + "/Music/" + ks_file);
			try {	
			OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(dest_file));
            boolean success = ftpClient.retrieveFile(ks_file, outputStream1);
            outputStream1.close();
				} catch (IOException ex) {
            Log.d(TAG, "Error: " + ex.getMessage());
				}


		}
		});
		dwnldThread.start();
		}
    
    
        public void ActionPressBouton_1(View v) {		
		Log.d(TAG, "ActionPressBouton_1");
				for (FTPFile file : files) {
			    String details = file.getName();
					if (!file.isDirectory()) {
						Log.d(TAG, "details = " + details);
						//https://www.javatpoint.com/java-regex
						//EKO.*mp3 --> . = anything * = un nombre de fois indeterminé
						if (Pattern.matches("FIP.*ogg", details)) {
							//Log.d(TAG, "Pattern.matches = YES"); 
							DownloadFile(details);							 
							}
						}
			    
				}
		}
		
		public void ActionPressBouton_2(View v) {		
		Log.d(TAG, "ActionPressBouton_2");
				for (FTPFile file : files) {
			    String details = file.getName();
					if (!file.isDirectory()) {
						Log.d(TAG, "details = " + details);
						//https://www.javatpoint.com/java-regex
						//FIP.*mp3 --> . = anything * = un nombre de fois indeterminé
						if (Pattern.matches("ELECTRO.*ogg", details)) {
							//Log.d(TAG, "Pattern.matches = YES"); 
							DownloadFile(details);							 
							}
						}
			    
				}
		}
		
		public void ActionPressBouton_3(View v) {		
		Log.d(TAG, "ActionPressBouton_3");
				for (FTPFile file : files) {
			    String details = file.getName();
					if (!file.isDirectory()) {
						Log.d(TAG, "details = " + details);
						//https://www.javatpoint.com/java-regex
						//EKO.*ogg --> . = anything * = un nombre de fois indeterminé
						if (Pattern.matches("EKO.*ogg", details)) {
							//Log.d(TAG, "Pattern.matches = YES"); 
							DownloadFile(details);							 
							}
						}
			    
				}
		}
		
		public void afficheToast(String message) {
		    runOnUiThread (new Runnable() {
				public void run() {
				Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show(); 
				}
			});
		}
		
}

