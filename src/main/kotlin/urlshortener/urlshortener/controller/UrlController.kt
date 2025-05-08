package urlshortener.urlshortener.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.*
import urlshortener.urlshortener.service.UrlService
import java.time.Duration
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@RestController
class UrlController(val service: UrlService) {

    data class ShortenRequest(val url: String, val ttlSeconds: Long?)
    data class ShortenResponse(val code: String)

    @PostMapping("/shorten")
    fun shorten(@RequestBody req: ShortenRequest): ShortenResponse {
        val ttl = req.ttlSeconds?.let { Duration.ofSeconds(it) }
        val code = service.shorten(req.url, ttl)
        return ShortenResponse(code)
    }

    @GetMapping("/{code}")
    fun redirect(@PathVariable code: String, response: HttpServletResponse) {
        val url = service.resolve(code) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        response.sendRedirect(url)
    }
}
