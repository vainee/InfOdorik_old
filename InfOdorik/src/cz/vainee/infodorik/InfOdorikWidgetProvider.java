package cz.vainee.infodorik;

import java.net.MalformedURLException;
import java.net.URL;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;


public class InfOdorikWidgetProvider extends AppWidgetProvider {

	//	  private static final String ACTION_CLICK = "ACTION_CLICK";

	// the tag to be used in the application logs
	private static final String TAG = "InfOdorik_Widget";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		android.util.Log.d(TAG, "InfOdorik widget onUpdate() started");
		
		URL odorikUrl = null;
		
		try {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

			String username = prefs.getString("pref_username", "");
			String passw = prefs.getString("pref_password", "");

			StringBuilder urlBuilder = new StringBuilder("https://www.odorik.cz/api/v1/balance?user=");
			urlBuilder.append(username);
			urlBuilder.append("&password=");
			urlBuilder.append(passw);
			//URL odorikUrl = new URL("https://www.odorik.cz/api/v1/balance");
			odorikUrl = new URL(urlBuilder.toString());
		}
		catch (MalformedURLException e)
		{
			android.util.Log.e(TAG, "Unknown balance request(" + e.getMessage() + ")", e);
		}
		
/*		TextView tv1 = (TextView) findViewById  (R.id.textView1);
		tv1.append("Checking your balance ... ");
*/		
		RemoteViews remoteViews;
	    ComponentName watchWidget;

	    remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget_layout);
	    watchWidget = new ComponentName(context, InfOdorikWidgetProvider.class);

	    // onUpdate is called every xx seconds.
	    // trigger fetch from the server!
	    FetchCredit fetchCredit = new FetchCredit(appWidgetManager, remoteViews, watchWidget);
	    fetchCredit.execute(odorikUrl);
		
	    android.util.Log.d(TAG, "InfOdorik widget onUpdate() exited");		
	}



}