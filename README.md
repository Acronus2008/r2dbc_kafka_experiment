# POC for Oracle R2DBC
Make operations over Oracle Database using reactive approach.

## Reference Documentation

The following documentations illustrate how to use some features concretely:

* [Spring Data R2DBC](https://spring.io/projects/spring-data-r2dbc#overview)
* [R2DBC Driver for Oracle Database](https://reposhub.com/java/distributed-databases/oracle-oracle-r2dbc.html)
* [Accessing RDBMS with Spring Data R2dbc](https://hantsy.medium.com/reactive-accessing-rdbms-with-spring-data-r2dbc-d6e453f2837e)
* [Overview of docker-compose CLI](https://docs.docker.com/compose/reference/)
* [Kafka Docker](https://hub.docker.com/r/wurstmeister/kafka)

## Platform

- ***Spring Boot***: *2.4.5*
- ***Java***: *11*
- ***Maven***: *3.8.1*
- ***Oracle dependency***: *com.oracle.database.r2dbc:oracle-r2dbc:0.1.0*

## Requirements

- Oracle XE 11g R2
- Apache Kafka
- Zookeper
- Docker
- Compose

### Install Apache Kafka and Zookeper server

In the directory *src/main/resources/kafka_server* execute the fallowing command

```shell
    docker-compose up
```
This command will create a container that will contain a Kafka Broker and a Zookeper that will allow us to consume and produce messages through kafka

###  Database migration

After creating the Oracle XE 11g R2 database, run the dll scripts inside the *src/main/resources/sql* folder  


