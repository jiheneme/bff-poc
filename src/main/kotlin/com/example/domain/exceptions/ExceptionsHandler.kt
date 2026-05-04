package com.example.domain.exceptions

class UserNotFoundException(val email: String) : Throwable()
class RemoteServiceException(val serviceName: String, val statusCode: Int) : Throwable()