#!/bin/bash

cd /home/milos/IdeaProjects/CriptoQuiz/src/root/$4

openssl req -new -key private/$1.key -out requests/$1.req -config openssl.cnf -subj "/C=BA/ST=RS/O=Elektrotehnicki fakultet/OU=ETF/CN=$2/emailAddress=$3" -passin pass:sigurnost
openssl ca -in requests/$1.req -out certs/$1.cert -config openssl.cnf -batch
echo "Cert generated at: /home/milos/IdeaProjects/CriptoQuiz/src/root/$4/certs/$1.cert"
openssl ca -gencrl -out crl/$1.list -config openssl.cnf
