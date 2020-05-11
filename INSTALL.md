# Grouse
Grouse consists of both a REST-api and a web-based GUI.

Grouse is a Java application so you need both maven and Java.
Depending on which route you pick you might also need to install other
dependencies.  The project is developed on a Linux machine with Apache Maven
3.6.0 and Java 11. Please make sure both of these are installed before you
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

## Third party requirements

Grouse requires the availability of the asciidoc and pandoc programs to generate the
requirements document. The command grouse uses to generate the requirements document is:

    asciidoctor --backend docbook --out-file - requirements.adoc |pandoc --from docbook --to odt  --output requirements.odt
     
## API

You should be able to see which REST calls are available from the logging 
information that was written to the terminal. Search for Mapped and you can 
easily see a list. You will find something similar to the following:
 
 	Mapped "{[/krav/{krav:.+}],methods=[GET]}"

Grouse uses springs OAuth2 implementation. To interact with Grouse you need to login
and get a token. If you are testing Grouse fresh from a clone, be aware of the following.
It runs on port 9294. An Authorization token is also set for your convenience. You 
should be able to get an Authorization token using the following command:  

    `curl -v -X POST  -H 'Authorization: Basic Z3JvdXNlLWNsaWVudDpzZWNyZXQ=' http://localhost:9294/grouse/oauth/token -d grant_type=password -d username=admin@example.com -d password=password`

## GUI

To run and test the GUI you will need a webserver like [Apache](https://httpd.apache.org/) or [Nginx](https://www.nginx.com/)
And this server will need to be configured to use deep links, this means that when the browser tries to load links within
the application like _grouse/Login_ it wont actualy try to serve the browser a file named Login.html but it will instead serve index.html.
The settup of this varies from web server to web server, but an easy setup for Apache is to include a _.htaccess_ file on the same level as _index.html_
containing the following: 

	<IfModule mod_rewrite.c>
		RewriteEngine On
		RewriteBase /
		RewriteRule ^index\.html$ - [L]
		RewriteCond %{REQUEST_FILENAME} !-f
		RewriteCond %{REQUEST_FILENAME} !-d
		RewriteRule . index.html [L]
	</IfModule>

This will essentialy tell the webserver that if the client tries to access a file it cannot find it will serve index.html instead.
For this to work though you will also need to have the `mod_rewrite` module from Apache installed and `AllowOveride all` enabled 
on the directory of the files. 

You might need `sudo` for all of theese.

To install the `mod_rewrite` module simply:

	a2enmod rewrite
	
To enable `AllowOveride all` if your webserver is largely unconfigured you can do this simply by changing the default configuration file a bit.

Open `/etc/apache2/sites-enabled/000-default.conf` and paste:

	<Directory "/var/www/html">
		AllowOverride All
	</Directory>
	
within the `VirtualHost` block, assuming that the directory of _index.html_ is `/var/www/html`

Finally do a `service apache2 restart` and everything should work nicely.
