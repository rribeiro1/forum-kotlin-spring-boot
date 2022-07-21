package io.rafaelribeiro.forum.support

import org.slf4j.MDC

object ContextHelper {
    const val USER_ID = "userId"
    const val USER_EMAIL = "userEmail"
    const val REQUEST_ID = "requestId"

    fun setContext(userId: String, userEmail: String, requestId: String) {
        MDC.put(USER_ID, userId)
        MDC.put(USER_EMAIL, userEmail)
        MDC.put(REQUEST_ID, requestId)
    }

    fun clearContext() {
        MDC.clear()
    }
}
