package tw.ctl.interest.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "records")
data class Record(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "record_id")
        val recordId: Int,
        var principal: String,
        val interest: String,
        @ColumnInfo(name = "period_type")
        var periodType: Int,
        val period: String,
        var invest: String,
        @ColumnInfo(name = "simple_result")
        var simpleResult: String,
        @ColumnInfo(name = "compound_result")
        var compoundResult: String,
        @ColumnInfo(name = "invest_result")
        var investResult: String,
        var date: Date?
) {
    @Ignore
    constructor(principal: String, interest: String, periodType: Int, period: String, invest: String):
            this(0, principal, interest, periodType, period, invest, "", "", "", null)
}