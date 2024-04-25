# Use Ubuntu 22.04 as base image
FROM ubuntu:22.04

# Install prerequisites
RUN apt-get update \
    && apt-get install -y wget gnupg2 \
    && rm -rf /var/lib/apt/lists/*

# Import MongoDB public GPG key
RUN wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | apt-key add -

# Add MongoDB repository to sources list
RUN echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu jammy/mongodb-org/6.0 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-6.0.list

# Update package list and install MongoDB
RUN apt-get update \
    && apt-get install -y mongodb-org \
    && rm -rf /var/lib/apt/lists/*

# Create necessary directories
RUN mkdir -p /data/db /data/configdb

# Change ownership of directories
RUN chown -R mongodb:mongodb /data/db /data/configdb

# Add your stuff below:

# # Create /root/project directory
# WORKDIR /root/project

# # Install vim
# RUN apt-get update \
#     && apt-get install -y vim \
#     && rm -rf /var/lib/apt/lists/*

# # Install java 17
# RUN apt-get update \
#     && apt-get install -y openjdk-17-jdk \
#     && rm -rf /var/lib/apt/lists/*

# # Install maven
# RUN apt-get update \
#     && apt-get install -y maven \
#     && rm -rf /var/lib/apt/lists/*

# # Install Curl
# RUN apt-get update \
#     && apt-get install -y curl \
#     && rm -rf /var/lib/apt/lists/*

# # Copy my 'milestone1 directory' and run.sh file under WORKDIR
# RUN mkdir -p /milestone1
# COPY milestone1/. /root/project/milestone1
# COPY run.sh /root/project/

ENTRYPOINT mongod --fork --logpath /var/log/mongodb/mongod.log \
    && /bin/bash


######################################################################

# Add your stuff below:

# Install all necessary packages to run your program, such as vim, java 17, maven, etc.
RUN apt-get update && apt-get install -y \
    vim \
    openjdk-17-jdk \
    maven \
    curl

ENV HOME /root/project
# Create /root/project directory and set it as WORKDIR.
WORKDIR ${HOME}

# Add your ‘milestone1 directory’ under WORKDIR.
# TODO: uncomment this part
# ADD milestone1 milestone1

# Add your run.sh file under WORKDIR.
ADD run.sh run.sh

# Expose the port your app runs on
EXPOSE 8080

# Expose the default MongoDB port
EXPOSE 27017

############### NEED TO DELETE ###############
# RUN cd milestone1 && mvn package
# 1. add pom.xml only here
ADD milestone1/pom.xml milestone1/pom.xml 
# RUN cd milestone1
# 2. start downloading dependencies
RUN cd milestone1 && mvn verify clean --fail-never
# 3. add all source code and start compiling
ADD milestone1/data milestone1/data
ADD milestone1/src milestone1/src

# RUN cd milestone1 && mvn -e package
############### NEED TO DELETE ###############

# A container should execute a bash shell by default when the built image is launched.
CMD [ "/bin/bash" ]