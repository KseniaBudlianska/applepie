CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE recipe
(
    id   UUID NOT NULL
        CONSTRAINT recipe_id_pkey PRIMARY KEY DEFAULT uuid_generate_v4(),
    name TEXT NOT NULL
);