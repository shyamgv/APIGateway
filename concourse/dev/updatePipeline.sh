#!/bin/bash

fly -t manulife-ci set-pipeline -p zuulProxyTool-dev -c pipeline.yml -l config.yml

fly -t manulife-ci unpause-pipeline -p zuulProxyTool-dev