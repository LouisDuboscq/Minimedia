package com.lduboscq.minimedia

import com.squareup.sqldelight.db.SqlDriver
import com.lduboscq.minimedia.db.MinimediaDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): MinimediaDatabase {
    val driver = driverFactory.createDriver()
    val database = MinimediaDatabase(driver)

    return database
}
