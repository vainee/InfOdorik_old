/**
 * 
 */
package cz.vainee.infodorik;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author vainee
 *
 */
public class InfOdorikService extends IntentService {
	
	// the tag to be used in the application logs
	private static final String TAG = "InfOdorik_Service";
	
	
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
		
		Toast.makeText(getBaseContext(), "onHandleIntent", Toast.LENGTH_LONG).show();
		

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
		    (NotificationManager) getSystemService(getBaseContext().NOTIFICATION_SERVICE);
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
}
