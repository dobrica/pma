package com.example.pma.ereader.ui.settings;

import com.example.pma.ereader.MyApplication;
import com.example.pma.ereader.R;
import com.example.pma.ereader.ReadingActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class SettingsFragment extends Fragment {

	private SettingsViewModel settingsViewModel;
	private SettingsChanged settingsChanged;
	private boolean success = false;

	public View onCreateView(@NonNull LayoutInflater inflater,
		ViewGroup container, Bundle savedInstanceState) {
		settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
		View root = inflater.inflate(R.layout.fragment_settings, container, false);
		SeekBar fontSeekBar = root.findViewById(R.id.fontSeekBar);
		SeekBar brightnessBar = root.findViewById(R.id.brightness);
		fontSeekBar.setProgress(12);
		fontSeekBar.incrementProgressBy(1);
		fontSeekBar.setMax(36);
		brightnessBar.setMax(255);
		brightnessBar.setProgress(getBrightness());
		if (getActivity() instanceof ReadingActivity) {
			this.settingsChanged = (ReadingActivity) getActivity();
			getPermission();
		} else {
			fontSeekBar.setProgress(0);
			fontSeekBar.setEnabled(false);
		}
		fontSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (settingsChanged != null) {
					settingsChanged.fontChanged(progress);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser && success) {
					setBrightness(progress);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
		return root;
	}

	private void setBrightness(int brightness) {
		if (brightness < 0) {
			brightness = 0;
		} else if (brightness > 255) {
			brightness = 255;
		}
		ContentResolver resolver = MyApplication.getAppContext().getContentResolver();
		Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
	}

	private int getBrightness() {
		int brightness = 100;
		try {
			ContentResolver resolver = MyApplication.getAppContext().getContentResolver();
			Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
		} catch (Settings.SettingNotFoundException e) {
			e.printStackTrace();
		}
		return brightness;
	}

	private void getPermission() {
		boolean value;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			value = Settings.System.canWrite(MyApplication.getAppContext());
			if (value) {
				success = true;
			} else {
				Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
				startActivityForResult(intent, 1000);
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		boolean value;
		if (requestCode == 1000) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				value = Settings.System.canWrite(MyApplication.getAppContext());
				if (value) {
					success = true;
				} else {
					Toast.makeText(getActivity(), "Permission not granted", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
