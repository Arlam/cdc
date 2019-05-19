#!/usr/bin/env bash
if [ ! -d "pact" ]; then
    wget -c https://github.com/pact-foundation/pact-ruby-standalone/releases/download/v1.64.1/pact-1.64.1-linux-x86.tar.gz -o -
    tar -xvf pact-1.64.1-linux-x86.tar.gz
fi
sh pact/bin/pact-stub-service server-api/target/pacts/ -p 3001
