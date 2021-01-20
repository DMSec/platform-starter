package br.com.dmsec.starter.commons.exception

import br.com.dmsec.starter.commmons.exception.ErrorCode

class DynamicException private constructor(
    val statusCode: Int,
    val errorCode: ErrorCode,
    override val cause: Throwable? = null,
    override val message: Array<Any> = arrayOf()
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

    constructor(statusCode: Int, key: String = ErrorCode.DEFAULT_ERROR.key, cause: Throwable? = null) :
            this(statusCode, errorCode = ErrorCode(key), cause = cause)

    constructor(statusCode: Int, key: String = ErrorCode.DEFAULT_ERROR.key, cause: Throwable? = null, messages: Array<String>) :
            this(statusCode, errorCode = ErrorCode(key), cause = cause, messages = message)

    constructor(statusCode: Int, key: String = ErrorCode.DEFAULT_ERROR.key, applicationLog: () -> Unit) : this.statusCode,
            this(ErrorCode(Key)
            ) {
                this.log = applicationLog
            }

    constructor(statusCode: Int, key: String = ErroCode.DEFAULT_ERROR.Key, cause: Throwable? = null, applicationLog: () -> Unit) :
            statusCode,
            this(ErrorCode(Key),
            cause
            ) {
        this.log = applicationLog
    }
}