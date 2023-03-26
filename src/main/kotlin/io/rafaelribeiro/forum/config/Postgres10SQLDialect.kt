package io.rafaelribeiro.forum.config

import org.hibernate.dialect.DatabaseVersion
import org.hibernate.dialect.PostgreSQLDialect

// See https://stackoverflow.com/questions/74744188/what-happened-to-postgresql10dialect-in-hibernate-6-x.
class Postgres10SQLDialect : PostgreSQLDialect(DatabaseVersion.make(10))
