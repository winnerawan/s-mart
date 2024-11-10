/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.account.auth;


import android.accounts.Account;
import android.accounts.AccountManager;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import id.co.sherly.mart.BuildConfig;
import id.co.sherly.mart.Smart;
import id.co.sherly.mart.R;

@Keep
public abstract class Auth {
    private static String accountName;
    private static String accountType;
    private static String authTokenLabel;
    private static String authTokenType;

    public static String getAccountName() {
        if (accountName == null) {
            accountName = BuildConfig.APPLICATION_ID;
        }
        return accountName;
    }

    public static String getAccountType() {
        if (accountType == null) {
            accountType = BuildConfig.APPLICATION_ID;//Jagonet.Companion.getInstances().getString(R.string.account_type);
        }
        return accountType;
    }

    public static String getAuthTokenType() {
        if (authTokenType == null) {
            authTokenType = Smart.Companion.getInstances().getString(R.string.account_authtoken_type);
        }
        return authTokenType;
    }

    public static String getAuthTokenLabel() {
        if (authTokenLabel == null) {
            authTokenLabel = Smart.Companion.getInstances().getString(R.string.account_authtoken_label);
        }
        return authTokenLabel;
    }

    public static Account createAccount() {
        return createAccount(getAccountName(), getAccountType());
    }

    public static Account createAccount(@NonNull String name, @NonNull String type) {
        return new Account(name, type);
    }

    public static boolean isAccountExists() {
        return isAccountExists(AccountManager.get(Smart.Companion.getInstances()));
    }

    public static boolean isAccountExists(@NonNull AccountManager am) {
        return am.getAccountsByType(getAccountType()).length > 0;
    }

    public static Account getAccount() {
        return getAccount(AccountManager.get(Smart.Companion.getInstances()));
    }

    public static Account getAccount(@NonNull AccountManager am) {
        Account[] accounts = am.getAccountsByType(getAccountType());
        if (accounts.length > 0) {
            return accounts[0];
        }
        return null;
    }

    public static Identity getIdentity() {
        return getIdentity(AccountManager.get(Smart.Companion.getInstances()));
    }

    public static Identity getIdentity(@NonNull AccountManager am) {
        Account account = getAccount(am);
        if (account != null) {
            return new Identity(am, account);
        }
        return null;
    }

    public static void registerIdentity(@NonNull Identity identity) {
        registerIdentity(AccountManager.get(Smart.Companion.getInstances()), identity);
    }

    public static void registerIdentity(@NonNull AccountManager am, @NonNull Identity identity) {
        if (!createAccount().equals(identity.getAccount())) {
            //throw new IllegalStateException("Your account identity is invalid.");
        } else if (!isAccountExists(am)) {
            registerIdentityToAccountManager(am, identity);
        } else {
            //throw new IllegalStateException("Your account identity is been registered.");
        }
    }

    public static void unregisterIdentity(@NonNull Identity identity) {
        unregisterIdentity(AccountManager.get(Smart.Companion.getInstances()), identity);
    }

    public static void unregisterIdentity(@NonNull AccountManager am, @NonNull Identity identity) {
        Identity oldIdentity = getIdentity(am);
        if (oldIdentity == null) {
            //throw new IllegalStateException("Your account identity is not registered.");
        } else if (oldIdentity.equals(identity)) {
            unregisterIdentityFromAccountManager(am, oldIdentity);
        } else {
            //throw new IllegalStateException("Your account identity is invalid.");
        }
    }

    private static void registerIdentityToAccountManager(@NonNull AccountManager am, @NonNull Identity identity) {
        am.addAccountExplicitly(identity.getAccount(), identity.getToken().getToken(), null);
        am.setAuthToken(identity.getAccount(), identity.getToken().getType(), identity.getToken().getToken());
        identity.writeToAccountManager(am);
    }

    private static void unregisterIdentityFromAccountManager(@NonNull AccountManager am, @NonNull Identity identity) {
        am.removeAccountExplicitly(identity.getAccount());
    }
}