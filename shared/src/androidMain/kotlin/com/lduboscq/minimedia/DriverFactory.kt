package com.lduboscq.minimedia

import android.content.Context
import com.lduboscq.minimedia.db.MinimediaDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MinimediaDatabase.Schema, context, "test.db")
    }
}
