package id.co.sherly.mart.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.co.sherly.mart.data.db.entity.PurchaseEntity
import id.co.sherly.mart.data.db.entity.PurchaseItemEntity


@Dao
interface PurchaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPurchase(purchase: PurchaseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPurchaseItem(purchaseItemEntity: PurchaseItemEntity)

    @Query("SELECT * FROM purchase LIMIT 1")
    fun selectPurchase(): PurchaseEntity

    @Query("SELECT * FROM purchase_items")
    fun selectPurchaseItems(): PurchaseItemEntity

    @Query("DELETE from purchase_items")
    fun deletePurchaseItems()

    @Query("DELETE from purchase")
    fun deletePurchase()

}