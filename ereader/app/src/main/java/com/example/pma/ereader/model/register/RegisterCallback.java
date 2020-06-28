package com.example.pma.ereader.model.register;

public interface RegisterCallback {

	void onSuccess(RegisteredUser registeredUser);

	void onError(Throwable throwable);
}
