#!/usr/bin/env sh

[ -z "$1" ] && echo "Space not given." && exit 1

cf api https://api.run.pivotal.io
cf auth $CF_BUILD_USER $CF_BUILD_USER_PASSWORD

cf create-space $1 # Create space so we can assure that we can switch to it safely. Doesn't error if it already exists.
cf target -o nobt.io -s $1