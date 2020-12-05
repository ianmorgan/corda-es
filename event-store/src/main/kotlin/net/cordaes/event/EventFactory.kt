package net.corda.ccl.commons.event

import net.cordaes.event.Event
import java.nio.file.Paths
import java.util.*

/**
 * One way to create all the events. Mainly so its easy to see the whole picture in one place
 */
enum class EventTypes { AutoRestartRegistered }

object EventFactory {

    fun AUTO_RESTART_REGISTERED(processId: UUID, label: String, builder: ProcessBuilder): Event {
        val payload = mutableMapOf("commands" to builder.command(),
                "label" to label,
                "id" to processId.toString())
        if (builder.directory() != null) {
            payload["dir"] = builder.directory().absolutePath
        } else {
            val currentRelativePath = Paths.get("")
            payload["dir"] = currentRelativePath.toAbsolutePath().toString()
        }

        val ev = Event(type = EventTypes.AutoRestartRegistered.name,
                aggregateId = processId.toString(),
                payload = payload)

        return ev
    }

}