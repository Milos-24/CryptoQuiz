#!/bin/bash

cd /home/milos/IdeaProjects/CriptoQuiz/src/root/rootca2

openssl ca -revoke certs/$1.cert -crl_reason cessationOfOperation -config openssl.cnf

openssl ca -gencrl -out crl/revoke$1.list -config openssl.cnf