package tech.nilu.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {

    private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE Network ADD COLUMN chainId INTEGER NOT NULL DEFAULT 0")
            db.execSQL("ALTER TABLE Network ADD COLUMN explorerAddress TEXT")
            db.execSQL("UPDATE Network SET address='https://walletapi.nilu.tech', chainId=512, explorerAddress='https://walletapi.nilu.tech/' WHERE id=1")
            db.execSQL("UPDATE Network SET chainId=1, explorerAddress='https://api.ethplorer.io/' WHERE id=2")
            db.execSQL("UPDATE Network SET chainId=3, explorerAddress='' WHERE id=3")
        }
    }

    private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("INSERT INTO Network (id,name,address,active,symbol,chainId,explorerAddress) VALUES (4,'Pirl','https://rpc.pirl.minerpool.net',0,'PIRL',3125659152,'http://devpool.nilu.tech/pirl/')")
        }
    }

    private val MIGRATION_3_4: Migration = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("INSERT INTO Network (id,name,address,active,symbol,chainId,explorerAddress) VALUES (5,'Ether-1','https://rpc.ether1.org',0,'ETHO',1313114,'https://walletapi.ether1.org/')")
        }
    }

    val allMigrations = arrayOf(
        MIGRATION_1_2,
        MIGRATION_2_3,
        MIGRATION_3_4
    )
}
