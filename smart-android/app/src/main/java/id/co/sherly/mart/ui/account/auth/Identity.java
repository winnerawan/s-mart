/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.account.auth;

import androidx.annotation.Keep;

import android.accounts.Account;
import android.accounts.AccountManager;
import java.io.Serializable;

import id.co.sherly.mart.data.model.Token;
import id.co.sherly.mart.data.model.User;


@Keep
public class Identity implements Serializable {

    private Account account;
    private Token token;
    private User user;

//    public Identity(Parcel in) {
//        this.user = (User) in.readParcelable(getClass().getClassLoader());
//        this.token = (Token) in.readParcelable(getClass().getClassLoader());
//        this.account = (Account) in.readParcelable(getClass().getClassLoader());
//    }

    public Identity(User user2, Token token2) {
        this.user = user2;
        this.token = token2;
        this.account = Auth.createAccount();
    }

    public Identity(AccountManager am, Account account2) {
        this.account = account2;
        this.token = new Token(am, account2);
        this.user = new User(am, account2);
    }

    public int describeContents() {
        return 0;
    }

//    public void writeToParcel(Parcel out, int flags) {
//        out.writeParcelable(this.user, flags);
//        out.writeParcelable(this.token, flags);
//        out.writeParcelable(this.account, flags);
//    }

    public void writeToAccountManager(AccountManager am) {
        this.user.writeToAccountManager(am, this.account);
        this.token.writeToAccountManager(am, this.account);
    }

    public User getUser() {
        return this.user;
    }

    public Token getToken() {
        return this.token;
    }

    public Account getAccount() {
        return this.account;
    }

    public boolean equals(Object object) {
        boolean z = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Identity)) {
            return false;
        }
        Identity identity = (Identity) object;
        if (!this.user.equals(identity.user) || !this.token.equals(identity.token) || !this.account.equals(identity.account)) {
            z = false;
        }
        return z;
    }
}
