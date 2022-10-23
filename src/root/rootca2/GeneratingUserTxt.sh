#!/bin/bash
#kreiraj datoteku pod nazivom usera u users, u toj datoteci kreiraj txt fajl sa imenom i hashom, pored txt dodaj pkcs u tu datoteku
#//proslijedi username
#//hash i cert.p12
#// username $1, hash $2, password $3



cd /home/milos/IdeaProjects/CriptoQuiz/src/root/$5

mkdir $1
mv $1 /home/milos/IdeaProjects/CriptoQuiz/src/root/users
rm -d $1

echo $1 $2 $4 $5> $1.txt

echo 0 > timesPlayed.txt
mv timesPlayed.txt /home/milos/IdeaProjects/CriptoQuiz/src/root/users/$1

mv $1.txt /home/milos/IdeaProjects/CriptoQuiz/src/root/users/$1

openssl pkcs12 -export -inkey private/$1.key -in certs/$1.cert -name $1 -out $1.p12 -certfile bundle.cert -passout pass:$3

mv $1.p12 /home/milos/IdeaProjects/CriptoQuiz/src/root/users/$1

rm $1.p12

echo "aes-256-cbc kriptografija" > lozinka1.txt
openssl rsa -in private/$1.key -out private/$1.pubkey -pubout
openssl rsautl -encrypt -in lozinka1.txt -out lozinka.txt -inkey private/$1.pubkey -pubin
mv lozinka.txt /home/milos/IdeaProjects/CriptoQuiz/src/root/users/$1
rm lozinka1.txt

