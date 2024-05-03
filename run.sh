# git clone
git clone https://github.com/cse364-unist/projects-group11
# go into git repo and checkout milestone
cd projects-group11 && git checkout milestone2 
# change folder structure to not have projects-group11 as parent directory
cd .. && mv projects-group11/milestone2 . 
rm -rf projects-group11
# go into milestone2 folder
cd milestone2
# make package first to get target folder
mvn package
# create jacoco report
mvn jacoco:report
# run jar file
java -jar ./target/cse364-project-1.0-SNAPSHOT.jar