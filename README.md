# OGProject - Backend

OG Project With [Kinga Stanek](https://github.com/kingastanek) - front You can find [here!](https://github.com/kingastanek/OG-project)
<br>As a first bigger approach to create Service with Spring and Postgres trying to implement some features from game - Ogame

<hr>

## What it contains?
* Facilites, Fleet, Research - CRUD operation 
* Resouces using ExecutorService
* Improving build with Scheduler

## Project uses
* Spring, Spring Boot
* SQL, JDBCTemplate, Flyway
* Postgres, Docker
* Basic Spring Security, Swagger

## How To Start
* Clone repository
* Install Postgres and set name ogdatabase
* Run project to init migration
* Create default user using follow command: <br>
  `CREATE EXTENSION IF NOT EXISTS "uuid-ossp";` <br>
  `INSERT INTO users(user_id, username, password, email) VALUES (uuid_generate_v4(), 'admin', '123', 'admin@gmail.com');`
  
  
  
