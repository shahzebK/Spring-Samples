DROP TABLE reviews IF EXISTS;
DROP TABLE movies IF EXISTS;
DROP TABLE users IF EXISTS;

CREATE TABLE users (
  id         INTEGER IDENTITY PRIMARY KEY,
  userName VARCHAR(30),
  password  VARCHAR(30),
  firstName VARCHAR(30),
  lastName  VARCHAR(30)
);

CREATE TABLE movies (
  id         INTEGER IDENTITY PRIMARY KEY,
  movieName VARCHAR(100),
  createdBy  INTEGER
);

CREATE TABLE reviews (
  id         INTEGER IDENTITY PRIMARY KEY,
  review VARCHAR(100),
  movie INTEGER,
  createdBy  INTEGER
);
ALTER TABLE movies ADD CONSTRAINT fk_movies_users FOREIGN KEY (createdBy) REFERENCES users (id);
ALTER TABLE reviews ADD CONSTRAINT fk_reviews_users FOREIGN KEY (createdBy) REFERENCES users (id);
ALTER TABLE reviews ADD CONSTRAINT fk_reviews_movies FOREIGN KEY (movie) REFERENCES movies (id);

