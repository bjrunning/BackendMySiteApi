build:
	./gradlew clean build

report:
	./gradlew jacocoTestReport

install-dist:
	./gradlew clean installDist

start-dist:
	./build/install/BackendMySiteApi/bin/BackendMySiteApi

.PHONY: build