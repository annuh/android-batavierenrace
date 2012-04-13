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
import android.os.AsyncTask;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;
import com.google.android.c2dm.C2DMBaseReceiver;
import com.ut.bataapp.R;
import com.ut.bataapp.activities.BerichtenActivity;
import com.ut.bataapp.activities.WeerActivity;
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
			
			Utils.addBericht(getApplicationContext(), type+message);
			
			makeNotification(context, type, message);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void makeNotification(Context context, String type, String message) {
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.icon, "Message received", System.currentTimeMillis());
		
		// Hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.number += 1;
		Intent tointent = new Intent(context, BerichtenActivity.class);
		if(type.equals("w")) {
			tointent = new Intent(context, WeerActivity.class);
			type = type + " Weer-alert";
		}
		
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				tointent, 0);
		notification.setLatestEventInfo(context, "Batavierenrace "+type,
				message, pendingIntent);
		notificationManager.notify(0, notification);
	}

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

	/**
	 * Register or unregister based on phone sync settings. Called on each
	 * performSync by the SyncAdapter.
	 *
	 * @param context the context
	 * @param autoSyncDesired the auto sync desired
	 */
	/*
	public static void refreshAppC2DMRegistrationState(Context context,
			boolean register) {
		// Determine if there are any auto-syncable accounts. If there are, make
		// sure we are
		// registered with the C2DM servers. If not, unregister the application.

		if (Build.VERSION.SDK_INT < 8) {
			return;
		} else {

			if (register) {
				C2DMessaging.register(context, C2DMConfig.C2DM_GOOGLE_ACCOUNT);
			} else {
				C2DMessaging.unregister(context);
			}

		}
	}
	 */	
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
				request.setURI(new URI("http://batabericht.eu5.org/push_register.php?deviceid="+URLEncoder.encode(deviceId)+"&devicetoken="+URLEncoder.encode(deviceRegistrationID).toString()));

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
