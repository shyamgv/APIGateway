#!/bin/bash

fly -t manulife-ci set-pipeline -p zuulProxyToolDB-dev -c pipeline.yml -l config.yml

fly -t manulife-ci unpause-pipeline -p zuulProxyToolDB-dev