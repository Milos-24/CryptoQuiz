#!/bin/bash

# $1 simetricni algoritam
# $2 kljuc

cd /home/milos/IdeaProjects/CriptoQuiz/src/root/

openssl $1 -in cryptleaderboard.txt -d -out leaderboard.txt -k $2

echo "successfull unlock"