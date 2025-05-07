package srangeldev.database

import org.apache.ibatis.jdbc.ScriptRunner
import org.lighthousegames.logging.logging
import srangeldev.config.AppConfig
import java.io.PrintWriter
import java.io.Reader
import java.sql.Connection
import java.sql.DriverManager

object DataBaseManager : AutoCloseable {
    private val logger = logging()
    private val appConfig = AppConfig() // Instancia de AppConfig para obtener configuraciones

    var connection: Connection? = null
        private set

    init {
        initDatabase()
    }

    private fun initDatabase() {
        initConexion()
        if (appConfig.databaseInitTables) {
            initTablas()
        }
        if (appConfig.databaseInitData) {
            initData()
        }
        close()
    }

    private fun initConexion() {
        logger.debug { "Iniciando conexión con la base de datos en ${appConfig.databaseUrl}" }
        if (connection == null || connection!!.isClosed) {
            connection = DriverManager.getConnection(appConfig.databaseUrl)
            logger.debug { "Conexión con la base de datos iniciada" }
        } else {
            logger.debug { "La conexión con la base de datos ya está iniciada" }
        }
    }

    private fun initTablas() {
        logger.debug { "Creando tablas de la base de datos" }
        try {
            val tablas = ClassLoader.getSystemResourceAsStream("tablas.sql")?.bufferedReader()
                ?: throw Exception("No se encontró el archivo tablas.sql")
            scriptRunner(tablas, true)
            logger.debug { "Tablas de la base de datos creadas correctamente." }
        } catch (e: Exception) {
            logger.error { "Error al crear las tablas de la base de datos: ${e.message}" }
        }
    }

    private fun initData() {
        logger.debug { "Iniciando carga de datos de la base de datos" }
        try {
            val datos = ClassLoader.getSystemResourceAsStream("datos.sql")?.bufferedReader()
                ?: throw Exception("No se encontró el archivo datos.sql")
            scriptRunner(datos, true)
            logger.debug { "Datos de la base de datos cargados correctamente." }
        } catch (e: Exception) {
            logger.error { "Error al cargar los datos de la base de datos: ${e.message}" }
        }
    }

    override fun close() {
        logger.debug { "Cerrando conexión con la base de datos" }
        connection?.let {
            if (!it.isClosed) {
                it.close()
                logger.debug { "Conexión con la base de datos cerrada correctamente." }
            }
        }
    }

    fun <T> use(block: (DataBaseManager) -> T) {
        initConexion()
        try {
            block(this)
        } finally {
            close()
        }
    }

    private fun scriptRunner(reader: Reader, logWriter: Boolean = false) {
        if (connection == null || connection!!.isClosed) {
            throw IllegalStateException("Error: la conexión con la base de datos no está establecida.")
        }

        logger.debug { "Ejecutando script SQL con log: $logWriter" }
        val sr = ScriptRunner(connection!!)
        sr.setLogWriter(if (logWriter) PrintWriter(System.out) else null)
        sr.runScript(reader)
    }
}
