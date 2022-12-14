SET statement_timeout = '5s';

BEGIN TRANSACTION;

ALTER TABLE public.course
    ADD COLUMN IF NOT EXISTS name_tokens TSVECTOR;

COMMIT;