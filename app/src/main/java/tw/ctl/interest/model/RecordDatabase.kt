package tw.ctl.interest.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

@Database(entities = [Record::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class RecordDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao

    companion object {
        private var INSTANCE: RecordDatabase? = null

        fun getInstance(context: Context): RecordDatabase? {
            if (INSTANCE == null) {
                synchronized(RecordDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            RecordDatabase::class.java, "record.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
