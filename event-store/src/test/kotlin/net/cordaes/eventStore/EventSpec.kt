package net.cordaes.eventStore

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.throws
import net.cordaes.eventStore.EventSpecHelper.ev
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.*
import kotlin.NoSuchElementException

@RunWith(JUnitPlatform::class)
object EventSpec : Spek({

    describe("standard operations on an Event class") {

        it("should retrieve payload item as UUID") {
            val uuid = UUID.randomUUID()
            assertThat(ev(mapOf("uuid" to uuid.toString())).payloadAsUUID("uuid"), equalTo(uuid))
            assertThat(ev(emptyMap()).payloadAsUUID("uuid", uuid), equalTo(uuid))
            assertThat({ ev(emptyMap()).payloadAsUUID("uuid") }, throws<NoSuchElementException>())
            assertThat({ ev(mapOf("uuid" to "not a uuid")).payloadAsUUID("uuid") }, throws<RuntimeException>())
        }

    }
})