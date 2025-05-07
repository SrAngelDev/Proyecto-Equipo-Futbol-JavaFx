package srangeldev.config

import org.lighthousegames.logging.logging
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.io.File
import java.io.InputStream

private val logger = logging()

private const val CONFIG_FILE_NAME = "config.properties"

/**
 * Configuración de la aplicación con carga diferida (lazy)
 */

class AppConfig {

    val APP_PATH = System.getProperty("user.dir")

    val dataDirectory by lazy {
        val path = readProperty("data.directory") ?: "data"
        "$APP_PATH${File.separator}$path/"
    }

    val backupDirectory by lazy {
        val path = readProperty("backup.directory") ?: "backup"
        "$APP_PATH${File.separator}$path/"
    }

    val inputFormats: String by lazy {
        readProperty("input.formats") ?: "CSV,XML,JSON"
    }

    val outputFormats: String by lazy {
        readProperty("output.formats") ?: "CSV,XML,JSON"
    }

    val databaseUrl: String by lazy {
        readProperty("database.url") ?: "jdbc:sqlite:equipo.db"
    }

    val databaseInitTables: Boolean by lazy {
        readProperty("database.init.tables")?.toBoolean() ?: true
    }

    val databaseInitData: Boolean by lazy {
        readProperty("database.init.data")?.toBoolean() ?: true
    }

    val databaseLogger: Boolean by lazy {
        readProperty("database.logger")?.toBoolean() ?: true
    }

    val cacheCapacity: Long by lazy {
        readProperty("cache.capacity")?.toLong() ?: 60L
    }

    val cacheExpiration: Long by lazy {
        readProperty("cache.expiration")?.toLong() ?: 10L
    }

    val databaseStorageData: String by lazy {
        readProperty("storage.data") ?: "data"
    }

    init {
        logger.debug { "Cargando configuración de la aplicación" }
        createDirectories(dataDirectory, backupDirectory)
    }

    private fun readProperty(propiedad: String): String? {
        return try {
            logger.debug { "Leyendo propiedad: $propiedad" }
            val properties = Properties()
            val inputStream: InputStream = ClassLoader.getSystemResourceAsStream(CONFIG_FILE_NAME)
                ?: throw Exception("No se puede leer el fichero de configuración $CONFIG_FILE_NAME")
            properties.load(inputStream)
            properties.getProperty(propiedad)
        } catch (e: Exception) {
            logger.error { "Error al leer la propiedad $propiedad: ${e.message}" }
            null
        }
    }

    private fun createDirectories(vararg directories: String) {
        directories.forEach {
            logger.debug { "Creando directorio: $it" }
            Files.createDirectories(Path.of(it))
        }
    }
}