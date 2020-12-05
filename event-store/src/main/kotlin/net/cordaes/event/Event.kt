package net.cordaes.event

import net.cordaes.eventStore.JsonHelper
import net.cordaes.json.JSONObject
import java.util.UUID

/**
 * Data class to define an Event for a simple event sourcing pattern
 */
data class Event(
        val id: UUID = UUID.randomUUID(),
        val type: String,
        val timestamp: Long = System.currentTimeMillis(),
        val creator: String = "???",
        val aggregateId: String? = null,
        val payload: Map<String, Any?> = emptyMap()
) {


    fun payloadAsUUID(key: String, default: UUID? = null): UUID {
        return payloadAsType(key, default, {
            if (it is UUID) id else UUID.fromString(it as String)
        })
    }

    fun payloadAsString(key: String, default: String? = null): String {
        return payloadAsType(key, default, { it.toString() })
    }

    fun payloadAsLong(key: String, default: Long? = null): Long {
        return payloadAsType(key, default, { it.toString().toLong() })
    }

    fun payloadAsInt(key: String, default: Int? = null): Int {
        return payloadAsType(key, default, { it.toString().toInt() })
    }

    fun payloadAsBoolean(key: String, default: Boolean? = null): Boolean {
        return payloadAsType(key, default, { it.toString().toBoolean() })
    }

    fun payloadAsDouble(key: String, default: Double? = null): Double {
        return payloadAsType(key, default, { it.toString().toDouble() })
    }

    fun aggregateIdAsUUID() : UUID {
        return UUID.fromString(aggregateId)
    }

    private fun <T> payloadAsType(key: String, default: T?, mapper: (Any) -> T): T {
        return if (payload.containsKey(key)) {
            mapper(payload[key] as Any)
        } else {
            default ?: throw NoSuchElementException("$key is missing from the payload and no default specified")
        }
    }

    /**
     * Move between different representations
     */
    object ModelMapper {
        fun asMap(ev: Event): Map<String, Any> {
            val map = mutableMapOf(
                    "id" to ev.id,
                    "type" to ev.type,
                    "creator" to ev.creator,
                    "timestamp" to ev.timestamp,
                    "payload" to ev.payload
            )

            if (ev.aggregateId != null) map["aggregateId"] = ev.aggregateId

            return map
        }

        fun fromMap(data: Map<String, Any>): Event {
            var ev = Event(id = UUID.fromString(data["id"] as String),
                    type = data["type"] as String,
                    timestamp = data["timestamp"].toString().toLong(),
                    creator = data["creator"] as String)

            if (data.containsKey("payload")) {
                ev = ev.copy(payload = data["payload"] as Map<String, Any?>)
            }

            if (data.containsKey("aggregateId")) {
                ev = ev.copy(aggregateId = data["aggregateId"] as String)
            }

            return ev
        }

        fun fromJSON(json: JSONObject): Event {
            val id = UUID.fromString(json.get("id") as String)
            val type = json.getString("type")
            val creator = json.getString("creator")
            val timestamp = json.get("timestamp") as Long
            val aggregateId = if (json.has("aggregateId")) json.getString("aggregateId") else null
            val payload = if (json.has("payload")) JsonHelper.jsonToMap(json.getJSONObject("payload")) else emptyMap()

            return Event(id = id,
                    type = type,
                    creator = creator,
                    timestamp = timestamp,
                    aggregateId = aggregateId,
                    payload = payload)
        }

        fun toJSON(event: Event): JSONObject = JSONObject(asMap(event))
    }
}




