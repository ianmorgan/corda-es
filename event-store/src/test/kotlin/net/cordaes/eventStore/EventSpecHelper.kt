package net.cordaes.eventStore

import net.cordaes.event.Event
import java.util.*

// have to keep this in a separate class for Intellij Junit runners to work
object EventSpecHelper {
    fun ev(payload: Map<String, Any>): Event {
        return Event(id = UUID.randomUUID(), type = "TestEvent", payload = payload)
    }
}