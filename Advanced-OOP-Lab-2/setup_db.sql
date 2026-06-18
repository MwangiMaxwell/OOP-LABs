-- Lab 3: Video Library System (VLS) Database Setup
-- Run this in the XAMPP MySQL shell or phpMyAdmin

-- Create and select the database
CREATE DATABASE IF NOT EXISTS dbVLS;
USE dbVLS;

-- -------------------------------------------------------
-- Table: Genres
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS Genres (
    id       INT          NOT NULL AUTO_INCREMENT,
    genre    VARCHAR(255) NOT NULL,
    isactive BOOLEAN      NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------
-- Table: Movies
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS Movies (
    id       INT          NOT NULL AUTO_INCREMENT,
    genre_id INT          NOT NULL,
    Title    VARCHAR(255) NOT NULL,
    isactive BOOLEAN      NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    FOREIGN KEY (genre_id) REFERENCES Genres(id)
);

-- -------------------------------------------------------
-- Table: Clients
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS Clients (
    id       INT          NOT NULL AUTO_INCREMENT,
    Fullname VARCHAR(255) NOT NULL,
    isactive BOOLEAN      NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------
-- Table: Rentals
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS Rentals (
    id        INT     NOT NULL AUTO_INCREMENT,
    client_id INT     NOT NULL,
    movie_id  INT     NOT NULL,
    Returned  BOOLEAN NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES Clients(id),
    FOREIGN KEY (movie_id)  REFERENCES Movies(id)
);
