-------------------
-- USER & SCHEMA --
-------------------

CREATE ROLE bestellung WITH
    LOGIN
    SUPERUSER
    INHERIT
    CREATEDB
    CREATEROLE
    REPLICATION
    PASSWORD 'bestellung';

CREATE SCHEMA bestellung
    AUTHORIZATION bestellung ;

----------------------
-- SET ORDER SCHEMA --
----------------------

SET SCHEMA 'bestellung';


------------
-- TABLES --
------------


drop table if exists bestellmenge;
drop table if exists bestellungen;


CREATE TABLE bestellungen
(
    id serial,
    userid integer NOT NULL ,
    address character varying(250) NOT NULL,
    status character varying(20),
    totalprice real NOT NULL,
    paymentmethod character varying(20) NOT NULL,
    constraint pk_bestellungen PRIMARY KEY (id)
);

ALTER SEQUENCE bestellungen_id_seq restart with 7;

CREATE TABLE bestellmenge
(
    bestellungid integer not null,
    posterid integer not null,
    menge integer not null ,
    price real not null,
    FOREIGN KEY (bestellungid) REFERENCES bestellungen(id),
    constraint pk_menge PRIMARY KEY (bestellungid, posterid)
);
CREATE INDEX bestellmenge_bestellung_id ON bestellmenge(bestellungid);





