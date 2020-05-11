package com.example.pma.ereader.ui.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pma.ereader.LoginActivity;
import com.example.pma.ereader.R;
import com.example.pma.ereader.model.register.RegisteredUser;
import com.google.gson.Gson;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        final TextView userNameTextView = root.findViewById(R.id.account_username);
        final TextView nameTextView = root.findViewById(R.id.account_name);
        final TextView idTextView = root.findViewById(R.id.account_id);
        Gson gson = new Gson();
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        final RegisteredUser user = gson.fromJson(sharedPref.getString("UserLoggedIn",  ""), RegisteredUser.class);
        Resources res = getResources();
        userNameTextView.setText(res.getString(R.string.account_username, user.getUserName()));
        nameTextView.setText(res.getString(R.string.account_name, user.getFullName()));
        idTextView.setText(res.getString(R.string.account_user_id, user.getUserId()));
        logOut(root);
        return root;
    }

    private void logOut(final View root) {
        Button button = root.findViewById(R.id.logout);
        button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("UserLoggedIn");
                editor.apply();
                Toast.makeText(getContext(), "Successfully logged out", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
