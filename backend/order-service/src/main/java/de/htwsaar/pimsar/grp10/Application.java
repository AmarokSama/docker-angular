package de.htwsaar.pimsar.grp10;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.server.EmbeddedServer;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import lombok.extern.slf4j.Slf4j;

@OpenAPIDefinition(
    info = @Info(
            title = "order-service",
            version = "1.0"
    ),
   servers = {
      @Server(
         description = "Server description",
         url = "{protocol}://{host}:{port}",
         variables = {
            @ServerVariable( name = "host",
               description = "host",
               defaultValue = "localhost" ),
            @ServerVariable( name = "port",
               description = "port",
               defaultValue = "32582" ),
            @ServerVariable( name = "protocol",
               description = "protocol",
               defaultValue = "http" )
         }
      )
   }
)
@Slf4j
public class Application
{

    public static void main( String[] args )
    {
        ApplicationContext applicationContext = Micronaut.run( Application.class, args );
        logApplicationStartup( applicationContext );
    }

    private static void logApplicationStartup( ApplicationContext context )
    {
        EmbeddedServer server = context.getBean( EmbeddedServer.class );
        ApplicationConfiguration application = context.getBean( ApplicationConfiguration.class );
        String protocol = server.getScheme();
        int serverPort = server.getPort();
        String hostAddress = server.getHost();

        log.info(
           "\n----------------------------------------------------------\n\t" +
           "Application '{}' is running! Access URLs:\n\t" +
           "Local: \t\t\t{}://localhost:{}\n\t" +
           "External: \t\t{}://{}:{}" +
           "\n----------------------------------------------------------",
           application.getName().orElse( null ),
           protocol,
           serverPort,
           protocol,
           hostAddress,
           serverPort
        );
    }
}
