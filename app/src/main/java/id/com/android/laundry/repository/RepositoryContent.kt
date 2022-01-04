package id.com.android.laundry.repository

import android.app.Application
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration
import id.com.android.laundry.tools.UtilConstant



abstract class RepositoryContent : RoomDatabase() {


    companion object {
        fun newInstance(application: Application): RepositoryContent {
            return Room.databaseBuilder(application, RepositoryContent::class.java, UtilConstant.PARAM_DATABASE)
                .addMigrations(MIGRATION_1_2)
                .build()
        }
        internal val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Since we didn't alter the table, there's nothing else to do here.
             }
        }
    }


}