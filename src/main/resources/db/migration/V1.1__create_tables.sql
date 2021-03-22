CREATE TABLE public.author (
    "id"    BIGINT GENERATED BY DEFAULT AS IDENTITY,
    "email" VARCHAR(255) NOT NULL,
    "name"  VARCHAR(255) NOT NULL,
    "pass"  VARCHAR(255) NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE public.profile (
    "id"    BIGINT GENERATED BY DEFAULT AS IDENTITY,
    "name"  VARCHAR(255) NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE public.course (
    "id"        BIGINT GENERATED BY DEFAULT AS IDENTITY,
    "category"  VARCHAR(255),
    "name"      VARCHAR(255),
    PRIMARY KEY ("id")
);

CREATE TABLE public.author_profiles (
    "user_id"       BIGINT NOT NULL,
    "profiles_id"   BIGINT NOT NULL,
    CONSTRAINT "fk_author_profiles_user_id" FOREIGN KEY ("user_id") REFERENCES "author" ("id") ON DELETE CASCADE,
    CONSTRAINT "fk_author_profiles_profiles_id" FOREIGN KEY ("profiles_id") REFERENCES "profile" ("id") ON DELETE CASCADE
);

CREATE TABLE public.topic (
    "id"            BIGINT GENERATED BY DEFAULT AS IDENTITY,
    "creation_date" TIMESTAMP WITH TIME ZONE DEFAULT now()  NOT NULL,
    "message"       TEXT                                    NOT NULL,
    "status"        VARCHAR(255)                            NOT NULL,
    "title"         VARCHAR(255)                            NOT NULL,
    "author_id"     BIGINT                                  NOT NULL,
    "course_id"     BIGINT                                  NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk_topic_author_id" FOREIGN KEY ("author_id") REFERENCES "author" ("id") ON DELETE CASCADE,
    CONSTRAINT "fk_topic_course_id" FOREIGN KEY ("course_id") REFERENCES "course" ("id") ON DELETE CASCADE
);

CREATE TABLE public.answer (
    "id"            BIGINT GENERATED BY DEFAULT AS IDENTITY,
    "creation_date" TIMESTAMP WITH TIME ZONE    DEFAULT now()   NOT NULL,
    "message"       TEXT                                        NOT NULL,
    "solution"      BOOLEAN                     DEFAULT false   NOT NULL,
    "author_id"     BIGINT                                      NOT NULL,
    "topic_id"      BIGINT                                      NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk_answer_author_id" FOREIGN KEY ("author_id") REFERENCES "author" ("id") ON DELETE CASCADE,
    CONSTRAINT "fk_answer_topic_id" FOREIGN KEY ("topic_id") REFERENCES "topic" ("id") ON DELETE CASCADE
);