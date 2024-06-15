# git clone
git clone https://github.com/cse364-unist/projects-group11
# go into git repo and checkout milestone
cd projects-group11 && git fetch && git checkout -b master
# change folder structure to not have projects-group11 as parent directory
cd ../ && mv projects-group11/milestone2 ./ && mv projects-group11/frontend ./
# heelo wodl
rm -rf projects-group11
# go into milestone2 folder
cd milestone2
# make package first to get target folder
mvn package
# create jacoco report
mvn jacoco:report
# run jar file
java -jar ./target/cse364-project-1.0-SNAPSHOT.jar
# tomcat apache
wget https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.89/bin/apache-tomcat-9.0.89.tar.gz
mkdir -p tomcat && tar -zvxf apache-tomcat-9.0.89.tar.gz -C tomcat/ --strip-components=1
cp target/cse364-project.war tomcat/webapps/
mv -v ../frontend/* tomcat/webapps/
cd tomcat/bin
sh catalina.sh run