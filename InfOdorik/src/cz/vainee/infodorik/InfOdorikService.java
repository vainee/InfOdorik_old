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
	
	
	public InfOdorikService(String name) {
		super(name);
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
	}
	
	
	
/*	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
*/
}
