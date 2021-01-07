#Build

* clean install

#Start

Please choose a path where neo4j should place it's datastore

* java -jar survey-*.jar -Dparam.neo4j.storagePath=..../data/neo4j

#Neo4j browser

It's a deprecated version for including the neo4j browser, see the DatabaseConfiguration.java for details.
You can reach the application at: http://localhost:7474

* username: neo4j
* password: neo4j

#Example query for data creation

CREATE (s:SurveyNode { question: "The Answer to the Ultimate Question of Life, The Universe, and Everything?" }),
(u:User),
(u)-[:isAuthorOf]->(s),
(s)-[:isAuthoredBy]->(u)