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

ENTRYPOINT mongod --fork --logpath /var/log/mongodb/mongod.log \
    && /bin/bash


######################################################################

# Add your stuff below:

# Install all necessary packages to run your program, such as vim, java 17, maven, etc.
RUN apt-get update && apt-get install -y \
    vim \
    openjdk-17-jdk \
    maven \
    curl \
    git

ENV HOME /root/project
# Create /root/project directory and set it as WORKDIR.
WORKDIR ${HOME}

# Add your run.sh file under WORKDIR.
ADD run.sh run.sh

RUN git clone https://github.com/cse364-unist/projects-group11
RUN cd projects-group11 && git fetch && git checkout -b master ; exit 0
RUN mv projects-group11/milestone3 . && mv projects-group11/frontend . && mv projects-group11/tomcat_configs .
RUN rm -rf projects-group11
RUN cd milestone3
RUN cd milestone3 && mvn clean package

RUN wget https://dlcdn.apache.org/tomcat/tomcat-10/v10.1.24/bin/apache-tomcat-10.1.24.tar.gz
RUN mkdir -p tomcat && tar -zvxf apache-tomcat-10.1.24.tar.gz -C tomcat/ --strip-components=1
RUN cp target/cse364-project.war tomcat/webapps/
RUN mv ../frontend/ tomcat/webapps/
RUN mv -f ../tomcat_configs/* tomcat/conf/
RUN rm -rf ../frontend/ ../tomcat_configs
RUN sh tomcat/bin/catalina.sh start

# Expose the port your app runs on
EXPOSE 8080

# Expose the default MongoDB port
EXPOSE 27017

# A container should execute a bash shell by default when the built image is launched.
CMD [ "/bin/bash" ]