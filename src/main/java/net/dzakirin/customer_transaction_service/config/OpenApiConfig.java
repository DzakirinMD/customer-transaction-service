package net.dzakirin.customer_transaction_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "LinkedIn",
                        url = "https://www.linkedin.com/in/dzakirin-rahim/"
                ),
                description = "OpenApi documentation for customer-transaction-service",
                title = "Customer Transaction Service",
                version = "1.0.0",
                license = @License(
                        name = "MIT license",
                        url = "https://github.com/DzakirinMD/customer-transaction-service?tab=MIT-1-ov-file"
                )
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:10001"
                )
        },
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT"
)
public class OpenApiConfig {

}
