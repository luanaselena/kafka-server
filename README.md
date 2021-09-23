Pasos para iniciar el servidor:
  1.	Descargar Docker.
  2.	Ubicarse en la carpeta “kafka-server” y ejecutar en consola: “docker-compose -f kafka-docker-compose.yml up -d”. Fijarse si en el Docker están corriendo los dos procesos.
  3.	En consola ejecutar los siguientes comandos:
    docker exec -i kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic publish-post
    docker exec -i kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic follow
    docker exec -i kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user
    docker exec -i kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user-followers
    docker exec -i kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic like
    docker exec -i kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic noticias
    docker exec -i kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic my-posts
    docker exec -i kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic likes
    docker exec -i kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic liked
    docker exec -i kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic followed
  4.	Crear un schema en MySQL llamado “socialnetworkdb”. Verificar en el archivo “application.properties” que los datos sean correctos (la base de datos se crea automáticamente     al iniciar Spring).
  5.	En consola: "mvn clean install"
  6.	Iniciar Spring.
  7.	Ejecutar en MySQL el archivo "Script inicial", que contiene usuarios, posts y seguidos.
