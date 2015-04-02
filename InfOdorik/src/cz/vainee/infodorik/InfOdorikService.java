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
//import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.text.format.Time;

/**
 * @author Pavel Vejnarek
 *
 */
public class InfOdorikService extends IntentService {
	
	// the tag to be used in the application logs
	private static final String TAG = "InfOdorik_Service";
	
	// API basic url
	// TODO: completely replace with API class
	private static final String API_URL = "https://www.odorik.cz/api/v1/";
	
	// API resource IDs
	private static final String RESOURCE_CREDIT = "balance";
	private static final String RESOURCE_LINES = "lines.json";
	private static final String RESOURCE_CALLS = "calls.json";
	
	
	// Service interface definition
	public static final String SERVICE_METHOD = "method";
	// TODO: temporary, define methods using enum
	public static final String SERVICE_METHOD_UPDATE = "update";
	
	//
	private static final String XML_SCHEMA_DATETIME_FORMAT = "yyyy-MM-ddTHH:mm:ss+Z";
	
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
		if (method.equals(SERVICE_METHOD_UPDATE))
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
		try {
			StringBuilder urlBuilder = initWithCredentials(RESOURCE_CREDIT);
			URL creditUrl = new URL(urlBuilder.toString());
			String result = handleHttpMessage(creditUrl);
			android.util.Log.d(TAG, "Credit fetched by the service: " + result);
		}
		catch (MalformedURLException e)
		{
			android.util.Log.e(TAG, "Unknown request(" + e.getMessage() + ")", e);
		}
	}
	
	private void method_update_lines() {
		android.util.Log.d(TAG, "method_update_lines entered");
		try {
			StringBuilder urlBuilder = initWithCredentials(RESOURCE_LINES);
			URL creditUrl = new URL(urlBuilder.toString());
			String result = handleHttpMessage(creditUrl);
			android.util.Log.d(TAG, "Lines fetched by the service: " + result);
		}
		catch (MalformedURLException e)
		{
			android.util.Log.e(TAG, "Unknown request(" + e.getMessage() + ")", e);
		}
	}
	
	private void method_update_CallLog() {
		android.util.Log.d(TAG, "method_update_CallLog entered");
		try {
			StringBuilder urlBuilder = initWithCredentials(RESOURCE_CALLS);
			
//			SimpleDateFormat sdf = new SimpleDateFormat(XML_SCHEMA_DATETIME_FORMAT, Locale.US);
//			StringBuilder dateTo.format("%30s %s\n", XML_SCHEMA_DATETIME_FORMAT, sdf.format(new Date(0)));

			// solution of date handling inspired by:
			// http://stackoverflow.com/questions/3747490/android-get-date-before-7-days-one-week
			Time timeTo = new Time();
			timeTo.set(System.currentTimeMillis());
			String timeToFormatted =  timeTo.format("%Y-%m-%dT%H:%M:%S");

			Time timeFrom = new Time();
			long timeFromMillis = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000); // seven days ago
			timeFrom.set(timeFromMillis);
			// get the whole day
			timeFrom.hour = 0;
			timeFrom.minute = 0;
			timeFrom.second = 0;
			String timeFromFormatted = timeFrom.format("%Y-%m-%dT%H:%M:%S");
			 

			System.out.println("Service timeFromFormatted: " + timeFromFormatted +
					", to: " + timeToFormatted);
			android.util.Log.d(TAG, "Service timeFromFormatted: " + timeFromFormatted +
					", to: " + timeToFormatted);
			urlBuilder.append("&from=" /*"2013-01-01T00:00:00"*/ + timeFromFormatted);
			urlBuilder.append("&to=" /*"2015-01-01T23:59:59"*/ + timeToFormatted);
			urlBuilder.append("&page_size=30");
			urlBuilder.append("&page=1");
			
			URL creditUrl = new URL(urlBuilder.toString());
			String result = handleHttpMessage(creditUrl);
			android.util.Log.d(TAG, "Calls fetched by the service: " + result);
			System.out.println("Calls fetched by the service: " + result);
		}
		catch (MalformedURLException e)
		{
			android.util.Log.e(TAG, "Unknown request(" + e.getMessage() + ")", e);
		}
	}
	
	private void method_update_data() {
		android.util.Log.d(TAG, "method_update_data entered");
	} //method_update_data
	
	/**
	 * @param resourceName resource string identifier to be used during URI construction
	 * @return URI stringBuilder with the URL full path and credentials already included
	 */
	private StringBuilder initWithCredentials(String resourceName)
	{
		// get the credentials from the APP user preferences
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String username = prefs.getString("pref_username", "");
		String passw = prefs.getString("pref_password", "");

		// construct the URI
		StringBuilder urlBuilder = new StringBuilder(API_URL);
		urlBuilder.append(resourceName);
		urlBuilder.append("?user=");
		urlBuilder.append(username);
		urlBuilder.append("&password=");
		urlBuilder.append(passw);
		
		return urlBuilder;
	} // initWithCredentials
	
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
	} // handleHttpMessage

} // InfOdorikService
