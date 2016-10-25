# APIGateway Sample

This application acts as an APIGateway that intercepts the incoming requests and passes the response back to the requested application. This sample application 
demonstrates implementation of the following:
    - ZuulFilter
    - ZuulProxy
    - Spring Boot Application
    - RestController
    - SSO
    - Config Server
	- Register with Service Registry
	
# Building and Deploying

1) Go to the project root
$ mvn clean package

2) (Local) Run the app using IDE or command line
$mvn spring-boot:run

3) (Cloud) On PCF - set the following environment variables
	- SPRING_PROFILES_ACTIVE
	- client_id
	- client_secret
	- CF_TARGET

	For ease all these are set using the manifest.yml. If you want to set it manually then use the command like below from CF command line.

		$ cf set-env ZuulProxy CF_TARGET https://api.sys.dev.gsdcf.manulife.com
		
	Create Service:
	===============
	Config-Server, Service Registry and SSO we need to create services in our PCF Space.
	
		$ cf create-service p-service-registry standard service-registry
		$ cf create-service p-config-server standard config-server
		$ cf create-service p-identity manulife SSO
	
	Bind Services
	=============
	Bind the created services to this application
		$ cf bind-service ZuulProxy service-registry
		$ cf bind-service ZuulProxy config-server
		$ cf bind-service ZuulProxy SSO
	For ease its included in the manifest.yml.
	

# Trying It Out

This being an APIGateway connects to microservices. In this case you can refer to the code and documentation of https://gitlab.apps.dev.gsdcf.manulife.com/microservices/greeting to implement a microservice that this Gateway is configured to call.

Hit the below end-point:
https://zuulproxy.apps.dev.gsdcf.manulife.com/services/hello where zuulproxy is the host name given in manifest.yml and /services/hello is the end point in the greeting application.
 
# Because of the SSO implementation it would automatically redirect and get the auth code and use that to fetch the token and establish a session. When hit for the first time after deployment 
  it will take you to Authorize screen for the permissions to be accepted. Once you authorize it would take you to the response screen.

