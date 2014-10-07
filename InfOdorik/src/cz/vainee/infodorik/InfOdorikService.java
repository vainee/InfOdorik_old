/**
 * 
 */
package cz.vainee.infodorik;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author vainee
 *
 */
public class InfOdorikService extends IntentService {
	
	// the tag to be used in the application logs
	private static final String TAG = "InfOdorik_Service";
	
	
	public InfOdorikService(String name) {
		super(name);
		android.util.Log.d(TAG, "InfOdorikService constructor ######");
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
		Toast.makeText(getBaseContext(), "onHandleIntent", Toast.LENGTH_LONG).show();
		
		android.util.Log.d(TAG, "InfOdorikService::onHandleIntent ######");
		
		/*TextView tv1 = (TextView) findViewById(R.id.textView1);
		tv1.append("\nInfOdorikService::onHandleIntent ######\n");
		*/
		
	}
	
	
	
/*	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
*/
}
