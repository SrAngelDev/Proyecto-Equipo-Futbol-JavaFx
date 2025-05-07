package srangeldev.controller

import org.lighthousegames.logging.logging
import srangeldev.config.AppConfig
import srangeldev.models.Entrenador
import srangeldev.models.Jugador
import srangeldev.service.PersonalServiceImpl
import srangeldev.storage.FileFormat
import java.nio.file.Paths
import java.time.LocalDate
import java.time.LocalDateTime

private val logger = logging()
private val appConfig = AppConfig()

class Controller {
    private val service = PersonalServiceImpl()

    fun cargarDatos(formato: String) {
        logger.debug { "Cargando datos" }
        val filePath = constructFilePath(formato)
        val inputFormat = FileFormat.valueOf(formato.trim())
        service.importFromFile(filePath, inputFormat)
    }

    fun copiarDatos(formato: String) {
        logger.debug { "Copiando datos" }
        if (formato.isBlank()) {
            println("El formato proporcionado está vacío. No se puede copiar.")
            return
        }
        appConfig.outputFormats.split(",").forEach {
            val outputFormat = FileFormat.valueOf(it.trim())
            if (outputFormat.name.equals(formato.trim(), ignoreCase = true)) {
                service.exportToFile(appConfig.dataDirectory, outputFormat)
            }
        }
    }

    private fun constructFilePath(formato: String): String {
        val dataDir = Paths.get(appConfig.dataDirectory).toAbsolutePath().toString()
        val fileName = when (formato.uppercase()) {
            "CSV" -> "personal.csv"
            "XML" -> "personal.xml"
            "JSON" -> "personal.json"
            else -> throw IllegalArgumentException("Formato no soportado: $formato")
        }
        return Paths.get(dataDir, fileName).toString()
    }

    fun crearEntrenador(
        nombre: String,
        apellido: String,
        fechaNacimiento: LocalDate,
        fechaIncorporacion: LocalDate,
        salario: Double,
        paisOrigen: String,
        especializacion: Entrenador.Especializacion
    ) {
        val entrenador = Entrenador(
            id = 0, nombre, apellido, fechaNacimiento, fechaIncorporacion, salario, paisOrigen,
            LocalDateTime.now(), LocalDateTime.now(), especializacion
        )
        service.save(entrenador)
    }

    fun crearJugador(
        nombre: String,
        apellido: String,
        fechaNacimiento: LocalDate,
        fechaIncorporacion: LocalDate,
        salario: Double,
        paisOrigen: String,
        posicion: Jugador.Posicion,
        dorsal: Int,
        altura: Double,
        peso: Double,
        goles: Int,
        partidosJugados: Int
    ) {
        val jugador = Jugador(
            id = 0, nombre, apellido, fechaNacimiento, fechaIncorporacion, salario, paisOrigen,
            LocalDateTime.now(), LocalDateTime.now(), posicion, dorsal, altura, peso, goles, partidosJugados
        )
        service.save(jugador)
    }

    fun actualizarEntrenador(
        id: Int,
        nombre: String,
        apellido: String,
        fechaNacimiento: LocalDate,
        fechaIncorporacion: LocalDate,
        salario: Double,
        paisOrigen: String,
        especializacion: Entrenador.Especializacion
    ): Entrenador {
        val entrenador = Entrenador(
            id, nombre, apellido, fechaNacimiento, fechaIncorporacion, salario, paisOrigen,
            LocalDateTime.now(), LocalDateTime.now(), especializacion
        )
        return service.update(id, entrenador) as Entrenador
    }

    fun actualizarJugador(
        id: Int,
        nombre: String,
        apellido: String,
        fechaNacimiento: LocalDate,
        fechaIncorporacion: LocalDate,
        salario: Double,
        paisOrigen: String,
        posicion: Jugador.Posicion,
        dorsal: Int,
        altura: Double,
        peso: Double,
        goles: Int,
        partidosJugados: Int
    ): Jugador {
        val jugador = Jugador(
            id, nombre, apellido, fechaNacimiento, fechaIncorporacion, salario, paisOrigen,
            LocalDateTime.now(), LocalDateTime.now(), posicion, dorsal, altura, peso, goles, partidosJugados
        )
        return service.update(id, jugador) as Jugador
    }

    fun eliminarMiembro(id: Int) {
        logger.debug { "Eliminando miembro" }
        service.delete(id)?.let { println("Miembro eliminado: $it") } ?: println("Miembro no encontrado.")
    }
}
