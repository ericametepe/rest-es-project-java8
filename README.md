POC on professional training search
================================
  Prerequisites
 - JDK 8
 -  Maven 3
 - Eclipse or IntelliJ IDEA


  1. Install & Start Elasticsearch (Spring Data supports : elasticsearch-1.7.3) add your ES host & port to /src/main/resources/elasticsearch.properties

  2. Start the App : mvn clean spring-boot:run

  3. Init the index with REST Api (by Swagger) : http://localhost:8080/docs/index.html#!/service%2Fformations/populate.
  
  4. Test http://localhost:8080/docs/index.html#!/service%2Fformations/getAll
  
  5. Then ad references : http://localhost:8080/docs/index.html#!/service%2Fformations/updateWithRefDatas
  
  
  Enjoy search of professional trainings




