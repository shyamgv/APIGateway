#!/bin/bash

# Make a new docker image
#set -o xtrace
#set -o errexit

source utils/check-params.sh
source utils/docker-vars.sh

printf "Checking docker-specific variables (defined in scripts/utils/docker-vars.sh)"
params=( \
  DOCKER_MACHINE_NAME \
  DOCKER_HOST \
  DOCKER_TLS_VERIFY \
  DOCKER_CERT_PATH \
  DOCKER_BUILD_TMP_DIR \
  DOCKER_FLAGS
)

check-params "${params[@]}"; 

mkdir -p "$DOCKER_BUILD_TMP_DIR"
pushd $DOCKER_BUILD_TMP_DIR

rm -rf ./*
cp $PROJECTPATH/APIGateway/Dockerfile .
cp -R $PROJECTPATH/APIGateway ./APIGateway

docker $DOCKER_FLAGS rmi -f zuul-proxy:latest || true
docker $DOCKER_FLAGS build -t zuul-proxy:latest .
