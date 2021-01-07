#Build

* mvn clean package
* mvn clean package -Pdocker (produces also a docker image)

* mvn clean deploy (publish jars to nexus.prodyna.com)
* mvn clean deploy -Pdocker (publishes also a docker image to dockerhub.prodyna.com)

#Start