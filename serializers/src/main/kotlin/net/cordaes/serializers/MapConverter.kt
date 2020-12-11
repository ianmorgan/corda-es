package net.cordaes.serializers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import net.cordaes.json.JSONObject

class MapConverter {
    private val mapper: ObjectMapper = ObjectMapper()

    init {
        val module = KotlinModule()
        mapper.registerModule(module)
    }


    fun toMap(value: Any): Map<String, Any?> {
        // Jackson gives us good JSON
        val json = mapper.writeValueAsString(value)

        // At JSONObject converts it (reasonably) sensibly to a regular map
        return JSONObject(json).toMap()
    }
}