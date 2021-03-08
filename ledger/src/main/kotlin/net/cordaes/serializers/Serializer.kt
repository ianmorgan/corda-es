package net.cordaes.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import net.corda.core.identity.Party
import net.cordaes.json.JSONObject
import java.io.File
import java.io.IOException

class Serializer {
    private val mapper: ObjectMapper = ObjectMapper()

    init {
        val module = KotlinModule()
        module.addSerializer(Party::class.java, PartySerializer())
        mapper.registerModule(module)
    }


    fun toMap(value: Any): Map<String, Any?> {
        // Jackson gives us good JSON
        val json = mapper.writeValueAsString(value)

        // At JSONObject converts it (reasonably) sensibly to a regular map
        return JSONObject(json).toMap()
    }

    fun toJson(value: Any): String {
        // Jackson gives us good JSON
        val json = mapper.writeValueAsString(value)
        return json
    }}


class PartySerializer @JvmOverloads constructor(t: Class<Party>? = null) : StdSerializer<Party>(t) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(
            value: Party, jgen: JsonGenerator, provider: SerializerProvider) {
        jgen.writeString(value.name.organisation)
    }
}