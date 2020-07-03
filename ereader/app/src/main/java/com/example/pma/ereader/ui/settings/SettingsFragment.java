package com.example.pma.ereader.ui.settings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pma.ereader.R;
import com.example.pma.ereader.ReadingActivity;

public class SettingsFragment extends Fragment {

	private SettingsViewModel settingsViewModel;
	private SettingsChanged settingsChanged;

	public View onCreateView(@NonNull LayoutInflater inflater,
		ViewGroup container, Bundle savedInstanceState) {
		settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
		View root = inflater.inflate(R.layout.fragment_settings, container, false);
		SeekBar fontSeekBar = root.findViewById(R.id.fontSeekBar);
		fontSeekBar.setProgress(12);
		fontSeekBar.incrementProgressBy(1);
		fontSeekBar.setMax(36);
		if (getActivity() instanceof ReadingActivity) {
			this.settingsChanged = (ReadingActivity) getActivity();
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
		return root;
	}

}
