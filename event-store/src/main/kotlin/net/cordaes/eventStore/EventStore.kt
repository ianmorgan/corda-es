package net.cordaes.eventStore

import net.cordaes.event.Event
import net.cordaes.json.JSONObject
import java.util.UUID

/**
 * Bare bones event store for basic event sourcing
 */
interface EventStore {
    /**
     * Store a list of events
     */
    fun storeEvents(events: List<Event>): EventStore

    /**
     * Store a single event
     */
    fun storeEvent(event: Event): EventStore {
        return storeEvents(listOf(event))
    }

    fun store(event: Event): EventStore {
        return storeEvent(event)
    }

    fun store(events: List<Event>): EventStore {
        return storeEvents(events)
    }

    /**
     * Read everything
     */
    fun retrieve(): List<Event>

    /**
     * Retrieves events with filtering applied
     */
    fun retrieve(filter: Filter): List<Event>
}

data class Filter(
        val type: String? = null,  // Comma separated list of event types (the 'type' key) to filter on
        val types: List<String>? = null,
        val pageSize: Int? = null,
        val lastId: UUID? = null,  // Typically combined with the 'pageSize' to retrieve from a position within the event stream. Note that this exclusive, i.e. the query will return the matching events directly after this event id
        val aggregateId: String? = null

) {
    object ModelMapper {

        /**
         * Unpack the map of http query params and build a filter
         */
        fun fromQueryMap(map: Map<String, Array<String>>): Filter {
            val type = safeUnpack(map["type"])
            val types = safeUnpack(map["types"])
            val pageSize = safeToInteger(safeUnpack(map["pageSize"]))
            val lastId = safeToUUID(safeUnpack(map["lastId"]))
            val aggregateId = safeUnpack(map["aggregateId"])

            return Filter(
                    type = type,
                    pageSize = pageSize,
                    lastId = lastId,
                    aggregateId = aggregateId
            )
        }

        fun fromJSON(json: String): Filter {
            return fromJSON(JSONObject(json))
        }

        fun fromJSON(json: JSONObject): Filter {
            val type = if (json.has("type")) json.getString("type") else null
            val types = if (json.has("types")) {
                val types = ArrayList<String>()
                json.getJSONArray("types").forEach { types.add(it as String) }
                types
            } else null

            val pageSize = if (json.has("pageSize")) json.getInt("pageSize") else null
            val lastId = if (json.has("lastId")) UUID.fromString(json.getString("lastId")) else null
            val aggregateId = if (json.has("aggregateId")) json.getString("aggregateId") else null

            return Filter(
                    type = type,
                    types = types,
                    pageSize = pageSize,
                    lastId = lastId,
                    aggregateId = aggregateId
            )
        }

        fun asJSON(filter: Filter): String {
            return JSONObject(asMap(filter)).toString(2)
        }

        fun asMap(filter: Filter): Map<String, Any> {
            val map = HashMap<String, Any>()
            if (filter.type != null) {
                map["type"] = filter.type
            }
            if (filter.types != null) {
                map["types"] = filter.types
            }
            if (filter.pageSize != null) {
                map["pageSize"] = filter.pageSize
            }
            if (filter.lastId != null) {
                map["lastId"] = filter.lastId
            }
            if (filter.aggregateId != null) {
                map["aggregateId"] = filter.aggregateId
            }
            return map
        }

        private fun safeToInteger(value: String?): Int? {
            return if (value != null) Integer.parseInt(value) else null
        }

        private fun safeToUUID(value: String?): UUID? {
            return if (value != null) UUID.fromString(value) else null
        }

        private fun safeUnpack(array: Array<String>?): String? {
            if (array != null && array.isNotEmpty()) {
                return array[0]
            }
            return null
        }
    }
}