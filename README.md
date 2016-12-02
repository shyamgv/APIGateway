# APIGateway Sample

This application acts as an APIGateway that intercepts the incoming requests and passes the response back to the requested application. This sample application 
demonstrates implementation of the following:

    # ZuulFilter
    # ZuulProxy
    # Spring Boot Application
    # RestController
    # SSO
    # Config Server
	# Register with Service Registry
	
To spin up a Eureka Server instance in your local pls refer to https://spring.io/blog/2015/07/14/microservices-with-spring
	
# Building and Deploying

1) Go to the project root

    $ mvn clean package

2) (Local) Set the active profile as dev in local so that the application looks into application-dev.properties for configuration 
    and then run the app using IDE or command line
    
    Go to (Run/debug Configurations -> Select ZuulProxyApplication -> Environment Variables)
        SPRING_PROFILES_ACTIVE=dev
    $mvn spring-boot:run

3) (Cloud) On PCF - set the following environment variables

	* SPRING_PROFILES_ACTIVE
	* client_id
	* client_secret
	* CF_TARGET

	For ease all these are set using the manifest.yml. If you want to set it manually then use the command like below from CF command line.

		$ cf set-env ZuulProxy CF_TARGET <your pcf login URL>
		
	Create Service:
	===============
	Config-Server, Service Registry and SSO we need to create services in our PCF Space.
	
		$ cf create-service p-service-registry standard service-registry
		$ cf create-service p-config-server standard config-server
		$ cf create-service p-identity XXX SSO
	
	Bind Services
	=============
	Bind the created services to this application
		$ cf bind-service ZuulProxy service-registry
		$ cf bind-service ZuulProxy config-server
		$ cf bind-service ZuulProxy SSO
	For ease its included in the manifest.yml.
	

# Trying It Out

This being an APIGateway connects to microservices. In this case you can refer to the code and documentation of <gitlab url> to implement a microservice that this Gateway is configured to call.

Hit the below end-point:

https://zuulproxy.apps.xxxx.xxxx.com/services/hello where zuulproxy is the host name given in manifest.yml and /services/hello is the end point in the greeting application.
 
Note: Because of the SSO implementation it would automatically redirect and get the auth code and use that to fetch the token and establish a session. When hit for the first time after deployment 
      it will take you to Authorize screen for the permissions to be accepted. Once you authorize it would take you to the response screen.

