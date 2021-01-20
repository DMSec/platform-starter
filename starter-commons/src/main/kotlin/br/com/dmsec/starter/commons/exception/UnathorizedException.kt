package br.com.dmsec.starter.commons.exception

import br.com.dmsec.starter.commmons.exception.ErrorCode

data class UnathorizedException (
    val errorCode: ErrorCode
        ) : RuntimeException() {

            constructor(key: String = ErrorCode.UNAUTHORIZED_ERROR.key) :
                    this(errorCode = ErrorCode(key))
        }