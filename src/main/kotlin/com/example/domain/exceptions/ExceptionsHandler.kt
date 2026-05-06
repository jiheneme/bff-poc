package com.example.domain.exceptions

class UserNotFoundException(val email: String) : Throwable()
class RemoteServiceException(val serviceName: String, val statusCode: Int) : Throwable()
// Ces exceptions sont intercéptées de la même façon dans toute l'app, donc il ne faut pas rajouter la gestion des erreurs métiers ici