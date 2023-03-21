package io.rafaelribeiro.forum.support

import com.fasterxml.jackson.databind.ObjectMapper
import io.rafaelribeiro.forum.support.facades.CourseFacade
import io.rafaelribeiro.forum.support.facades.RoleFacade
import io.rafaelribeiro.forum.support.facades.TopicFacade
import io.rafaelribeiro.forum.support.facades.UserFacade
import io.restassured.RestAssured
import io.restassured.config.ObjectMapperConfig
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ContextConfiguration(initializers = [TestPostgresConfiguration::class])
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractIT {

    @LocalServerPort
    protected var testPort: Int = -1

    @Autowired
    protected lateinit var userFacade: UserFacade

    @Autowired
    protected lateinit var roleFacade: RoleFacade

    @Autowired
    protected lateinit var courseFacade: CourseFacade

    @Autowired
    protected lateinit var topicFacade: TopicFacade

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    protected fun authenticated(): RequestSpecification {
        return RestAssured.given()
            .auth().preemptive().oauth2("Any")
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
    }

    @BeforeEach
    fun before() {
        RestAssured.port = testPort
        RestAssured.defaultParser = Parser.JSON
        RestAssured.config.objectMapperConfig(
            ObjectMapperConfig.objectMapperConfig()
                .jackson2ObjectMapperFactory { _, _ -> objectMapper }
        )

        userFacade.resetDatabase()
        roleFacade.resetDatabase()
        courseFacade.resetDatabase()
        topicFacade.resetDatabase()
    }
}
