#!/bin/bash

cd /home/milos/IdeaProjects/CriptoQuiz/src/root/pictures

steghide extract -sf $1.jpg -p sigurnost

cat pitanje$1.txt

rm pitanje$1.txt
