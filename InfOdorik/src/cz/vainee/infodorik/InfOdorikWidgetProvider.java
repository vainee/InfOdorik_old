package cz.vainee.infodorik;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
		
		RemoteViews remoteViews;
	    ComponentName watchWidget;

	    remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget_layout);
	    watchWidget = new ComponentName(context, InfOdorikWidgetProvider.class);

	    // onUpdate is called every xx seconds.
	    // trigger fetch from the server!
	    FetchCredit fetchCredit = new FetchCredit(appWidgetManager, remoteViews, watchWidget, context);
	    fetchCredit.execute(odorikUrl);
		
	    
	    
	    // Clickable widget to run the main activity, idea/source:
	    // http://wptrafficanalyzer.in/blog/android-home-screen-app-widget-with-onclick-event/
	    final int N = appWidgetIds.length;
	    
        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
 
            // Create an Intent to launch MainActivity
            Intent intent = new Intent(context, MainActivity.class);
 
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
 
            // This is needed to make this intent different from its previous intents
            intent.setData(Uri.parse("tel:/"+ (int)System.currentTimeMillis()));
 
            // Creating a pending intent, which will be invoked when the user
            // clicks on the widget
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                            intent,PendingIntent.FLAG_UPDATE_CURRENT);
 
            // Get the layout for the App Widget
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_layout);
 
            //  Attach an on-click listener to the widget's object
            views.setOnClickPendingIntent(R.id.textview_credit, pendingIntent);
 
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
	    
	    
	    android.util.Log.d(TAG, "InfOdorik widget onUpdate() exited");		
	}



}