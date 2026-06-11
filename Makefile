build:
	mvn clean compile

test:
	mvn clean verify

openapi:
	mvn clean verify -Popenapi
