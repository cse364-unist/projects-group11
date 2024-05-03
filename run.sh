git clone https://github.com/cse364-unist/projects-group11

cd projects-group11

git checkout milestone2

cd milestone1

mvn jacoco:report

mvn package

java -jar ./target/cse364-project-1.0-SNAPSHOT.jar