package com.ut.bataapp.services;

import com.ut.bataapp.*;
import com.ut.bataapp.activities.TeamActivity;
import com.ut.bataapp.api.api;
import com.ut.bataapp.objects.*;

import android.app.*;
import android.content.*;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.*;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.*;

/**
 * Service die op de achtergrond gegevens van favoriete teams binnenhaalt en bij
 * gewijzigde gegevens (eventueel) een notificatie laat zien.
 * Reageert op wijzigingen van gebruikersvoorkeuren.
 * Reageert op wijzigingen in connectiviteit (voor retries).
 * Wanneer update mislukt, wordt regelmatig een nieuwe poging ondernomen.
 * Onderdeel van ontwerpproject BataApp.
 * @author Danny Bergsma
 * @version 0.1
 */
public class BackgroundUpdater extends Service implements OnSharedPreferenceChangeListener {
	// -- INNER CLASSES --
	
	/* Broadcast receiver voor connectivity change action, die kan aangeven dat er weer connectie is met een
	 * netwerk. Wanneer vorige update niet voltooid kon worden (vanwege gebrek aan (goede) internetverbinding),
	 * wordt de update "ingehaald".
	 * Onderdeel van ontwerpproject BataApp.
     * @author Danny Bergsma
     * @version 0.1 
	 */
	private class ConnectivityChange extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (mRetrying && intent != null && !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
				Log.d("bu", "retrying on connectivity change!");
				mHandler.removeMessages(BACKGROUND_UPDATE);
		    	mHandler.sendEmptyMessage(BACKGROUND_UPDATE);
			}
		}	
	}
	
	/* Handler voor schedulen van downloaden gegevens favoriete teams op achtergrond en eventueel tonen
	 * van wijzigingen.
	 * Onderdeel van ontwerpproject BataApp.
     * @author Danny Bergsma
     * @version 0.1
	 */
	private class BackgroundUpdaterHandler extends Handler {
		// -- INNER CLASSES --
		
		/* AsyncTask die gegevens van favoriete teams binnenhaalt en eventueel een notificatie laat zien van de wijzigingen.
		 * Onderdeel van ontwerpproject BataApp.
         * @author Danny Bergsma
         * @version 0.1 
		 */
		private class DownloadXMLWithUpdateNotification extends AsyncTask<Map.Entry<String, ?>, Void, Integer> {			
			// -- CONSTANTEN --
			
			/** scheidingsteken voor etappenummers bij meerdere nieuwe/gewijzigde etappeuitslagen */
			public static final String DELIMITER = ", ";
			/* offset voor alle id's van klassementnotificatices */
			private static final int NOTIFICATION_KLASSEMENT_ID_OFFSET = 1000;
			/* offset voor alle id's van etappeuitslagnotificatices */
			private static final int NOTIFICATION_ETAPPEUITSLAG_ID_OFFSET = 2000;
			
			// -- INSTANTIEVARIABELEN --
			
			/* Notificaties voor klassementsnotering- en etappeuitslagwijziging. Kunnen null zijn, wanneer de
			 * desbetreffede wijziging er niet is. 
			 */
			private Notification mKlassementNotification, mEtappeUitslagNotification;
			
			// -- HULPMETHODEN --
			
			/* Geeft pending intent terug voor starten infoactivity voor team met id als startnummer en tabId als id van de
			 * tab die geopend gaat worden.
			 * @param id startnummer van team
			 * @param tabId id van te openen tab
			 * @return pending intent voor starten infoactivity voor team met id als startnummer en tabId als id van de tab die geopend gaat worden
			 * @require id >= 0
			 * @require tabId == TeamActivity.ETAPPEUITSLAG_TAB || tabId == TeamActivity.KLASSEMENT_TAB
			 * @ensure result != null
			 */
			private PendingIntent getIntentForTeamActivity(int id, int tabId) {
				Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
				intent.putExtra(TeamActivity.ID, id);
				intent.putExtra(TeamActivity.TAB, tabId);
				return PendingIntent.getActivity(BackgroundUpdater.this, 0, intent, 0);
			}
			
			/* Voegt flags (volgens user's voorkeuren) toe aan notification.
			 * @param notification notificatie waaraan flags moeten worden toegevoegd
			 * @require notification != null
			 */
			private void addNotificationParams(Notification notification) {
				// optimalisatie:
				Resources res = mRes;
				SharedPreferences prefs = mPrefs;
				
				if (prefs.getBoolean(res.getString(R.string.pref_background_update_sound), res.getBoolean(R.bool.pref_background_update_sound_default)))
					notification.defaults |= Notification.DEFAULT_SOUND;
				if (prefs.getBoolean(res.getString(R.string.pref_background_update_vibrate), res.getBoolean(R.bool.pref_background_update_vibrate_default)))
					notification.defaults |= Notification.DEFAULT_VIBRATE;
				if (prefs.getBoolean(res.getString(R.string.pref_background_update_flash), res.getBoolean(R.bool.pref_background_update_flash_default))) {
					notification.ledARGB = res.getColor(R.color.notification_flashing_color);
					notification.ledOnMS = res.getInteger(R.integer.notification_flashing_on_ms);
					notification.ledOffMS = res.getInteger(R.integer.notification_flashing_off_ms);
					notification.flags |= Notification.FLAG_SHOW_LIGHTS;
				}
				
				notification.flags |= Notification.FLAG_AUTO_CANCEL;
			}
				
			/* Geeft notificatie terug voor klassementsnoteringwijziging.
			 * @param id startnummer van team
			 * @param naam naam van team
			 * @param klassementsnotering klassementsnotering van team
			 * @param totEtappe tot welke etappe in klassementsnotering is verwerkt
			 * @return notificatie voor klassementsnoteringwijziging
			 * @require id >= 0 && naam != null
			 * @ensure result != null
			 */
			private Notification createKlassementNotification(int id, String naam, int klassementsnotering, int totEtappe) {
				Resources res = mRes;
				
				Notification notification = new Notification(R.drawable.favorieten, String.format(res.getString(R.string.notification_bu_klassement_ticker), naam), System.currentTimeMillis());
				addNotificationParams(notification);
				notification.setLatestEventInfo(BackgroundUpdater.this, naam, String.format(res.getString(R.string.notification_bu_klassement_content), 
						totEtappe, klassementsnotering), getIntentForTeamActivity(id, TeamActivity.KLASSEMENT_TAB));
				return notification;
			}
			
			/* Geeft notificatie terug voor nieuwe/gewijzigde etappeuitslag.
			 * @param id startnummer van team
			 * @param naam naam van team
			 * @param etappe van welke etappe uitslag nieuw/gewijzigd is
			 * @param tijd gelopen tijd tijdens etappe
			 * @return notificatie voor nieuwe/gewijzigde etappeuitslag
			 * @require id >= 0 && naam != null
			 * @ensure result != null
			 */
			private Notification createEtappeUitslagNotification(int id, String naam, int etappe, String tijd) {
				Resources res = mRes;
				Notification notification = new Notification(R.drawable.favorieten, String.format(res.getString(R.string.notification_bu_etappeuitslag_ticker), naam), System.currentTimeMillis());
				addNotificationParams(notification);
				notification.setLatestEventInfo(BackgroundUpdater.this, naam, String.format(res.getString(R.string.notification_bu_etappeuitslag_content), 
						etappe, tijd), getIntentForTeamActivity(id, TeamActivity.ETAPPEUITSLAG_TAB));
				return notification;
			}
			
			/* Geeft notificatie terug voor meeerdere nieuwe/gewijzigde etappeuitslagen.
			 * @param id startnummer van team
			 * @param naam naam van team
			 * @param etappes van welke etappes uitslag nieuw/gewijzigd is
			 * @return notificatie voor nieuwe/gewijzigde etappeuitslag
			 * @require id >= 0 && naam != null && etappes != null && etappes.size() > 0
			 * @ensure result != null
			 */
			private Notification createEtappeUitslagenNotification(int id, String naam, ArrayList<Integer> etappes) {
				Resources res = mRes;
				
				String gewijzigdeEtappes = "" + etappes.get(0);
				for (int i=1; i<etappes.size(); i++)
					gewijzigdeEtappes += (DELIMITER + etappes.get(i));
				Notification notification = new Notification(R.drawable.favorieten, String.format(res.getString(R.string.notification_bu_etappeuitslagen_ticker), naam), System.currentTimeMillis());
				addNotificationParams(notification);
				notification.setLatestEventInfo(BackgroundUpdater.this, naam, String.format(res.getString(R.string.notification_bu_etappeuitslagen_content), 
						gewijzigdeEtappes), getIntentForTeamActivity(id, TeamActivity.ETAPPEUITSLAG_TAB));
				return notification;
			}
			
			/* Geeft looptijd voor etappe met etappeId als etappenummer terug. Kan null zijn, wanneer er in looptijden geen looptijd voor 
			 * deze etappe gevonden kan worden.
			 * @param looptijden lijst van de looptijden van alle etappes
			 * @param etappeId nummer van op te zoeken etappe
			 * @return looptijd voor etappe met etappeId als etappenummer of null wanneer er in looptijden geen looptijd voor deze etappe gevonden kan worden
			 * @require looptijden != null
			 */
			private Looptijd getLooptijd(ArrayList<Looptijd> looptijden, int etappeId) {
				Looptijd result = null;
				int i=0;
				while (result == null && i<looptijden.size()) {
					Looptijd looptijd = looptijden.get(i);
					if (looptijd.getEtappe() == etappeId)
						result = looptijd;
					i++;
				}
				return result;
			}
			
			// -- ASYNCTASK --
			
			@Override
			protected Integer doInBackground(Map.Entry<String, ?>... favoEntry) {
				SharedPreferences prefs = mPrefs;
				Resources res = mRes;
				
				int teamId = Integer.parseInt(favoEntry[0].getKey());
				boolean notifyOnChange = prefs.getBoolean(res.getString(R.string.pref_background_update_notify), res.getBoolean(R.bool.pref_background_update_notify_default));
				
				Team old = null;
				if (notifyOnChange) {
					// parse oude teamuitslag:
					Response<Team> response = api.getTeamByID(teamId, true);
					if (response.getStatus() == Response.OK_NO_UPDATE)
						old = response.getResponse();
				}
					
				Response<Team> response = api.getTeamByID(teamId);
				if (response.getStatus() == Response.NOK_NO_DATA || response.getStatus() == Response.NOK_OLD_DATA)
					scheduleRetry();	
			    else {
			    	scheduleNextUpdate();
				    if (notifyOnChange && response.getStatus() == Response.OK_UPDATE) {
						Team newOne = response.getResponse();
						String teamNaam = newOne.getNaam();
						int klassementsnotering = newOne.getKlassementsnotering(), klassementTotEtappe = newOne.getKlassementTotEtappe();
						if (klassementsnotering != -1 && klassementTotEtappe != 0 && (old == null || klassementsnotering != old.getKlassementsnotering() || klassementTotEtappe != old.getKlassementTotEtappe()))
							mKlassementNotification = createKlassementNotification(teamId, teamNaam, klassementsnotering, klassementTotEtappe);
						if (prefs.getBoolean(res.getString(R.string.pref_background_update_notify_etappeuitslag), res.getBoolean(R.bool.pref_background_update_notify_etappeuitslag_default))) {
							ArrayList<Looptijd> oudeLooptijden = (old == null ? new ArrayList<Looptijd>() : old.getLooptijden()),
									            nieuweLooptijden = response.getResponse().getLooptijden();
							ArrayList<Integer> gewijzigdeEtappes = new ArrayList<Integer>();
							for (Looptijd nieuweLooptijd: nieuweLooptijden) {
								Looptijd oudeLooptijd = getLooptijd(oudeLooptijden, nieuweLooptijd.getEtappe());
								if (oudeLooptijd == null || !nieuweLooptijd.getTijd().equals(oudeLooptijd.getTijd()))
									gewijzigdeEtappes.add(nieuweLooptijd.getEtappe());
							}
							if (gewijzigdeEtappes.size() == 1) {
								Looptijd gewijzigd = getLooptijd(nieuweLooptijden, gewijzigdeEtappes.get(0));
								mEtappeUitslagNotification = createEtappeUitslagNotification(teamId, teamNaam, gewijzigd.getEtappe(), gewijzigd.getTijd());
							} else if (gewijzigdeEtappes.size() > 1)
								mEtappeUitslagNotification = createEtappeUitslagenNotification(teamId, teamNaam, gewijzigdeEtappes);
						}
				    }
				}
				
				return teamId;
		    }
			
			@Override
			protected void onPostExecute(Integer result) {
				if (mKlassementNotification != null)
					mNotificationManager.notify((NOTIFICATION_KLASSEMENT_ID_OFFSET + result), mKlassementNotification);
				if (mEtappeUitslagNotification != null)
					mNotificationManager.notify((NOTIFICATION_ETAPPEUITSLAG_ID_OFFSET + result), mEtappeUitslagNotification);
			}
		}
				
		// -- INSTANTIEVARIABELEN --
		
		/* Manager van status bar notificaties.
		 * @invariant mNotificationManager != null 
		 * */
		private NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					
		// -- HANDLER --
		
		@Override
		public void handleMessage(Message msg) {
			Log.d("bu", "Background update ongoing!");
			mRetrying = false;
			// start AsyncTask voor elk favoriet team:
			SharedPreferences keyValues = getSharedPreferences(mRes.getString(R.string.pref_favorieten), Context.MODE_PRIVATE);
			Map<String, ?> favoteams = keyValues.getAll();
			for (Map.Entry<String, ?> entry: favoteams.entrySet())
				new DownloadXMLWithUpdateNotification().execute(entry);
		}
	}
	
	// -- CONSTANTEN --
	
	/* aantal milliseconden in een minuut */
	private static final int MILLIS_IN_MINUTE = 60000;
	/* id van message voor downloaden op achtergrond */
	private static final int BACKGROUND_UPDATE = 1;
		
	// -- INSTANTIEVARIABELEN --
	
	/* Voorkeuren van gebruiker.
	 * @invariant mPrefs != null
	 */
	private SharedPreferences mPrefs;
	/* Handler voor schedulen downloaden gegevens favoriete teams op achtergrond.
	 * @invariant mHandler != null
	 */
	private Handler mHandler;
	/* Referentie naar resources van app.
	 * @invariant mRes != null
	 */
	private Resources mRes;
	/* geeft aan of vorige update mislukt is, dus dat daarom nu wordt geprobeerd om update in te halen */
	private boolean mRetrying;
	/* Broadcast receiver voor connectivity changes: wanneer is geconnect naar nieuw netwerk, kan eventuele
	 * mislukte update worden ingehaald.
	 * @invariant mConnectivityChange != null 
	 */
	private BroadcastReceiver mConnectivityChange;
	
	// -- HULPMETHODEN --
	
	/* Schedulet de volgende (reguliere) update. Wanneer Bata al geweest is of gebruiker geen background update wenst, wordt niets gescheduled. 
	 * Wanneer Bata vandaag is, wordt het standaard updateinterval gebruikt.
	 * Wanneer de Batadag nog moet komen, wordt de volgende update zo gescheduled dat de volgende update plaatsvindt om middernacht
	 * op de Batadag.
	 */
	private synchronized void scheduleNextUpdate() {
		if (mPrefs.getBoolean(mRes.getString(R.string.pref_background_update), mRes.getBoolean(R.bool.pref_background_update_default))) { 
			Calendar bataDag = Calendar.getInstance();
			bataDag.set(mRes.getInteger(R.integer.batadag_jaar), mRes.getInteger(R.integer.batadag_maand)-1, mRes.getInteger(R.integer.batadag_dag));
			Calendar today = Calendar.getInstance();
			Utils.clearTime(today);
		    int diff = today.compareTo(bataDag);
		    if (diff <= 0) {
		    	long interval = Long.MIN_VALUE;
		    	/*if (diff < 0)
		    		interval = bataDag.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();*/
		    	interval = Math.max(interval, mPrefs.getInt(mRes.getString(R.string.pref_background_update_interval), mRes.getInteger(R.integer.pref_background_update_interval_default)) * MILLIS_IN_MINUTE);
		    	mHandler.removeMessages(BACKGROUND_UPDATE);
		    	mHandler.sendEmptyMessageDelayed(BACKGROUND_UPDATE, interval);
		    	Log.d("bu", "Next background update scheduled at " + interval + "ms");
		    }
		}
	}
	
	/* Schedulet de retry update. Wanneer gebruik geen background update (meer) wenst, wordt niets gescheduled.
	 * In het andere geval wordt de retry update gescheduled volgens het retry interval.
	 */
	private synchronized void scheduleRetry() {
		if (mPrefs.getBoolean(mRes.getString(R.string.pref_background_update), mRes.getBoolean(R.bool.pref_background_update_default))) {
			Log.d("bu", "Next background update (retry) scheduled at " + mRes.getInteger(R.integer.pref_background_update_retry_on_failure) * MILLIS_IN_MINUTE + "ms");
			mRetrying = true;
			mHandler.removeMessages(BACKGROUND_UPDATE);
			mHandler.sendEmptyMessageDelayed(BACKGROUND_UPDATE, mRes.getInteger(R.integer.pref_background_update_retry_on_failure) * MILLIS_IN_MINUTE);
		}
	}
	
	// -- LIFECYCLEMETHODEN --
	
	@Override
	public void onCreate() {
		mRes = getResources();
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mPrefs.registerOnSharedPreferenceChangeListener(this);
		mHandler = new BackgroundUpdaterHandler();
		mRetrying = false;
		registerReceiver(mConnectivityChange = new ConnectivityChange(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		scheduleNextUpdate();
	  }

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY; // herstart service wanneer service door Android wordt gedestroyed
	}
	
	@Override
	public void onDestroy() {
		mPrefs.unregisterOnSharedPreferenceChangeListener(this);
		unregisterReceiver(mConnectivityChange);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	// -- ONSHAREDPREFERENCECHANGELISTENER -- 
	
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) { // gebruikersvoorkeuren zijn gewijzigd
		if (key.equals(mRes.getString(R.string.pref_background_update_interval)) || key.equals(mRes.getString(R.string.pref_background_update)))
			scheduleNextUpdate();
	}
}