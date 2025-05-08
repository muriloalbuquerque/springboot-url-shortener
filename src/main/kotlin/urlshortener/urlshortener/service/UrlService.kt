package urlshortener.urlshortener.service

import urlshortener.urlshortener.model.UrlMapping
import urlshortener.urlshortener.repository.UrlMappingRepository
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.Duration
import java.time.Instant

@Service
class UrlService(
    private val repository: UrlMappingRepository
) {
    companion object {
        private const val CODE_LENGTH = 8
        private const val ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        private val rnd = SecureRandom()
    }

    fun shorten(originalUrl: String, ttl: Duration?): String {
        var code: String
        do {
            code = generateShortCode()
        } while (repository.findByShortKey(code) != null)

        val expireAt = ttl?.let { Instant.now().plus(it) }

        repository.save(
            UrlMapping(
                id = null,
                originalUrl = originalUrl,
                shortKey = code,
                createdAt = Instant.now(),
                expireAt = expireAt
            )
        )
        return code
    }

    fun resolve(shortCode: String): String? {
        val mapping = repository.findByShortKey(shortCode) ?: return null
        return if (mapping.expireAt?.isAfter(Instant.now()) != false) {
            mapping.originalUrl
        } else {
            null
        }
    }

    private fun generateShortCode(): String {
        val sb = StringBuilder(CODE_LENGTH)
        repeat(CODE_LENGTH) {
            sb.append(ALPHABET[rnd.nextInt(ALPHABET.length)])
        }
        return sb.toString()
    }
}
