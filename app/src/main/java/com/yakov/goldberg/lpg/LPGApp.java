package com.yakov.goldberg.lpg;

import java.util.HashMap;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import android.app.Application;

public class LPGApp extends Application {
	
	String updated_prices;
	
	@Override
	public void onCreate(){
		updated_prices = "";
	}
	// The following line should be changed to include the correct property id.
		private static final String PROPERTY_ID = "UA-46017478-1";
		public static int GENERAL_TRACKER = 0;

		public enum TrackerName {
			APP_TRACKER, // Tracker used only in this app.
			GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
			ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
		}

		HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

		public LPGApp() {
			super();
		}
/*
		synchronized Tracker getTracker(TrackerName trackerId) {
			if (!mTrackers.containsKey(trackerId)) {
				GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
				Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics
						.newTracker(R.xml.app_tracker)
						: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics
								.newTracker(PROPERTY_ID) : null;
				mTrackers.put(trackerId, t);
			}
			return mTrackers.get(trackerId);
		}
		*/
		
		private Tracker mTracker;
		  public synchronized Tracker getAppTracker() {
		    if (mTracker == null) {
		      GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
		      mTracker = analytics.newTracker(PROPERTY_ID); // Replace with your real tracker id
		      mTracker.enableAutoActivityTracking(true);
		      mTracker.enableExceptionReporting(true);
		      mTracker.setSessionTimeout(-1);
		      mTracker.setScreenName("LPG Israel (APP TRACKER2)");
		      // mTracker.setSampleRate(100.0d); // Not needed. The default sampling rate it 100%
		    }
		    return mTracker;
		  }
}
