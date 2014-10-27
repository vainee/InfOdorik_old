/**
 * 
 */
package cz.vainee.infodorik;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

/**
 * @author vainee
 *
 */
public class InfOdorikService extends IntentService {
	
	// the tag to be used in the application logs
	private static final String TAG = "InfOdorik_Service";
	
	// API basic url
	// TODO: completely replace with API class
	private static final String API_URL = "https://www.odorik.cz/api/v1/";
	
	// Service interface definition
	public static final String SERVICE_METHOD = "method";
	// TODO: temporary, define methods using enum
	public static final String SERVICE_METHOD_UPDATE = "update";
	
	
/*	@Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	    //TODO do something useful
		android.util.Log.d(TAG, "InfOdorikService: onStartCommand ######");
	    //return Service.START_NOT_STICKY;
	  }*/

	public InfOdorikService() {
		super("InfOdorikService");
		android.util.Log.d(TAG, "InfOdorikService() constructor ######");
		// TODO Auto-generated constructor stub
	}
	
	public InfOdorikService(String name) {
		super(name);
		android.util.Log.d(TAG, "InfOdorikService(" + name  + ") constructor ######");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		// Normally we would do some work here, like download a file.
		// For our sample, we just sleep for 5 seconds.
/*		long endTime = System.currentTimeMillis() + 5 * 1000;
		while (System.currentTimeMillis() < endTime) {
			synchronized (this) {
				try {
					wait(endTime - System.currentTimeMillis());
				} catch (Exception e) {
				}
			}
		}
*/
		android.util.Log.d(TAG, "InfOdorikService::onHandleIntent ######");
		
		//Toast.makeText(getBaseContext(), "onHandleIntent", Toast.LENGTH_LONG).show();
		
		// Select the appropriate method
		String method = intent.getStringExtra(SERVICE_METHOD);
		if (method == SERVICE_METHOD_UPDATE)
		{
			method_update();
		}
		else
		{
			// unknown method
		}
		

		/*TextView tv1 = (TextView) findViewById(R.id.textView1);
		tv1.append("\nInfOdorikService::onHandleIntent ######\n");
		*/
		
		
		// Send the system notification
		//Notification serviceStartedNotification = NotificationCompat.Builder.build();
		NotificationCompat.Builder serviceNotificationBuilder =
				new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.notification_icon)
				.setContentTitle("InfOdorik notification")
				.setContentText("The InfOdorik Service has been started!");

		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainActivity.class);

		
		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		serviceNotificationBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		int mId = 0;
		mNotificationManager.notify(mId, serviceNotificationBuilder.build());
		
		
	}
	
	
	
/*	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
*/
	
	/**
	 * method_udate - update the basic information from Odorik API
	 * TODO: cache/store the updated information for later use
	 */
	private void method_update()
	{
		method_update_credit();
		method_update_lines();
		method_update_CallLog();
		method_update_data();
		
		// TODO: refresh the widget / broadcast the "data updated" event to others
	}
	
	
	private void method_update_credit() {
		android.util.Log.d(TAG, "method_update_credit entered");
		
//		TextView tv1 = (TextView) findViewById(R.id.textView1);
//		tv1.append("Checking your balance ... ");
		
		try {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

			String username = prefs.getString("pref_username", "");
			String passw = prefs.getString("pref_password", "");

			StringBuilder urlBuilder = new StringBuilder(API_URL);
			urlBuilder.append("balance?user=");
			urlBuilder.append(username);
			urlBuilder.append("&password=");
			urlBuilder.append(passw);
			URL odorikUrl = new URL(urlBuilder.toString());
			
			new HttpHandlerLocal().execute(odorikUrl);
		}
		catch (MalformedURLException e)
		{
			android.util.Log.e(TAG, "Unknown credit request(" + e.getMessage() + ")", e);
		}
		
		/*URLConnection urlConnection = url.openConnection();
		urlConnection.addRequestProperty("user", "123456");
		urlConnection.addRequestProperty("password", "abcdefg");*/
		
		
		//tv1.append("Balance: " + this.handleBalanceMessage() + "\n");
	}
	
	private void method_update_lines() {
		android.util.Log.d(TAG, "method_update_lines entered");
	}
	
	private void method_update_CallLog() {
		android.util.Log.d(TAG, "method_update_CallLog entered");
	}
	
	private void method_update_data() {
		android.util.Log.d(TAG, "method_update_data entered");
	}
	
	private class HttpHandlerLocal extends AsyncTask<URL, Integer, String> {

		@Override
		protected String doInBackground(URL... params) {
			StringBuilder rv = new StringBuilder();
			for (URL oneUrl : params) {
				rv.append(handleHttpMessage(oneUrl));
			}
			return rv.toString();
		}
		
		@Override
		protected void onPostExecute(String result) {
//			TextView tv1 = (TextView) findViewById(R.id.textView1);
/*			Float balance;
			try {
				balance = Float.parseFloat(result);
			}
			catch (NumberFormatException exception) {
				
				return;
			}
*/
			
//			tv1.getContext();
			// store the value for the next time when we will be offline (synchronization outage)
			//Context context = getActivity();
			/*
			SharedPreferences sharedPref = tv1.getContext().getSharedPreferences(
			        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putFloat("balance", balance);
			editor.commit();
			*/
			
//			tv1.append(result + "\n");
		}
		
		private String handleHttpMessage(URL url) {
			StringBuilder rv = new StringBuilder();
			
			try {
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

				BufferedReader inBufReader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				String inputLine;
				while ((inputLine = inBufReader.readLine()) != null)
				{
					//System.out.println(inputLine);
					rv.append(inputLine + "\n");
				}
				urlConnection.disconnect();
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}

			if (rv.length()>0)
				return rv.toString();
			else
				return "-----";
		}
	
	}
	
}
