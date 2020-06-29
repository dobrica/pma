package com.example.pma.ereader;

import com.example.pma.ereader.ui.register.RegisterFormState;
import com.example.pma.ereader.ui.register.RegisterResult;
import com.example.pma.ereader.ui.register.RegisterViewModel;
import com.example.pma.ereader.ui.register.RegisterViewModelFactory;
import com.example.pma.ereader.ui.register.RegisteredUserView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class RegisterActivity extends AppCompatActivity {

	private RegisterViewModel registerViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		final EditText fullNameEditText = findViewById(R.id.full_name);
		final EditText usernameEditText = findViewById(R.id.email_address);
		final EditText passwordEditText = findViewById(R.id.reg_password);
		final EditText repeatPasswordEditText = findViewById(R.id.repeat_password);
		final Button registerButton = findViewById(R.id.register2);
		registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory()).get(RegisterViewModel.class);

		registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
			@Override
			public void onChanged(@Nullable RegisterFormState registerFormState) {
				if (registerFormState == null) {
					return;
				}
				registerButton.setEnabled(registerFormState.isDataValid());
				if (registerFormState.getFullNameError() != null) {
					fullNameEditText.setError(getString(registerFormState.getFullNameError()));
				}
				if (registerFormState.getUsernameError() != null) {
					usernameEditText.setError(getString(registerFormState.getUsernameError()));
				}
				if (registerFormState.getPasswordError() != null) {
					passwordEditText.setError(getString(registerFormState.getPasswordError()));
				}
				if (registerFormState.getPasswordMatchError() != null) {
					repeatPasswordEditText.setError(getString(registerFormState.getPasswordMatchError()));
				}
			}
		});

		registerViewModel.getRegisterResult().observe(this, new Observer<RegisterResult>() {
			@Override
			public void onChanged(@Nullable RegisterResult registerResult) {
				if (registerResult == null) {
					return;
				}
				if (registerResult.getError() != null) {
					showRegistrationFailed(registerResult.getError());
				}
				if (registerResult.getSuccess() != null) {
					updateUiWithUser(registerResult.getSuccess());
				}
				setResult(Activity.RESULT_OK);
				Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});

		TextWatcher afterTextChangedListener = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// ignore
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// ignore
			}

			@Override
			public void afterTextChanged(Editable s) {
				registerViewModel.registerDataChanged(fullNameEditText.getText().toString(), usernameEditText.getText().toString(),
					passwordEditText.getText().toString(), repeatPasswordEditText.getText().toString());
			}
		};

		fullNameEditText.addTextChangedListener(afterTextChangedListener);
		usernameEditText.addTextChangedListener(afterTextChangedListener);
		passwordEditText.addTextChangedListener(afterTextChangedListener);
		repeatPasswordEditText.addTextChangedListener(afterTextChangedListener);

		registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				registerViewModel.register(fullNameEditText.getText().toString(), usernameEditText.getText().toString(),
					passwordEditText.getText().toString());
			}
		});
}

	private void showRegistrationFailed(@StringRes Integer errorString) {
		Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
	}

	private void updateUiWithUser(RegisteredUserView model) {
		Toast.makeText(getApplicationContext(), getString(R.string.registration) + model.getDisplayName(), Toast.LENGTH_LONG).show();
	}
}



