package br.com.dmsec.starter.commons.exception


import kotlin.RuntimeException
import kotlin.reflect.KClass

data class NotFoundException (
    val resource: String,
    val value: Any
    ) : RuntimeException(){

        override val message: String
            get() = "Not found the resource [${resource}] with value [${value}]"

        constructor(resource: KClass<*>, value: Any) : this(resource.simpleName!!, value)
    }