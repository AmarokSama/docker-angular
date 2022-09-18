------------------
--USER & SCHEMA --
------------------

CREATE ROLE poster WITH
    LOGIN
    SUPERUSER
    INHERIT
    CREATEDB
    CREATEROLE
    REPLICATION
    PASSWORD 'poster';

CREATE SCHEMA poster
    AUTHORIZATION poster;

-------------------
-- POSTER SCHEMA --
-------------------

SET SCHEMA 'poster';

------------
-- TABLES --
------------

DROP TABLE IF EXISTS poster;
DROP TABLE IF EXISTS postersize;
DROP TABLE IF EXISTS inventory;

CREATE TABLE postersize
(
    id   serial,
    name character varying(80),
    CONSTRAINT pk_postersize PRIMARY KEY (id)
);
ALTER SEQUENCE postersize_id_seq restart with 7;
CREATE INDEX idx_postersize_name ON postersize (name);


CREATE TABLE poster
(
    id             serial,
    title          character varying(30),
    category       character varying(255),
    description    character varying(255),
    price          real                   NOT NULL,
    rating_count   integer,
    average_rating real,
    size_id        integer                NOT NULL,
    image          character varying(255) NOT NULL,
    FOREIGN KEY (size_id) REFERENCES postersize (id),
    CONSTRAINT pk_poster PRIMARY KEY (id)
);
ALTER SEQUENCE poster_id_seq restart with 14;
CREATE INDEX idx_poster_name ON poster (title);


CREATE TABLE inventory
(
    posterId integer NOT NULL,
    quantity integer NOT NULL,

    FOREIGN KEY (posterId) REFERENCES poster(id),
    CONSTRAINT pk_inventory PRIMARY KEY (posterId)
);


--------
--DATA--
--------

INSERT INTO postersize VALUES (1, '11 x 17');
INSERT INTO postersize VALUES (2, '12 x 18');
INSERT INTO postersize VALUES (3, '16 x 20');
INSERT INTO postersize VALUES (4, '18 x 24');
INSERT INTO postersize VALUES (5, '19 x 27');
INSERT INTO postersize VALUES (6, '20 x 30');
INSERT INTO postersize VALUES (7, '22 x 28');
INSERT INTO postersize VALUES (8, '22 x 34');
INSERT INTO postersize VALUES (9, '24 x 36');
INSERT INTO postersize VALUES (10, '25 x 25');
INSERT INTO postersize VALUES (11, '27 x 40');

INSERT INTO poster (id, title, category, description, price, size_id, image)
VALUES (1,'Christmas Poster','Christmas Poster',
        'Poster for Christmas',25, 10, 'https://cdn.pixabay.com/photo/2021/12/24/08/41/woman-6890711_960_720.jpg');

INSERT INTO inventory (posterId, quantity) VALUES
(1, 20)

