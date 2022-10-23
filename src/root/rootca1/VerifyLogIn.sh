#!/bin/bash

cd /home/milos/IdeaProjects/CriptoQuiz/src/root/rootca1


openssl verify -CAfile bundle.cert -verbose $1.cert

rm $1.cert
