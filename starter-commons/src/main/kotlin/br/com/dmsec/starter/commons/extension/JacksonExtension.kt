package br.com.dmsec.starter.commons.extension


import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

object JacksonExtension {

    val jacksonObjectMapper: ObjectMapper by lazy {
        ObjectMapper().registerModule(JavaTimeModule())
                .setSeriaizationInclusion(JsonInclude.Include.NON_NULL)
                .disable(SerializationFeature.WRITE_DATE_AS_TIMESTAMPS)
                .registerModule(KotlinModule())
    }
}

fun <T> String.jsonToArrayListObject(t: Class<T>): ArrayList<T> {
    val valueType = TypeFactory.defaultInstance().constructorCollectionType(ArrayList::class.java, t)
    return JacksonExtension.jacksonObjectMapper.readValue(this, valueType)
}

fun <T> String.jsonToMutableListObject(t: Class<T>): MutableList<T> {
    val valueType = TypeFactory.defaultInstance().constructorCollectionType(MutableList::class.java, t)
    return JacksonExtension.jacksonObjectMapper.readValue(this, valueType)
}

fun <T> String.jsonToObject(t: Class<T>): T =
    JacksonExtension.jacksonObjectMapper.readValue(this, t)

inline fun <reified T> String.jsonToObject(): T =
    JacksonExtension.jacksonObjectMapper.readValue(this, jacksonTypeRef<T>())

fun <T> T.objectToJson(): String =
    JacksonExtension.jacksonObjectMapper.writeValueAsString(this)

fun <T> T.toJsonNode(): JsonNode =
    JacksonExtension.jacksonObjectMapper.convertValue(this, JsonNode::class.java)

fun <T,K> String.jsonToMap(t: Class<T>, k: Class<K>): Map<T?, K?> {
    val valueType = TypeFactory.defaultInstance().constructMapType(Map::class.java, t, k)
    return JacksonExtension.jacksonObjectMapper.readValue(this, valueType)
}