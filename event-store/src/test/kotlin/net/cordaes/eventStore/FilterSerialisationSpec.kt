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
object FilterSerialisationSpec : Spek({

    describe("serialization  / deserialization on a Filter class") {

        it("should round trip simple filter") {
            val filter = Filter(type = "EventType1")
            val json = Filter.ModelMapper.asJSON(filter)
            val deserializedFilter = Filter.ModelMapper.fromJSON(json)

            assertThat(deserializedFilter, equalTo(filter))
        }

        it("should round trip filter with all attributes set") {
            val filter = Filter(types = listOf("EventType1","EventType2"),
                    aggregateId = "id1",
                    lastId = UUID.randomUUID(),
                    pageSize = 10)
            val json = Filter.ModelMapper.asJSON(filter)
            val deserializedFilter = Filter.ModelMapper.fromJSON(json)

            assertThat(deserializedFilter, equalTo(filter))
        }

    }
})