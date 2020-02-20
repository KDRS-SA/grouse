# Grouse
Grouse consists of both a REST-api and a web-based GUI.


Grouse is a Java application so you need both maven and Java.
Depending on which route you pick you might also need to install other
dependencies.  The project is developed on a Linux machine with Apache Maven
3.2.1 and Java 1.8. Please make sure both of these are installed before you
attempt to run the project. You can verify your versions with:

    mvn --version
    java -version

## Getting the code

The latest version of the code is available on Github at
[KDRS-SA/grouse](https://github.com/KDRS-SA/grouse.git).
If you haven't cloned the project then:

    git clone https://github.com/KDRS-SA/grouse.git

## Makefile

This option is a wrapper around the maven command. To compile the 
application and start it automatically, from the top level directory run :

    make         
    
## Maven

Please note that maven will automatically download all dependencies (jar files)
and put them in a directory ~/.m2. If you are uncomfortable with this, please
check the pom.xml files to find out which jar files will be downloaded.
 
    mvn -Dmaven.test.skip=true clean validate install
    mvn spring-boot:run -Dspring-boot.run.profiles=demo

Note: Here you are starting Grouse with the 'demo' profile. Settings for this
profile are in the file application-demo.yml. The default profile looks for 
a mysql instance. 

    mvn spring-boot:run 

Settings for this profile are in the file application.yml.  
 
You will see a lot of different startup messages, but there should be no
exceptions. (Please let us know if there are any exceptions).

The program should output some thing like the following if everything is 
successful
 	
 	Started GrouseApplication in 15.489 seconds (JVM running for 19.948)

## API

You should be able to see which REST calls are available from the logging 
information that was written to the terminal. Search for Mapped and you can 
easily see a list. You will find something similar to the following:
 
 	`Mapped "{[/krav/{krav:.+}],methods=[GET]}"` 

Grouse uses springs OAuth2 implementation. To interact with Grouse you need to login
and get a token. If you are testing Grouse fresh from a clone, be aware of the following.
It runs on port 9294. An Authorization token is also set for your convenience. You 
should be able to get an Authorization token using the following command:  

    `curl -v -X POST  -H 'Authorization: Basic Z3JvdXNlLWNsaWVudDpzZWNyZXQ=' http://localhost:9294/grouse/oauth/token -d grant_type=password -d username=admin@example.com -d password=password`

## GUI

Grouse uses webjars to download required js resources. You can use make 
install-gui to force download of required js files. But this should also be 
done when make builds the project. You will require a HTTP server to serve web
content. 
