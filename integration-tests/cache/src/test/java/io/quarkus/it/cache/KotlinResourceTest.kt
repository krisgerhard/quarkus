package io.quarkus.it.cache

import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.When
import io.restassured.module.kotlin.extensions.Then
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@QuarkusTest
@DisplayName("Tests the integration between the cache and the kotlin extensions")
class KotlinResourceTest {

    @Test
    fun testCache() {
        verifyGetResult("1")
        verifyGetResult("1")
    }

    @Test
    fun testCacheInvalidateAllIfOneAnnotation() {
        verifyGetResult("1")

        When {
            delete("/kotlin-resource")
        } Then {
            statusCode(200)
        }
        verifyGetResult("2")
    }

    @Test
    fun testCacheInvalidateAllIfList() {
        verifyGetResult("1")

        When {
            delete("/kotlin-resource/all")
        } Then {
            statusCode(200)
        }
        verifyGetResult("2")
    }

    @Test
    fun testCacheInvalidateAllIfRepeated() {
        verifyGetResult("1")

        When {
            delete("/kotlin-resource/all-repeated")
        } Then {
            statusCode(200)
        }
        verifyGetResult("2")
    }

    private fun verifyGetResult(result: String) {
        When {
            get("/kotlin-resource")
        } Then {
            statusCode(200)
            body(`is`(result))
        }
    }
}
