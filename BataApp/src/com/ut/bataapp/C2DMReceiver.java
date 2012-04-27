package com.ut.bataapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;
import com.google.android.c2dm.C2DMBaseReceiver;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.BerichtenActivity;
import com.ut.bataapp.activities.KleurcodesActivity;
import com.ut.bataapp.activities.WeerActivity;
import com.ut.bataapp.objects.Bericht;
import com.ut.bataapp.services.C2DMConfig;

/**
 * Broadcast receiver that handles Android Cloud to Data Messaging (AC2DM)
 * messages, initiated by the JumpNote App Engine server and routed/delivered by
 * Google AC2DM servers. The only currently defined message is 'sync'.
 */
public class C2DMReceiver extends C2DMBaseReceiver {

	/** The Constant TAG. */
	static final String TAG = C2DMConfig.makeLogTag(C2DMReceiver.class);
	Context context;
	String deviceRegistrationID;

	/**
	 * Instantiates a new c2 dm receiver.
	 */
	public C2DMReceiver() {
		super(C2DMConfig.C2DM_GOOGLE_ACCOUNT);
	}

	/* (non-Javadoc)
	 * @see com.google.android.c2dm.C2DMBaseReceiver#onError(android.content.Context, java.lang.String)
	 */
	@Override
	public void onError(Context context1, String errorId) {
		Toast.makeText(context1, "Messaging registration error: " + errorId,
				Toast.LENGTH_LONG).show();
	}

	/* (non-Javadoc)
	 * @see com.google.android.c2dm.C2DMBaseReceiver#onMessage(android.content.Context, android.content.Intent)
	 */
	@Override
	protected void onMessage(Context context, Intent intent) {
		String message = intent.getExtras().getString("message");
		String type = intent.getExtras().getString("title");
		if (message != null) {
			Log.d("C2DM", message);
			Utils.addBericht(getApplicationContext(), type, message);
			makeNotification(context, type, message);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void makeNotification(Context context, String type, String message) {
		String titel = "";
		Intent tointent = new Intent(context, BerichtenActivity.class);
		tointent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		
		
		switch(type.charAt(0)) {
		case 'y':
			titel = context.getString(R.string.kleurcode_geel_titel);
			tointent = new Intent(context, KleurcodesActivity.class);
			tointent.putExtra("index", "geel");
			tointent.putExtra("beschrijving", message);
			break;
		case 'g':
			titel = context.getString(R.string.kleurcode_groen_titel);
			tointent = new Intent(context, KleurcodesActivity.class);
			tointent.putExtra("index", "groen");
			tointent.putExtra("beschrijving", message);
			break;
		case 'r':
			titel = context.getString(R.string.kleurcode_rood_titel);
			tointent = new Intent(context, KleurcodesActivity.class);
			tointent.putExtra("index", "rood");
			tointent.putExtra("beschrijving", message);
			break;
		case 'w':
			titel = context.getString(R.string.kleurcode_weer_titel);
			tointent = new Intent(context, WeerActivity.class);
			break;
		default:
			titel = message;
		}
		
		
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.icon, getString(R.string.notification_push_statusbar), System.currentTimeMillis());
		
		// Hide the notification after its selected
		addNotificationParams(notification);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				tointent, PendingIntent.FLAG_CANCEL_CURRENT);
		notification.setLatestEventInfo(context, getString(R.string.notification_push_titel),
				titel, pendingIntent);
		
		notificationManager.notify(nots, notification);
		nots++;
	}
	
	
	/* Voegt flags (volgens user's voorkeuren) toe aan notification.
	 * @param notification notificatie waaraan flags moeten worden toegevoegd
	 * @require notification != null
	 */
	private void addNotificationParams(Notification notification) {
		// optimalisatie:
		Resources res = this.getResources();
		
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.ledARGB = res.getColor(R.color.notification_flashing_color);
		notification.ledOnMS = res.getInteger(R.integer.notification_flashing_on_ms);
		notification.ledOffMS = res.getInteger(R.integer.notification_flashing_off_ms);
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
	}
	
	
	int nots = 0;

	/* (non-Javadoc)
	 * @see com.google.android.c2dm.C2DMBaseReceiver#onRegistered(android.content.Context, java.lang.String)
	 */
	@Override
	public void onRegistrered(Context context, String registrationId) {
		//super.onRegistrered(context, registrationId);
		this.context = context;
		this.deviceRegistrationID = registrationId;
		//Log.e(TAG, ">>>>id recieved" + registrationId);
		//Log.e(TAG, ">>>>device unique id " + deviceId);
		new registerServer().execute();


	}

	/* (non-Javadoc)
	 * @see com.google.android.c2dm.C2DMBaseReceiver#onUnregistered(android.content.Context)
	 */
	@Override
	public void onUnregistered(Context context) {
		super.onUnregistered(context);
	}


	private class registerServer extends AsyncTask<Void, Void, Void> {  		

		@SuppressWarnings("deprecation")
		@Override  
		protected Void doInBackground(Void... arg0) {
			// send to server
			BufferedReader in = null;
			try {
				HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet();
				String deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
				String get = "?deviceid="+URLEncoder.encode(deviceId)+"&devicetoken="+URLEncoder.encode(deviceRegistrationID).toString();
				request.setURI(new URI(String.format(getString(R.string.url_push_register), get)));

				HttpResponse response = client.execute(request);
				in = new BufferedReader
						(new InputStreamReader(response.getEntity().getContent()));
				StringBuffer sb = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();
				String page = sb.toString();
				System.out.println(page);


			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return null;       
		}
	}

}
