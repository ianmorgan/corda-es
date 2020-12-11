package net.cordaes.serializers

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


@RunWith(JUnitPlatform::class)
object EventSpec : Spek({

    describe("conversion toMaps") {

        it("should retrieve payload item as UUID") {
            val converter = MapConverter()
            val converted = converter.toMap(FooBar())
            assertThat(converted, equalTo(mapOf("foo" to "foo", "bar" to "bar") as Map<String,Any?>))

        }

    }
})