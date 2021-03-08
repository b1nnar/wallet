
### Setup

#### Apache Kafka
Download and unzip Apache Kafka 2.7.0 (`kafka_2.13-2.7.0.tgz`) from https://kafka.apache.org/downloads.
Go to Kafka directory and run:
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```
then, in another terminal window
```
bin/kafka-server-start.sh config/server.properties
```

#### PostgreSQL
Download and install PostgreSQL 13 from https://www.postgresql.org/download/.
Start postgresql and make sure is running on `localhost:5434`.
Make sure password for user `postgres` is `postgres`.
Create table `wallet` on database `postgres`, schema `public`:
```
CREATE TABLE wallet
(
id character varying(64)   NOT NULL,
balance      numeric(16,8) NOT NULL,
CONSTRAINT   wallet_pkey   PRIMARY KEY (id)
);
```

### Run Apps
```
java -jar bin/iron-sheep-wallet-s1-1.0.0-SNAPSHOT-shaded.jar
```
then, in another terminal window
```
java -jar bin/iron-sheep-wallet-s2-1.0.0-SNAPSHOT-shaded.jar
```

HTTP service WADL should be available at `http://localhost:8080/application.wadl`

Use POSTMAN or something similar to make requests:
```
GET  http://localhost:8080/wallet/{id}
POST http://localhost:8080/wallet/credit with body {"id": "1", "amount": 99}
POST http://localhost:8080/wallet/debit  with body {"id": "1", "amount": 10}
```
