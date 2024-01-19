CREATE TABLE IF NOT EXISTS public.author (
    "id"    UUID NOT NULL,
    "email" VARCHAR(255) NOT NULL,
    "name"  VARCHAR(255) NOT NULL,
    "pass"  VARCHAR(255) NOT NULL,
    PRIMARY KEY ("id"),
    UNIQUE ("email")
);

CREATE TABLE IF NOT EXISTS public.role (
    "id"    UUID NOT NULL,
    "name"  VARCHAR(255) NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS public.privilege (
    "id"    UUID NOT NULL,
    "name"  VARCHAR(255) NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS public.course (
    "id"            UUID NOT NULL,
    "category"      VARCHAR(255),
    "name"          VARCHAR(255),
    "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT now()  NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS public.author_roles (
    "author_id"     UUID NOT NULL,
    "role_id"       UUID NOT NULL,
    CONSTRAINT "fk_author_roles_author_id" FOREIGN KEY ("author_id") REFERENCES "author" ("id") ON DELETE CASCADE,
    CONSTRAINT "fk_author_roles_role_id" FOREIGN KEY ("role_id") REFERENCES "role" ("id") ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.role_privileges (
    "role_id"       UUID NOT NULL,
    "privilege_id"  UUID NOT NULL,
    CONSTRAINT "fk_role_privileges_role_id" FOREIGN KEY ("role_id") REFERENCES "role" ("id") ON DELETE CASCADE,
    CONSTRAINT "fk_role_privileges_privilege_id" FOREIGN KEY ("privilege_id") REFERENCES "privilege" ("id") ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.topic (
    "id"            UUID NOT NULL,
    "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT now()  NOT NULL,
    "message"       TEXT                                    NOT NULL,
    "status"        VARCHAR(255)                            NOT NULL,
    "title"         VARCHAR(255)                            NOT NULL,
    "author_id"     UUID                                    NOT NULL,
    "course_id"     UUID                                    NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk_topic_author_id" FOREIGN KEY ("author_id") REFERENCES "author" ("id") ON DELETE CASCADE,
    CONSTRAINT "fk_topic_course_id" FOREIGN KEY ("course_id") REFERENCES "course" ("id") ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.answer (
    "id"            UUID NOT NULL,
    "creation_date" TIMESTAMP WITH TIME ZONE    DEFAULT now()   NOT NULL,
    "message"       TEXT                                        NOT NULL,
    "solution"      BOOLEAN                     DEFAULT false   NOT NULL,
    "author_id"     UUID                                        NOT NULL,
    "topic_id"      UUID                                        NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk_answer_author_id" FOREIGN KEY ("author_id") REFERENCES "author" ("id") ON DELETE CASCADE,
    CONSTRAINT "fk_answer_topic_id" FOREIGN KEY ("topic_id") REFERENCES "topic" ("id") ON DELETE CASCADE
);
