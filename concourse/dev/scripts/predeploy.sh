#!/bin/bash
set -o errexit
set -o xtrace

ls -latr

cf -v

cf login --skip-ssl-validation -a $api -u $username -p $password -o $organization -s $space

cf create-service p-service-registry standard $serviceRegistryName
cf create-service p-config-server standard $configServerName
cf create-service p-circuit-breaker-dashboard standard $circuitBreakerName