
git clone https://github.com/cse364-unist/projects-group11
cd projects-group11 && git fetch && git checkout -b master 
cd ../ && mv projects-group11/milestone3 . && mv projects-group11/tomcat_configs . 
rm -rf projects-group11
# go into milestone3 folder
cd milestone3
# make package first to get target folder
mvn package
# create jacoco report
mvn jacoco:report
wget https://dlcdn.apache.org/tomcat/tomcat-10/v10.1.24/bin/apache-tomcat-10.1.24.tar.gz
mkdir -p tomcat && tar -zvxf apache-tomcat-10.1.24.tar.gz -C tomcat/ --strip-components=1
cp target/cse364-project.war tomcat/webapps/
mv ../frontend/ tomcat/webapps/
mv -f ../tomcat_configs/* tomcat/conf/
rm -rf ../frontend/ ../tomcat_configs
cd tomcat/bin
sh catalina.sh run