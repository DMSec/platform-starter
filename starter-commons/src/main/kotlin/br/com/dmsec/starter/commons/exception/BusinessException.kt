package br.com.dmsec.starter.commons.exception

import br.com.dmsec.starter.commmons.exception.ErrorCode

class BusinessException private constructor(
    val errorCode: ErrorCode,
    override val cause: Throwable? = null,
    val message: Array<String> = arrayOf()
) : RuntimeException(cause) {

    private lateinit var log: () -> Unit

    private var logUsed = false

    fun executeAppLog(defaultLog: () -> Unit) {

        if (logUsed) throw java.lang.IllegalStateException("Não deve logar mais de uma vez a mesma informação")

        if(this::log.isInitialized){
            logUsed = true
            log()

        }else {
            defaultLog
        }
    }

    constructor(key: String = ErrorCode.DEFAULT_ERROR.key, cause: Throwable? = null) :
            this(errorCode = ErrorCode(key), cause = cause)

    constructor(key: String = ErrorCode.DEFAULT_ERROR.key, cause: Throwable? = null, messages: Array<String>) :
            this(errorCode = ErrorCode(key), cause = cause, messages = message)

    constructor(key: String = ErrorCode.DEFAULT_ERROR.key, applicationLog: () -> Unit) :
            this(ErrorCode(key)
            ) {
                this.log = applicationLog
            }

    constructor(key: String = ErrorCode.DEFAULT_ERROR.key, cause: Throwable? = null, applicationLog: () -> Unit) :
            this(ErrorCode(key), cause
            ) {
        this.log = applicationLog
    }
} BusinessException