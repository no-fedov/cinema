CREATE TABLE IF NOT EXISTS movie (
	id serial PRIMARY KEY,
	name varchar(100) NOT NULL,
	description varchar(1000) NOT NULL
);/end_script

CREATE TABLE IF NOT EXISTS place (
	id serial PRIMARY KEY,
	name varchar(4) NOT NULL UNIQUE,
	CONSTRAINT place_name_check CHECK (name ~ '^[A-B]([1-5])$')
);/end_script

CREATE TABLE IF NOT EXISTS session(
	id serial PRIMARY KEY,
	movie_id int REFERENCES movie(id) ON DELETE SET NULL,
	price numeric NOT NULL,
	datetime timestamp NOT NULL
);/end_script

CREATE TABLE IF NOT EXISTS ticket(
	id serial PRIMARY KEY,
	place_id int REFERENCES place(id),
	session_id int REFERENCES session(id),
	is_sold bool
);/end_script