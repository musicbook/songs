mvn clean package
#java -jar target/posts-1.0-SNAPSHOT.jar
docker build -t cleptes/songs .
docker stop songs
docker rm songs
docker run -d --name songs -p 8081:8081 cleptes/songs