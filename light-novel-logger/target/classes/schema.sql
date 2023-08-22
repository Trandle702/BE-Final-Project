DROP TABLE IF EXISTS series_category;
DROP TABLE IF EXISTS light_novel;
DROP TABLE IF EXISTS series;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS illustrator;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS user;


CREATE TABLE user (
	user_id int NOT NULL AUTO_INCREMENT,
	first_name varchar(60) NOT NULL,
	last_name varchar(60) NOT NULL,
	email varchar(256) NOT NULL,
	PRIMARY KEY (user_id)  
);


CREATE TABLE author (
	author_id int NOT NULL AUTO_INCREMENT,
	first_name varchar(60) NOT NULL,
	last_name varchar(60),
	PRIMARY KEY (author_id)
);

CREATE TABLE illustrator (
	illustrator_id int NOT NULL AUTO_INCREMENT,
	first_name varchar(60) NOT NULL,
	last_name varchar(60),
	PRIMARY KEY (illustrator_id)
);

CREATE TABLE category (
	category_id int NOT NULL AUTO_INCREMENT,
	category_name varchar(60),
	PRIMARY KEY (category_id)
);

CREATE TABLE series (
	series_id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	author_id int NOT NULL,
	illustrator_id int NOT NULL,
	series_name varchar(256) NOT NULL,
	PRIMARY KEY (series_id),
	FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE,
	FOREIGN KEY (author_id) REFERENCES author (author_id) ON DELETE CASCADE,
	FOREIGN KEY (illustrator_id) REFERENCES illustrator (illustrator_id) ON DELETE CASCADE
);

CREATE TABLE light_novel (
	light_novel_id int NOT NULL AUTO_INCREMENT,
	series_id int NOT NULL,
	volume_number int NOT NULL,
	page_count int NOT NULL,
	description varchar(256),
	PRIMARY KEY (light_novel_id),
	FOREIGN KEY (series_id) REFERENCES series (series_id) ON DELETE CASCADE
);

CREATE TABLE series_category (
	series_id int NOT NULL,
	category_id int NOT NULL,
	FOREIGN KEY (series_id) REFERENCES series (series_id) ON DELETE CASCADE,
	FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE,
	UNIQUE KEY (series_id, category_id)
);