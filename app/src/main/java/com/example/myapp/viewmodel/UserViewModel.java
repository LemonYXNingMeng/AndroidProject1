package com.example.myapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private MutableLiveData<String> userID = new MutableLiveData<>();
    private MutableLiveData<String> userName = new MutableLiveData<>();
    private MutableLiveData<String> userToken = new MutableLiveData<>();
    private MutableLiveData<String> userAvatarPath = new MutableLiveData<>();

    public LiveData<String> getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID.setValue(userID);
    }

    public LiveData<String> getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.setValue(userName);
    }

    public LiveData<String> getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken.setValue(userToken);
    }

    public LiveData<String> getUserAvatarPath() {
        return userAvatarPath;
    }

    public void setUserAvatarPath(String userAvatarPath) {
        this.userAvatarPath.setValue(userAvatarPath);
    }
}
