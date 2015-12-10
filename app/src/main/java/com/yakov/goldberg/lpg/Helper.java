package com.yakov.goldberg.lpg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Helper {

	public Intent send_mail_to_developer(String subject, String body) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"yakov.goldberg@gmail.com"});
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, body);
		return intent;
	}
	public Intent getOpenFacebookIntent(Context context) {
	    try {
	    	//Checks if FB is even installed.
	        context.getPackageManager()
	                .getPackageInfo("com.facebook.katana", 0);
	      //Trys to make intent with FB's URI
	        return new Intent(Intent.ACTION_VIEW,
	                Uri.parse("fb://profile/149755558551918"));
	    }
	    //catches and opens a url to the desired page
	    catch (Exception e) {
	        return new Intent(Intent.ACTION_VIEW,
	                Uri.parse("https://www.facebook.com/LPGIsrael"));
	    }
	}

}
