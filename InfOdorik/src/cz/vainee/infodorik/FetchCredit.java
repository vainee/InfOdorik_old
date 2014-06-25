/**
 * 
 */
package cz.vainee.infodorik;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
//import android.content.ComponentName;
import android.os.AsyncTask;
import android.widget.RemoteViews;

/**
 * @author vainee
 *
 */
public class FetchCredit extends AsyncTask<URL, Integer, String> {

	private AppWidgetManager appWidgetManager;
	private RemoteViews remoteViews;
    private ComponentName watchWidget;
    
	/**
	 * @param appWidgetManager
	 * @param remoteViews
	 * @param watchWidget
	 */
	public FetchCredit(
			AppWidgetManager appWidgetManager,
			RemoteViews remoteViews,
			ComponentName watchWidget) {
		
		super();
		this.appWidgetManager = appWidgetManager;
		this.remoteViews = remoteViews;
		this.watchWidget = watchWidget;
	}

	@Override
	protected String doInBackground(URL... params) {
		StringBuilder rv = new StringBuilder();
		for (URL oneUrl : params) {
			rv.append(handleBalanceMessage(oneUrl));
		}
		return rv.toString();
	}


    protected void onPostExecute(String result) {
        if (appWidgetManager != null) {
            //String finalString = "sync @";
            remoteViews.setTextViewText(R.id.textview_credit, result);
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        }
    }
    
    private String handleBalanceMessage(URL url) {
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
			return "<NOTHING>\n";
	}
    
    

	
}
