package de.ub0r.android.andGMXsms;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Simple Activity for setting preferences.
 * 
 * @author flx
 */
public class Settings extends Activity {
	/** Local pref. for user. */
	private static String prUser;
	/** Local pref. for user's password. */
	private static String prPassword;
	/** Local pref. for user's phonenumber. */
	private static String prSender;

	/** Dialog: bootstrap. */
	private static final int DIALOG_BOOTSTRAP = 0;

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            default param
	 */
	@Override
	public final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// inflate XML
		this.setContentView(R.layout.settings);
		// register Listener
		Button button = (Button) this.findViewById(R.id.help);
		button.setOnClickListener(this.help);
		button = (Button) this.findViewById(R.id.ok);
		button.setOnClickListener(this.ok);
		button = (Button) this.findViewById(R.id.cancel);
		button.setOnClickListener(this.cancel);
		button = (Button) this.findViewById(R.id.bootstrap);
		button.setOnClickListener(this.bootstrap);
	}

	/** Called on activity resume. */
	@Override
	public final void onResume() {
		super.onResume();
		// load global prefs if local prefs are empty
		if (prUser == null) {
			prUser = AndGMXsms.prefsUser;
		}
		if (prPassword == null) {
			prPassword = AndGMXsms.prefsPassword;
		}
		if (prSender == null) {
			prSender = AndGMXsms.prefsSender;
		}

		// reload EditTexts' text from local prefs
		EditText et = (EditText) this.findViewById(R.id.user);
		et.setText(prUser);
		et = (EditText) this.findViewById(R.id.password);
		et.setText(prPassword);
		et = (EditText) this.findViewById(R.id.sender);
		et.setText(prSender);
	}

	/** Called on activity pause. */
	@Override
	public final void onPause() {
		super.onPause();
		// save TextEdits' text to local prefs
		EditText et = (EditText) this.findViewById(R.id.user);
		prUser = et.getText().toString();
		et = (EditText) this.findViewById(R.id.password);
		prPassword = et.getText().toString();
		et = (EditText) this.findViewById(R.id.sender);
		prSender = et.getText().toString();
	}

	/**
	 * Called to create dialog.
	 * 
	 * @param id
	 *            Dialog id
	 * @return dialog
	 */
	@Override
	protected final Dialog onCreateDialog(final int id) {
		Dialog dialog;
		switch (id) {
		case DIALOG_BOOTSTRAP:
			dialog = new Dialog(this);
			dialog.setContentView(R.layout.bootstrap);
			dialog.setTitle(this.getResources().getString(R.string.bootstrap_));
			break;
		default:
			dialog = null;
		}
		return dialog;
	}

	/** OnClickListener for launching 'help'. */
	private OnClickListener help = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			Settings.this.startActivity(new Intent(Settings.this, Help.class));
		}
	};

	/** OnClickListener listening for 'ok'. */
	private OnClickListener ok = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			// save prefs from TextEdits
			EditText et = (EditText) Settings.this.findViewById(R.id.user);
			prUser = et.getText().toString();
			et = (EditText) Settings.this.findViewById(R.id.password);
			prPassword = et.getText().toString();
			et = (EditText) Settings.this.findViewById(R.id.sender);
			prSender = et.getText().toString();

			// save prefs to global
			AndGMXsms.prefsUser = prUser;
			AndGMXsms.prefsPassword = prPassword;
			AndGMXsms.prefsSender = prSender;
			// save prefs
			AndGMXsms.me.saveSettings();
			// exit activity
			Settings.this.finish();
		}
	};

	/** OnClickListener listening for 'cancel'. */
	private OnClickListener cancel = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			// reload prefs from global
			prUser = AndGMXsms.prefsUser;
			prPassword = AndGMXsms.prefsPassword;
			prSender = AndGMXsms.prefsSender;

			// reload prefs into TextEdits
			EditText et = (EditText) Settings.this.findViewById(R.id.user);
			et.setText(prUser);
			et = (EditText) Settings.this.findViewById(R.id.password);
			et.setText(prPassword);
			et = (EditText) Settings.this.findViewById(R.id.sender);
			et.setText(prSender);
			// exit activity
			Settings.this.finish();
		}
	};

	/** OnClickListener listening for 'bootstrap'. */
	private OnClickListener bootstrap = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			Settings.this.showDialog(DIALOG_BOOTSTRAP);
		}

	};
}
