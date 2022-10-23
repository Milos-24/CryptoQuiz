#!/bin/bash

cd /home/milos/IdeaProjects/CriptoQuiz/src/root/$2


openssl genrsa -out private/$1.key 4096
