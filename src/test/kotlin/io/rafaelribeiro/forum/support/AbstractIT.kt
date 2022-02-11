package io.rafaelribeiro.forum.support

import io.rafaelribeiro.forum.support.facades.CourseFacade
import io.rafaelribeiro.forum.support.facades.TopicFacade
import io.rafaelribeiro.forum.support.facades.UserFacade
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
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

    @Autowired
    protected lateinit var courseFacade: CourseFacade

    @Autowired
    protected lateinit var topicFacade: TopicFacade

    protected fun authenticated(): RequestSpecification {
        return RestAssured.given()
            .auth().preemptive().oauth2("Any")
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
    }

    @BeforeEach
    fun before() {
        RestAssured.port = testPort
        userFacade.resetDatabase()
        courseFacade.resetDatabase()
        topicFacade.resetDatabase()
    }
}
