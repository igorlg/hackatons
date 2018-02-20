#!/usr/bin/env bash -x

export AWS_PROFILE=profile-sandbox
./gradlew build && java -jar build/libs/gs-spring-boot-0.1.0.jar