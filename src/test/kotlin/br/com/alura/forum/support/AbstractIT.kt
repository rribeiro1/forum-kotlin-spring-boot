package br.com.alura.forum.support

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractIT {
    @LocalServerPort
    protected var testPort: Int = -1

    @Autowired
    protected lateinit var userFacade: UserFacade

    @BeforeEach
    fun before() {
        RestAssured.port = testPort
        userFacade.resetDatabase()
    }
}
