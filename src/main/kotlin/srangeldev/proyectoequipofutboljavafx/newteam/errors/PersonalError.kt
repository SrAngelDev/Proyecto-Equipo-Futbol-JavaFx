package srangeldev.proyectoequipofutboljavafx.newteam.errors

sealed class PersonalError(val message: String) {
    class LoadCsv(message: String) : PersonalError(message)
    class SaveCsv(message: String) : PersonalError(message)
    class LoadJson(message: String) : PersonalError(message)
    class SaveJson(message: String) : PersonalError(message)
    class LoadImage(message: String) : PersonalError(message)
    class SaveImage(message: String) : PersonalError(message)
    class DeleteImage(message: String) : PersonalError(message)
    class deleteById(message: String) : PersonalError(message)
    class ValidationError(message: String) : PersonalError(message)
    class Notfound(message: String) : PersonalError(message)
    class ExportZip(message: String) : PersonalError(message)
    class ImportZip(message: String) : PersonalError(message)

}