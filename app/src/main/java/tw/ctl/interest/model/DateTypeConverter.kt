package tw.ctl.interest.model

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateTypeConverter {
    @TypeConverter
    fun toDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun toLong(date: Date?): Long? {
        return date?.time
    }
}