CREATE TABLE usrs (
    id IDENTITY,
    name VARCHAR NOT NULL
);
CREATE TABLE matches (
    id IDENTITY,
    user1 VARCHAR NOT NULL,
    user2 VARCHAR NOT NULL,
    user1Hand VARCHAR,
    user2Hand VARCHAR
);
