build:
	mvn clean compile

test:
	mvn clean verify -Dspring.profiles.active="test"

openapi:
	mvn clean verify -Popenapi -Dspring.profiles.active="test"
