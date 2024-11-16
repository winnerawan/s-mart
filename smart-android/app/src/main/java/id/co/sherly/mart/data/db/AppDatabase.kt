/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.co.sherly.mart.data.db.dao.PurchaseDao
import id.co.sherly.mart.data.db.entity.PurchaseEntity
import id.co.sherly.mart.data.db.entity.PurchaseItemEntity

@Database(entities = [PurchaseEntity::class, PurchaseItemEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDao
}