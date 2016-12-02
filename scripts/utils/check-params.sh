#!/bin/bash

function check-params() {
  local names=("$@")
  for name in "${names[@]}"; do
    local value=$(eval "echo \${${name}-UNDEFINED}")
    printf "Checking parameter %s..." $name
    if [ "$value" == "UNDEFINED" ]; then
      printf "not defined; you may need to update your secrets!\n"
      CHECK_PARAMS_FAIL="yes"
    elif [ "$value" == "" ]; then
      printf "defined but empty; make sure this is what you want!\n"
    else
      printf "ok\n"
    fi
  done
}

function exit-on-check-params-failure() {
  if [ "$CHECK_PARAMS_FAIL" == "yes" ]; then
    printf "Missing parameters; aborting...\n"
    exit 1
  fi
}
