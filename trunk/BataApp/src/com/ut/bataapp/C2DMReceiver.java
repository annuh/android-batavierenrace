package com.ut.bataapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.c2dm.C2DMBaseReceiver;
import com.google.android.c2dm.C2DMessaging;

// TODO: Auto-generated Javadoc
/**
 * Broadcast receiver that handles Android Cloud to Data Messaging (AC2DM)
 * messages, initiated by the JumpNote App Engine server and routed/delivered by
 * Google AC2DM servers. The only currently defined message is 'sync'.
 */
public class C2DMReceiver extends C2DMBaseReceiver {

	/** The Constant TAG. */
	static final String TAG = C2DMConfig.makeLogTag(C2DMReceiver.class);

	/**
	 * Instantiates a new c2 dm receiver.
	 */
	public C2DMReceiver() {
		super( C2DMConfig.C2DM_GOOGLE_ACCOUNT);
	}

	/* (non-Javadoc)
	 * @see com.google.android.c2dm.C2DMBaseReceiver#onError(android.content.Context, java.lang.String)
	 */
	@Override
	public void onError(Context context, String errorId) {
		Toast.makeText(context, "Messaging registration error: " + errorId,
				Toast.LENGTH_LONG).show();
	}

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onHandleIntentRecieved(context, intent);
	}

	/* (non-Javadoc)
	 * @see com.google.android.c2dm.C2DMBaseReceiver#onMessage(android.content.Context, android.content.Intent)
	 */
	@Override
	protected void onMessage(Context context, Intent intent) {
		String message = intent.getExtras().getString("message");
		String strTitle = intent.getExtras().getString("title");
		if (message != null) {
			//write code to do something with your msg
		}
	}

	/* (non-Javadoc)
	 * @see com.google.android.c2dm.C2DMBaseReceiver#onRegistered(android.content.Context, java.lang.String)
	 */
	@Override
	public void onRegistered(Context context, String registrationId)
			throws IOException {
		
		
		// TODO
		super.onRegistered(context, registrationId);
		Log.d("C2DM-MESSAGING", "registeren2");
		Log.e(TAG, ">>>>id recieved" + registrationId);
		String deviceId = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		//TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

		Log.e(TAG, ">>>>device unique id " + deviceId);
		// send to server
		BufferedReader in = null;
		try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            try {
				request.setURI(new URI("http://batabericht.eu5.org/push_register.php?deviceid="+URLEncoder.encode(deviceId)+"&devicetoken="+URLEncoder.encode(registrationId).toString()));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
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
            } finally {
            if (in != null) {
                try {
                    in.close();
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

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
}
