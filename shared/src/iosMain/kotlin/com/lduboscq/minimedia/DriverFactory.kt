package com.lduboscq.minimedia

import com.lduboscq.minimedia.db.MinimediaDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DriverFactory {
  actual fun createDriver(): SqlDriver {
    return NativeSqliteDriver(MinimediaDatabase.Schema, "test.db")
  }
}