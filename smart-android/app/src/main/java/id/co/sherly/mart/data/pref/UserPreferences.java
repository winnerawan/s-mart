

/*
 * Copyright (c) 2023 DuckDuckGo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package id.co.sherly.mart.data.pref;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class UserPreferences {

    private final String IS_LOGGED_IN = "IS_LOGGED_IN";

    private SharedPreferences mPreferences;

    public UserPreferences(Context context) {
        try {
            mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        } catch (Exception e) {
            mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public void setLoggedIn(Boolean loggedIn) {
        mPreferences.edit().putBoolean(IS_LOGGED_IN, loggedIn).apply();
    }

    public Boolean isLoggedIn() {
        return mPreferences.getBoolean(IS_LOGGED_IN, false);
    }

}
