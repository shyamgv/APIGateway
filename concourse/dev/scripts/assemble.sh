#!/bin/bash
set -o errexit
set -o xtrace

TARGET_DIR=$PWD/target
mkdir -p $TARGET_DIR

cd zuulProxyTool

mvn -P dev clean package -DskipTests=true

cp -R . $TARGET_DIR/
