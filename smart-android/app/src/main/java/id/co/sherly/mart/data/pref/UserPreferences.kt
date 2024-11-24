package id.co.sherly.mart.data.pref

import android.content.SharedPreferences
import id.co.sherly.mart.data.pref.delegates.booleanPreference
import id.co.sherly.mart.data.pref.delegates.intPreference
import id.co.sherly.mart.data.pref.delegates.nullableStringPreference
import id.co.sherly.mart.di.UserPrefs
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    @UserPrefs preferences: SharedPreferences
) {

    var isBiometricEnable by preferences.booleanPreference(IS_BIOMETRIC_ENABLE, false)
    var name by preferences.nullableStringPreference(NAME)
    var username by preferences.nullableStringPreference(USERNAME)
    var isLoggedIn by preferences.booleanPreference(IS_LOGGED_IN, false)
    var cartPurchase by preferences.nullableStringPreference(CART_PURCHASE)
    var masterItemGridCount by preferences.intPreference(MASTER_ITEM_GRID, 4)
    var masterItemCategoryGridCount by preferences.intPreference(MASTER_ITEM_CATEGORY_GRID, 4)
    var itemStockGrid by preferences.intPreference(ITEMS_STOCK_GRID, 5)

    var storeName by preferences.nullableStringPreference(STORE_NAME, STORE_NAME)
    var receiptFooter by preferences.nullableStringPreference(RECEIPT_FOOTER, RECEIPT_FOOTER)

}

private const val NAME = "NAME"
private const val USERNAME = "USERNAME"
private const val IS_LOGGED_IN = "IS_LOGGED_IN"
private const val IS_BIOMETRIC_ENABLE = "IS_BIOMETRIC_ENABLE"
private const val CART_PURCHASE = "CART_PURCHASE"
private const val MASTER_ITEM_GRID = "MASTER_ITEM_GRID"
private const val MASTER_ITEM_CATEGORY_GRID = "MASTER_ITEM_CATEGORY_GRID"
private const val ITEMS_STOCK_GRID = "ITEMS_STOCK_GRID"

private const val STORE_NAME = "FAMILY MART"
private const val RECEIPT_FOOTER = "TERIMA KASIH SUDAH BERBELANJA :)"
