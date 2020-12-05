package net.cordaes.eventStore

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import net.corda.ccl.commons.random
import net.cordaes.event.Event
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@RunWith(JUnitPlatform::class)
object FileEventStoreSpec : Spek({

    describe("storing and retrieving events") {

        it("should store events") {
            val dir = ".testing/${String.random(length = 6)}"
            val es = FileEventStore().load(dir)

            val alice = Event(type = "TestEvent",
                    payload = mapOf<String, Any>("name" to "alice"))
            val bob = Event(type = "TestEvent",
                    payload = mapOf<String, Any>("name" to "bob"))

            es.store(listOf(alice, bob))
            assertThat(es.retrieve(), equalTo(listOf(alice, bob)))
        }

        it("should retrieve events by aggregateId id") {
            val dir = ".testing/${String.random(length = 6)}"
            val es = FileEventStore().load(dir)

            val ev1 = Event(type = "TestEvent",
                    aggregateId = "id1")
            val ev2 = Event(type = "TestEvent",
                    aggregateId = "id2")

            es.storeEvent(ev1).storeEvent(ev2)
            assertThat(es.retrieve(Filter(aggregateId = "id1")).single(), equalTo(ev1))
        }

        it("should retrieve events by event type") {
            val dir = ".testing/${String.random(length = 6)}"
            val es = FileEventStore().load(dir)

            val ev1 = Event(type = "TestEvent1")
            val ev2 = Event(type = "TestEvent2")

            es.store(listOf(ev1, ev2))
            assertThat(es.retrieve(Filter(type = "TestEvent1")).single(), equalTo(ev1))
        }

        it("should retrieve events by event types") {
            val dir = ".testing/${String.random(length = 6)}"
            val es = FileEventStore().load(dir)

            val ev1 = Event(type = "TestEvent1")
            val ev2 = Event(type = "TestEvent2")
            val ev3 = Event(type = "TestEvent3")

            es.store(listOf(ev1, ev2, ev3))
            assertThat(es.retrieve(Filter(types = listOf("TestEvent1", "TestEvent3"))),
                    equalTo(listOf(ev1, ev3)))
        }


        it("should filter from lastEventId") {
            val dir = ".testing/${String.random(length = 6)}"
            val es = FileEventStore().load(dir)

            val skipped1 = Event(type = "TestEvent1", aggregateId = "id1")
            val skipped2 = Event(type = "TestEvent2", aggregateId = "id2")
            val skipped3 = Event(type = "TestEvent3", aggregateId = "id2")
            es.store(listOf(skipped1, skipped2, skipped3))

            val ev1 = Event(type = "TestEvent1", aggregateId = "id1")
            val ev2 = Event(type = "TestEvent2", aggregateId = "id2")
            val ev3 = Event(type = "TestEvent3", aggregateId = "id3")
            es.store(listOf(ev1, ev2, ev3))

            assertThat(es.retrieve(Filter(lastId = skipped3.id, type = "TestEvent1")).single(),
                    equalTo(ev1))
            assertThat(es.retrieve(Filter(lastId = skipped3.id, aggregateId = "id1")).single(),
                    equalTo(ev1))
            assertThat(es.retrieve(Filter(lastId = skipped3.id, types = listOf("TestEvent1", "TestEvent3"))),
                    equalTo(listOf(ev1, ev3)))
        }

    }
})