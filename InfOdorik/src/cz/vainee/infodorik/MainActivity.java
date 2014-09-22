package cz.vainee.infodorik;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
//import org.apache.commons.io.IOUtils;

public class MainActivity extends ActionBarActivity {
	
	// the tag to be used in the application logs
	private static final String TAG = "InfOdorik";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		
 		
/*		Intent intent = new Intent(MainActivity.this,
				SettingsActivity.class);
		startActivity(intent);
*/
		
		// TODO: why the ID does not match?		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(MainActivity.this,
					SettingsActivity.class);
			startActivity(intent);

			/*Toast.makeText(this, "Updating the textview",
			                Toast.LENGTH_LONG).show();
					updateSettingsInTextView();*/

			return true;
		}
		Toast.makeText(this, "Ne, neni to settings.",
				Toast.LENGTH_LONG).show();
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	/** Called when the user clicks the Balance button */
	public void printBalance(View view) {
		//Intent intent = new Intent(this, DisplayMessageActivity.class);
		//System.out.println("InfOdorik debug message - println");
		android.util.Log.d(TAG, "InfOdorik debug message");
		
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		tv1.append("Checking your balance ... ");
		
		try {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

			String username = prefs.getString("pref_username", "");
			String passw = prefs.getString("pref_password", "");

			StringBuilder urlBuilder = new StringBuilder("https://www.odorik.cz/api/v1/balance?user=");
			urlBuilder.append(username);
			urlBuilder.append("&password=");
			urlBuilder.append(passw);
			URL odorikUrl = new URL(urlBuilder.toString());
			
			//System.out.println(urlBuilder.toString());
			//android.util.Log.d(TAG, urlBuilder.toString());
			
			new HttpHandlerLocal().execute(odorikUrl);
		}
		catch (MalformedURLException e)
		{
			android.util.Log.e(TAG, "Unknown balance request(" + e.getMessage() + ")", e);
		}
		
		/*URLConnection urlConnection = url.openConnection();
		urlConnection.addRequestProperty("user", "123456");
		urlConnection.addRequestProperty("password", "abcdefg");*/
		
		
		//tv1.append("Balance: " + this.handleBalanceMessage() + "\n");
	}
	
	
	/** Called when the user clicks the Lines button */
	public void printLines(View view) {
		//Intent intent = new Intent(this, DisplayMessageActivity.class);
		//System.out.println("InfOdorik debug message - println");
		android.util.Log.d(TAG, "InfOdorik debug message");
		
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		tv1.append("Checking your lines ... ");
		
		try {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

			String username = prefs.getString("pref_username", "");
			String passw = prefs.getString("pref_password", "");

			StringBuilder urlBuilder = new StringBuilder("https://www.odorik.cz/api/v1/lines?user=");
			urlBuilder.append(username);
			urlBuilder.append("&password=");
			urlBuilder.append(passw);
			URL odorikUrl = new URL(urlBuilder.toString());
			
			//System.out.println(urlBuilder.toString());
			//android.util.Log.d(TAG, urlBuilder.toString());
			
			new HttpHandlerLocal().execute(odorikUrl);
		}
		catch (MalformedURLException e)
		{
			android.util.Log.e(TAG, "Unknown lines request(" + e.getMessage() + ")", e);
		}
		
		/*URLConnection urlConnection = url.openConnection();
		urlConnection.addRequestProperty("user", "123456");
		urlConnection.addRequestProperty("password", "abcdefg");*/
		
		
		//tv1.append("Balance: " + this.handleBalanceMessage() + "\n");
	}
	
	/** Called when the user clicks the CallLog button */
	public void printCallLog(View view) {
		//Intent intent = new Intent(this, DisplayMessageActivity.class);
		//System.out.println("InfOdorik debug message - println");
		android.util.Log.d(TAG, "InfOdorik debug message");
		
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		tv1.append("Checking calls ... ");
		
		try {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

			String username = prefs.getString("pref_username", "");
			String passw = prefs.getString("pref_password", "");

			//https://www.odorik.cz/api/v1/calls.csv?user=123456&password=abcdefgh&from=2014-07-01T00:00:00&to=2014-07-31T23:59:59
			StringBuilder urlBuilder = new StringBuilder("https://www.odorik.cz/api/v1/calls.csv?user=");
			urlBuilder.append(username);
			urlBuilder.append("&password=");
			urlBuilder.append(passw);
			urlBuilder.append("&from=2014-07-20T00:00:00&to=2014-07-31T23:59:59");
			URL odorikUrl = new URL(urlBuilder.toString());
			
			//System.out.println(urlBuilder.toString());
			//android.util.Log.d(TAG, urlBuilder.toString());
			
			new HttpHandlerLocal().execute(odorikUrl);
		}
		catch (MalformedURLException e)
		{
			android.util.Log.e(TAG, "Unknown balance request(" + e.getMessage() + ")", e);
		}
		
		/*URLConnection urlConnection = url.openConnection();
		urlConnection.addRequestProperty("user", "123456");
		urlConnection.addRequestProperty("password", "abcdefg");*/
		
		
		//tv1.append("Balance: " + this.handleBalanceMessage() + "\n");
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
			TextView tv1 = (TextView) findViewById(R.id.textView1);
/*			Float balance;
			try {
				balance = Float.parseFloat(result);
			}
			catch (NumberFormatException exception) {
				
				return;
			}
*/
			
			tv1.getContext();
			// store the value for the next time when we will be offline (synchronization outage)
			//Context context = getActivity();
			/*
			SharedPreferences sharedPref = tv1.getContext().getSharedPreferences(
			        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putFloat("balance", balance);
			editor.commit();
			*/
			
			tv1.append(result + "\n");
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
