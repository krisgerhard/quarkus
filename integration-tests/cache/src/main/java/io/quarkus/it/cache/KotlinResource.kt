package io.quarkus.it.cache

import io.quarkus.cache.CacheInvalidateAll
import io.quarkus.cache.CacheResult
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response

@ApplicationScoped
@Path("/kotlin-resource")
class KotlinResource {

    private var counter = 1

    @GET
    @CacheResult(cacheName = "kotlinCache")
    fun getResponse(): String {
        return counter++.toString()
    }


    @DELETE
    fun invalidateCache(): Response {
        CacheHelper.invalidateCache()
        return Response.ok().build()
    }

    @DELETE
    @Path("/all")
    fun invalidateCacheAll(): Response {
        CacheHelper.invalidateCacheAllList()
        return Response.ok().build()
    }

    @DELETE
    @Path("/all-repeated")
    fun invalidateCacheAllRepeated(): Response {
        CacheHelper.invalidateCacheAllRepeated()
        return Response.ok().build()
    }

    object CacheHelper {
        @CacheInvalidateAll(cacheName = "kotlinCache")
        @JvmStatic
        fun invalidateCache() {
        }

        @CacheInvalidateAll.List(
            CacheInvalidateAll(cacheName = "kotlinCache"),
            CacheInvalidateAll(cacheName = "kotlinCache2")
        )
        @JvmStatic
        fun invalidateCacheAllList() {
        }

        @CacheInvalidateAll(cacheName = "kotlinCache")
        @CacheInvalidateAll(cacheName = "kotlinCache2")
        @JvmStatic
        fun invalidateCacheAllRepeated() {
        }
    }
}
