package urlshortener.urlshortener.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed
import java.time.Instant
import kotlin.time.Duration


@Document("urls")
data class UrlMapping(
    @Id val id: String? = null,
    val originalUrl: String,
    val shortKey: String,
    val createdAt: Instant = Instant.now(),
    val expireAt: Instant? = null,
    var clicks: Long = 0
)