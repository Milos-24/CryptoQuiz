#!/bin/bash

# $1 username
# $2 password usera

cd /home/milos/IdeaProjects/CriptoQuiz/src/root/users/$1

openssl pkcs12 -in $1.p12 -nocerts -out $1.key -passin pass:$2 -passout pass:$2

openssl rsautl -decrypt -in lozinka.txt -inkey $1.key -passin pass:$2

rm $1.key