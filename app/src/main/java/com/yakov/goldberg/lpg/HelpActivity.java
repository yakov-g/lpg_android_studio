package com.yakov.goldberg.lpg;

import com.yakov.goldberg.lpg.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		TextView tw = (TextView) findViewById(R.id.helptextView);
		String content_str = getResources().getString(R.string.help_content);
		String ver_str = getResources().getString(R.string.version_string);
		tw.setText(content_str + "\n\n" + ver_str + "\n");

		final ImageButton mybutton = (ImageButton) findViewById(R.id.help_rate_button);
		mybutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String str = "https://play.google.com/store/apps/details?id=com.yakov.goldberg.lpg";
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}
	
	public void help_mail_send_clicked(View view) {
		Intent i = (new Helper()).send_mail_to_developer
				("LPG Israel - mail to developer", "");
		startActivity(Intent.createChooser(i, "Send Email"));
	}
	public void help_facebook_clicked(View view) {
		Intent i = (new Helper()).getOpenFacebookIntent(this);
		startActivity(i);
	}
}
