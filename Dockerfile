LABEL project.version="1.0-RELEASE" project.name="Desafio" project.release-date="2017-08-05"

MAINTAINER Bruno Rodrigues (brrodrigues@live.com)

echo "Set the base image to java 8"

FROM java:8

echo "Installing required tools or deployment"

RUN apt install maven git -y

echo "Creating deployment folder" 

RUN mkdir -p /home/appuser/deployment

WORKDIR /home/appuser/

echo "Cloning project desafio"

RUN git clone https://github.com/brrodrigues/desafio.git

echo "Compiling project"

WORKDIR /home/appuser/deployment/desafio


