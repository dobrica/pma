package com.example.pma.ereader.model.login;

import com.example.pma.ereader.model.register.RegisteredUser;

public interface LoginCallback {

	void onSuccess(LoggedInUser loggedInUser);

	void onError(Throwable throwable);

}
