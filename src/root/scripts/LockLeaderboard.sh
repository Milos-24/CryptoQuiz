#!/bin/bash

# $1 simetricni algoritam
# $2 kljuc
cd /home/milos/IdeaProjects/CriptoQuiz/src/root/

openssl $1 -in leaderboard.txt -out cryptleaderboard.txt -k $2

echo "successfull lock"

rm leaderboard.txt