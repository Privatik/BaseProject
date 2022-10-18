package io.my.data.local

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

internal fun getSqlDelightClient(context: Context): SqlDriver{
    return AndroidSqliteDriver(
        composeSqlDriverSchemes(),
        context,
        name = null
    )
}

private fun composeSqlDriverSchemes(vararg schemes: SqlDriver.Schema): SqlDriver.Schema =
    object : SqlDriver.Schema {
        override val version: Int = schemes.reduce { first, second ->
            if (first.version != second.version) {
                error("All schemes versions must be the same. first = $first, second: $second")
            }
            second
        }.version

        override fun create(driver: SqlDriver) =
            schemes.forEach {
                it.create(driver)
            }

        override fun migrate(driver: SqlDriver, oldVersion: Int, newVersion: Int) =
            schemes.forEach {
                it.migrate(driver, oldVersion, newVersion)
            }
    }