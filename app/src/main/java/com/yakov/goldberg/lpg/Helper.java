package com.yakov.goldberg.lpg;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
		String facebookUrl = "https://www.facebook.com/LPGIsrael";
		try {
			int versionCode = context.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
			if (versionCode >= 3002850) {
				Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
				return  new Intent(Intent.ACTION_VIEW, uri);
			} else {
				// open the Facebook app using the old method (fb://profile/id or fb://page/id)
				return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/149755558551918"));
			}
		} catch (PackageManager.NameNotFoundException e) {
			// Facebook is not installed. Open the browser
			return (new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
		}
	}

}