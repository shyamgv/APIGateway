#!/bin/bash

# Push a new docker image
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

docker $DOCKER_FLAGS tag zuul-proxy:latest 10.234.24.211:443/zuul-proxy
docker $DOCKER_FLAGS push 10.234.24.211:443/zuul-proxy

rm -rf $DOCKER_BUILD_TMP_DIR
