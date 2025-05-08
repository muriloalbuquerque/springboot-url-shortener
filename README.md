# URL Shortener

Um serviço simples de encurtamento de URLs, construído com **Kotlin** e **Spring Boot**, utilizando **MongoDB** para armazenar os mapeamentos. O projeto está pronto para uso, testes e deploy.

---

## 🚀 Funcionalidades

* **POST /shorten**: recebe JSON com `{ "url": "<URL original>", "ttlSeconds": <tempo de expiração> }` e retorna `{ "code": "<código curto>" }`.
* **GET /{code}**: redireciona (HTTP 302) para a URL original associada ao código curto.
* **Expiração automática**: documentos são removidos do banco após o tempo definido em `ttlSeconds`, graças a um índice TTL em `expireAt`.
* **Contagem de cliques**: cada acesso ao código incrementa o contador de `clicks` (pode ser exibido ou exposto via endpoint futuro).

## 🛠 Tecnologias

* **Kotlin 1.9** + **Spring Boot 3.4**
* **MongoDB 8.0** (collection `urls` com índice TTL)
* **Gradle Wrapper** para build e execução (`./gradlew bootRun`)
* **IntelliJ IDEA** (terminal integrado)

## 📂 Estrutura do projeto

```
src/main/kotlin/urlshortener/urlshortener/
├─ controller/    # UrlController.kt
├─ service/       # UrlService.kt
├─ repository/    # UrlMappingRepository.kt
├─ model/         # UrlMapping.kt
└─ UrlshortenerApplication.kt
```

## 📈 Testes manuais

1. **Encerrar URL**

   * Endpoint: `POST http://localhost:8080/shorten`
   * Body (raw JSON):

     ```json
     { "url": "https://exemplo.com/pagina", "ttlSeconds": 60 }
     ```
   * Resposta esperada: `{ "code": "Ab3dE9xY" }`

2. **Redirecionar**

   * Endpoint: `GET http://localhost:8080/Ab3dE9xY`
   * Deve retornar **302 Found** com header `Location: https://exemplo.com/pagina`

3. **Verificar no MongoDB**

   ```js
   use urlshortener
   db.urls.find().pretty()
   db.urls.getIndexes()
   ```

## 📦 Como subir no GitHub (IntelliJ Terminal)

1. Crie o repositório no GitHub (vazio, sem README nem .gitignore).
2. No terminal integrado do IntelliJ, na pasta raiz do projeto:

   ```bash
   git init
   git add .
   git commit -m "Initial commit: URL shortener with Kotlin, Spring Boot and MongoDB"
   git branch -M main
   git remote add origin https://github.com/SEU_USUARIO/SEU_REPO.git
   git push -u origin main
   ```

Pronto! O código ficará hospedado no seu GitHub.

---

> Este projeto foi desenvolvido de forma prática, sem templates prontos, buscando manter o código claro, conciso e fácil de entender. Sinta‑se à vontade para clonar, testar e contribuir!
