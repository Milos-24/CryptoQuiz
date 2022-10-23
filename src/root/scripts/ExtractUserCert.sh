#!/bin/bash


cd /home/milos/IdeaProjects/CriptoQuiz/src/root/users/$1

openssl pkcs12 -in $1.p12 -nokeys -clcerts -out $1.cert -passin pass:$3

mv $1.cert /home/milos/IdeaProjects/CriptoQuiz/src/root/$2

echo $1 > loggedInUser.txt

mv loggedInUser.txt /home/milos/IdeaProjects/CriptoQuiz/src/root
