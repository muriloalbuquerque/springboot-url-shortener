package urlshortener.urlshortener.repository

import urlshortener.urlshortener.model.UrlMapping
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant

interface UrlMappingRepository : MongoRepository<UrlMapping, String> {
    fun findByShortKey(shortKey: String): UrlMapping?
    fun deleteByExpireAtBefore(date: Instant): Long
}
