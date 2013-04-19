package com.askey.testing.walmart;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WalmartIntentTestActivity extends PreferenceActivity implements
		Preference.OnPreferenceChangeListener {
	private static final String TAG 					   = "WalmartIntentTestActivity";
	private static final String KEY_DNS_SUFFIX 			   = "dns_suffix";
	private static final String KEY_BOOTUP_WITH_BATTERY    = "Bootup_With_Battery";
	private static final String KEY_WAKEUP_SOURCE_KEYPAD   = "wakeup_sources_Keypad";
	private static final String KEY_WAKEUP_SOURCE_GSENSOR  = "wakeup_sources_Gsensor";
	private static final String KEY_WAKEUP_SOURCE_BT 	   = "wakeup_sources_Bt";
	private static final String KEY_INPUT_METHOD 		   = "input_method";
	private static final String KEY_HOME_KEY 			   = "home_key";
	private static final String KEY_PROXY_WIFI			   = "proxy_setting";
	private static final String KEY_NOTIFY_OPEN_NETWORKS   = "notify_open_networks";
	private static final String KEY_USER_CLASS   		   = "user_class";
	private static final String KEY_FREQUENCY_BAND 		   = "frequency_band";
	private static final String KEY_SLEEP_POLICY 		   = "sleep_policy";
	private static final String KEY_POOR_NETWORK_DETECTION = "avoid_poor_connections";
	private static final String KEY_LOCALE 				   = "locale";
	private static final String KEY_SDCARD 				   = "mountsdcard";
	private static final String KEY_ADVANCE 			   = "sentintent";
	
	private static final String BOOTUP_WITH_BATTERY_ON 	   = "BOOTUP_WITH_BATTERY_ON";
	private static final String BOOTUP_WITH_BATTERY_OFF    = "BOOTUP_WITH_BATTERY_OFF";
	private static final String DHCPFOLDER   			   = "/data/misc/dhcp/dhcpcd.conf";
	private static final String ADVANCE 				   = "com.motorolasolutions.intent.AdvancedWifiSettings";
	private static final String NOTIFY 					   = "NotifyOpenNetworks";
	private static final String FREQUENCY 				   = "Frequency";
	private static final String POORNETWORK 		       = "PoorNetworkDetection";
	private static final String SLEEP 					   = "SleepPolicy";
	private static final String PROXYSETTING 		       = "com.motorolasolutions.intent.HttpProxy";
	private static final String HOSTNAME				   = "hostname";
	private static final String PORT					   = "port";
	private static final String EXCLUSIONLIST	           = "exclusionList";
	private static final String USERCLASS		           = "Set User Class";
	private static final String USERCLASSINTENT		       = "com.motorolasolutions.intent.DHCP_OPTION_77";
		
	private ListPreference mBatterySettingPref;
	private ListPreference mWakeupSourcesKeypadPref;
	private ListPreference mWakeupSourcesGsensorPref;
	private ListPreference mWakeupSourcesBtPref;
	private ListPreference mInputMethodPref;
	private ListPreference mHomeKeyPref;
	private ListPreference mSleepPolicyPref;
	private ListPreference mFrequencyPref;
	private ListPreference mLocalePref;
	private ListPreference mSDCardPref;
	
	private PreferenceScreen mProxyPrefScr;
	private PreferenceScreen mDnsSuffixScr;
	private PreferenceScreen mUserClassScr;
	private PreferenceScreen mAdvancePrefScr;
	
	private CheckBoxPreference mNotifyOpenNetworks;
	private CheckBoxPreference mPoorNetworkDetection;
	
	private Toast mToast;

	private EditText mFirEdt;
	private EditText mSecEdt;
	private EditText mThrEdt;
	private EditText mHostTxt;
	private EditText mPortTxt;
	private EditText mExclusionListTxt;

	private Button mSaveBtn;
	private Button mClearBtn;
	private Button mResetBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);

		mToast = Toast.makeText(this, "", Toast.LENGTH_LONG);

		setViewComponent();
		setOnClickListener();
	}

	private void setViewComponent() {
		mBatterySettingPref 	  = (ListPreference) findPreference(KEY_BOOTUP_WITH_BATTERY);
		mWakeupSourcesKeypadPref  = (ListPreference) findPreference(KEY_WAKEUP_SOURCE_KEYPAD);
		mWakeupSourcesGsensorPref = (ListPreference) findPreference(KEY_WAKEUP_SOURCE_GSENSOR);
		mWakeupSourcesBtPref 	  = (ListPreference) findPreference(KEY_WAKEUP_SOURCE_BT);
		mInputMethodPref 		  = (ListPreference) findPreference(KEY_INPUT_METHOD);
		mHomeKeyPref 			  = (ListPreference) findPreference(KEY_HOME_KEY);
		mSleepPolicyPref          = (ListPreference) findPreference(KEY_SLEEP_POLICY);
		mFrequencyPref 		      = (ListPreference) findPreference(KEY_FREQUENCY_BAND);
		mLocalePref 		      = (ListPreference) findPreference(KEY_LOCALE);
		mSDCardPref				  = (ListPreference) findPreference(KEY_SDCARD);
		
		mNotifyOpenNetworks 	  = (CheckBoxPreference) findPreference(KEY_NOTIFY_OPEN_NETWORKS);
		mPoorNetworkDetection     = (CheckBoxPreference) findPreference(KEY_POOR_NETWORK_DETECTION);
		
		mProxyPrefScr             = (PreferenceScreen) findPreference(KEY_PROXY_WIFI);
		mDnsSuffixScr			  = (PreferenceScreen) findPreference(KEY_DNS_SUFFIX);
		mUserClassScr			  = (PreferenceScreen) findPreference(KEY_USER_CLASS);
		mAdvancePrefScr			  = (PreferenceScreen) findPreference(KEY_ADVANCE);
	}

	private void setOnClickListener() {
		mBatterySettingPref		 .setOnPreferenceChangeListener(this);
		mWakeupSourcesKeypadPref .setOnPreferenceChangeListener(this);
		mWakeupSourcesGsensorPref.setOnPreferenceChangeListener(this);
		mWakeupSourcesBtPref	 .setOnPreferenceChangeListener(this);
		mInputMethodPref		 .setOnPreferenceChangeListener(this);
		mHomeKeyPref			 .setOnPreferenceChangeListener(this);
		mSleepPolicyPref 	 	 .setOnPreferenceChangeListener(this);
		mNotifyOpenNetworks  	 .setOnPreferenceChangeListener(this);
		mFrequencyPref		 	 .setOnPreferenceChangeListener(this);
		mPoorNetworkDetection	 .setOnPreferenceChangeListener(this);
		mLocalePref				 .setOnPreferenceChangeListener(this);
		mSDCardPref				 .setOnPreferenceChangeListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mToast != null) {
			mToast.cancel();
			mToast = null;
		}
	}
	
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		if (preference== mProxyPrefScr) {
			ShowProxyDialog();
		}else if(preference== mDnsSuffixScr){
			if (enterCommand("DNS Suffix").equals("no data")) {
				ShowDataDialog("DNS Suffix", "WIFI connect success first");
			} else {
				ShowDataDialog("DNS Suffix", enterCommand("DNS Suffix"));
			} 		
		}else if(preference== mUserClassScr){
			ShowAddValueDialog(USERCLASS);
		}else if(preference== mAdvancePrefScr){
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			Intent i = new Intent(ADVANCE);
			i.putExtra(NOTIFY,settings.getInt(NOTIFY, 0));
			i.putExtra(SLEEP, settings.getInt(SLEEP, 0));
			i.putExtra(POORNETWORK, settings.getInt(POORNETWORK, 0));
			i.putExtra(FREQUENCY, settings.getInt(FREQUENCY, 0));
			sendBroadcast(i);
			
			showToast("notify: " + settings.getInt(NOTIFY, 0) + "\n"
					+ "Keep: " + settings.getInt(SLEEP, 0)+"\n"
					+ "Avoid: " + settings.getInt(POORNETWORK, 0)+"\n"
					+ "Wi-Fi frequency band: " +  settings.getInt(FREQUENCY, 0)+"\n");
			
		}
			
		return true;
	}

	private void ShowProxyDialog() {
		LayoutInflater factory = LayoutInflater.from(this);
		View textEntryView = factory.inflate(R.layout.proxysetting, null);
		findViewsDialog(textEntryView);
		Builder MyAlertDialog = new AlertDialog.Builder(this);
		MyAlertDialog.setView(textEntryView);
		MyAlertDialog.setTitle(R.string.Proxy_Setting);
		
		final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		mHostTxt.setText(settings.getString(HOSTNAME, ""));
		mPortTxt.setText(settings.getString(PORT, ""));
		mExclusionListTxt.setText(settings.getString(EXCLUSIONLIST, ""));
		
		mSaveBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mHostTxt.getText().toString().equals("") || mPortTxt.getText().toString().equals("")
						|| mExclusionListTxt.getText().toString().equals("")) {
					showToast("hostname or port or exclusionList must have value");
				} else {
					Intent i = new Intent(PROXYSETTING);
					i.putExtra(HOSTNAME, mHostTxt.getText().toString());
					i.putExtra(PORT,Integer.valueOf(mPortTxt.getText().toString()));
					i.putExtra(EXCLUSIONLIST,mExclusionListTxt.getText().toString());
					sendBroadcast(i);
					showToast("hostname:"+mHostTxt.getText().toString()+"\n"+"port:"+Integer.valueOf(mPortTxt.getText().toString())+"\n"+
							"exclusionList:"+mExclusionListTxt.getText().toString());
				}
			}
		});
		
		mClearBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mHostTxt.setText("");
				mPortTxt.setText("");
				mExclusionListTxt.setText("");
			}
		});
		
		mResetBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mHostTxt.setText("");
				mPortTxt.setText("");
				mExclusionListTxt.setText("");
				Intent i = new Intent(PROXYSETTING);
				i.putExtra(HOSTNAME, "");
				i.putExtra(PORT, "");
				i.putExtra(EXCLUSIONLIST, "");
				sendBroadcast(i);
				showToast("Reset the Default Proxy settings");
			}
		});
			
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (mHostTxt != null && mPortTxt != null && mExclusionListTxt != null) {
					settings.edit().putString(HOSTNAME, mHostTxt.getText().toString()).commit();
					settings.edit().putString(PORT, mPortTxt.getText().toString()).commit();
					settings.edit().putString(EXCLUSIONLIST, mExclusionListTxt.getText().toString()).commit();		
				}
			}
		};
		
		MyAlertDialog.setPositiveButton("OK", OkClick);
		MyAlertDialog.show();
	}

	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (newValue.toString().equals(BOOTUP_WITH_BATTERY_ON)) {
			sendAutoBootIntent(true);
		} else if (newValue.toString().equals(BOOTUP_WITH_BATTERY_OFF)) {
			sendAutoBootIntent(false);
		} else if (preference == mNotifyOpenNetworks) {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			if (newValue.toString().equals("true")) {
				settings.edit().putInt(NOTIFY, 1).commit();
			} else {
				settings.edit().putInt(NOTIFY, 0).commit();
			}
		}else if (preference == mSleepPolicyPref) {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			String stringValue = (String) newValue;
			settings.edit().putInt(SLEEP, Integer.valueOf(stringValue)).commit();
		}else if (preference == mPoorNetworkDetection) {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			if (newValue.toString().equals("true")) {
				settings.edit().putInt(POORNETWORK, 1).commit();
			} else {
				settings.edit().putInt(POORNETWORK, 0).commit();
			}
		}else if (preference == mFrequencyPref) {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			String stringValue = (String) newValue;
			settings.edit().putInt(FREQUENCY, Integer.valueOf(stringValue)).commit();
		}else {
			Intent intent = new Intent();
			showToast("Sending intent:\n" + newValue.toString());
			intent.setAction(newValue.toString());
			sendBroadcast(intent);
		}
		return true;
	}

	private final void sendAutoBootIntent(boolean bootmode){
        Intent intent = new Intent("BOOTUP_WITH_BATTERY");
        intent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY
                | Intent.FLAG_RECEIVER_REPLACE_PENDING);
        intent.putExtra("AB_STATE", bootmode);
        showToast("intent: BOOTUP_WITH_BATTERY\n"+"name: AB_STATE\n"+"key:"+bootmode);
        sendBroadcast(intent);
    }

	private void ShowAddValueDialog(final String title) {
		LayoutInflater factory = LayoutInflater.from(this);
		View textEntryView = factory.inflate(R.layout.dialog, null);
		findViewsDialog(textEntryView);
		Builder MyAlertDialog = new AlertDialog.Builder(this);
		MyAlertDialog.setView(textEntryView);
		MyAlertDialog.setTitle(title);
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(USERCLASSINTENT);
				intent.putExtra(USERCLASSINTENT, getValue());
				sendBroadcast(intent);
				clearEdt();
				try {
					Thread.sleep(4000);
				} catch (Exception e) {
				}
				File f=new File(DHCPFOLDER);
				if(!f.exists()){
				  ShowDataDialog("dhcpcd.conf","WIFI connect success first");
				}else{
				  ShowDataDialog("dhcpcd.conf",enterCommand(title));
				}
			}
		};
		MyAlertDialog.setPositiveButton("OK", OkClick);
		MyAlertDialog.show();
	}

	private void ShowDataDialog(String title, String data) {
		Builder MyAlertDialog = new AlertDialog.Builder(this);
		MyAlertDialog.setTitle(title);
		MyAlertDialog.setMessage(data);
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		};
		MyAlertDialog.setPositiveButton("OK", OkClick);
		MyAlertDialog.show();
	}

	private void clearEdt() {
		mFirEdt.setText("");
		mSecEdt.setText("");
		mThrEdt.setText("");
	}

	private void findViewsDialog(View textEntryView) {
		mFirEdt = (EditText) textEntryView.findViewById(R.id.id1);
		mSecEdt = (EditText) textEntryView.findViewById(R.id.id2);
		mThrEdt = (EditText) textEntryView.findViewById(R.id.id3);
		
		mSaveBtn = (Button) textEntryView.findViewById(R.id.Save);
		mClearBtn = (Button) textEntryView.findViewById(R.id.Clear);
		mResetBtn = (Button) textEntryView.findViewById(R.id.Reset);
		
		mHostTxt = (EditText) textEntryView.findViewById(R.id.host);
		mPortTxt = (EditText) textEntryView.findViewById(R.id.port);
		mExclusionListTxt = (EditText) textEntryView.findViewById(R.id.exclusionList);
		
		mToast = Toast.makeText(this, "", Toast.LENGTH_LONG);
		
		
	}

	private ArrayList getValue() {
		ArrayList al = new ArrayList();
		if (!(mFirEdt.getEditableText().toString().equals(""))) {
			String s = mFirEdt.getEditableText().toString();
			al.add(s);
		}
		if (!(mSecEdt.getEditableText().toString().equals(""))) {
			String s = mSecEdt.getEditableText().toString();
			al.add(s);
		}
		if (!(mThrEdt.getEditableText().toString().equals(""))) {
			String s = mThrEdt.getEditableText().toString();
			al.add(s);
		}
		return al;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "About").setIcon(android.R.drawable.ic_menu_info_details);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case 0:
			openOptionsDialog();
			break;
		}
		return true;
	}

	private void openOptionsDialog() {
		new AlertDialog.Builder(this).setTitle("About tool")
				.setIcon(android.R.drawable.ic_lock_idle_low_battery)
				.setMessage(R.string.alert_dialog_two_buttons2_msg)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	private String enterCommand(String s) {
		Process pr=null;
		BufferedReader br=null;
		StringBuilder sb = new StringBuilder();
		try {
			if(s.equals(USERCLASS)){
			   pr = Runtime.getRuntime().exec("cat /data/misc/dhcp/dhcpcd.conf");
			}else{
			   pr = Runtime.getRuntime().exec("getprop net.dns.search");
			}
			br = new BufferedReader(new InputStreamReader(pr.getInputStream()), 1024);
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (UnknownHostException e) {
			Log.i(TAG, "Ping test Fail: Unknown Host");
		} catch (IOException e) {
			Log.i(TAG, "Ping test Fail:  IOException");
		} finally {
			if (pr != null) {
				pr.destroy();
				pr = null;
			}
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					Log.e(TAG, "error closing stream");
				}
			}
		}
		if (sb.toString().equals(""+"\n")) {
			return "no data";
		}else{	
		    return sb.toString();
		}		
	}

	private void showToast(String s) {
		mToast.setText(s);
		mToast.show();
	}
}