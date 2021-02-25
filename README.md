  This is a sample program for Hot Reloading properties in SpringBoot.
  
  Please be aware that due to latest changes in springboot config file handling (https://spring.io/blog/2020/08/14/config-file-processing-in-spring-boot-2-4),
the following property must be added in application.properties: 

  "spring.config.use-legacy-processing: true"

  The server starts on localhost:9999 and after a property change and a page refresh, the new values should be displayed.
