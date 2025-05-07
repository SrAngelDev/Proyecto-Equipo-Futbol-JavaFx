package srangeldev.service

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.lighthousegames.logging.logging
import srangeldev.exceptions.PersonalException
import srangeldev.models.Personal
import srangeldev.repository.PersonalRepository
import srangeldev.repository.PersonalRespositoryImpl
import srangeldev.storage.FileFormat
import srangeldev.storage.PersonalStorage
import srangeldev.storage.PersonalStorageImpl
import srangeldev.validator.validate
import java.io.File

private const val CACHE_SIZE = 5

/**
 * Clase que implementa el servicio de gestión de personal.
 */
class PersonalServiceImpl(
    private val storage: PersonalStorage = PersonalStorageImpl(),
    private val repository: PersonalRepository = PersonalRespositoryImpl(),
    private val cache: Cache<Int, Personal>
): PersonalService {
    private val logger = logging()

    init {
        logger.debug { "Inicializando servicio de personal." }
    }

    private fun readFromFile(filePath: String, fileFormat: FileFormat): List<Personal> {
        logger.debug { "Leyendo personal de fichero: $filePath" }
        return storage.readFromFile(File(filePath), fileFormat)
    }

    private fun writeToFile(filePath: String, fileFormat: FileFormat, personalList: List<Personal>) {
        logger.debug { "Escribiendo personal en fichero: $filePath" }
        storage.writeToFile(File(filePath), fileFormat, personalList)
    }

    override fun importFromFile(filePath: String, format: FileFormat) {
        logger.info { "Importando personal de fichero: $filePath" }
        val personalList = readFromFile(filePath, format)
        personalList.forEach { repository.save(it) }
        logger.debug { "Personal guardado en repository: ${repository.getAll()}" }
    }

    override fun exportToFile(filePath: String, format: FileFormat) {
        logger.info { "Exportando personal a fichero: $filePath" }
        val personalList = repository.getAll()
        writeToFile(filePath, format, personalList)
    }

    override fun getAll(): List<Personal> {
        logger.info { "Obteniendo todo el personal" }
        return repository.getAll()
    }

    override fun getById(id: Int): Personal? {
        logger.info { "Obteniendo personal con id: $id" }

        return cache.getIfPresent(id) ?: repository.getById(id)?.also {
            cache.put(id, it)
        } ?: throw PersonalException.PersonalNotFoundException(id)
    }


    override fun save(personal: Personal): Personal {
        logger.info { "Guardando personal: $personal" }
        personal.validate()
        return repository.save(personal)
    }

    override fun update(id: Int, personal: Personal): Personal? {
        logger.info { "Actualizando personal con id: $id" }
        personal.validate()
        return repository.update(id, personal)?.also {
            cache.invalidate(id)
        } ?: throw PersonalException.PersonalNotFoundException(id)
    }

    override fun delete(id: Int): Personal {
        logger.info { "Borrando personal con id: $id" }
        return repository.delete(id)?.also {
            cache.invalidate(id)
        } ?: throw PersonalException.PersonalNotFoundException(id)
    }
}