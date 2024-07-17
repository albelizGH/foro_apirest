package com.alejobeliz.proyectos.forohub.infra.springdoc;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Foro Hub Api")
                        .description("""
                                API Rest para el desafío final Foro Hub Api de Alura.
                                                                       Crea un usuario, inicia sesión con tu correo electrónico y contraseña elegidos, y obtén un token para acceder a los controladores.
                                                                       [LinkedIn](https://linkedin.com/in/alejo-beliz) [GitHub](https://github.com/albelizGH/)
                                          """)
                        .contact(new Contact()
                                .name("Alejo Beliz")
                                .url("https://linkedin.com/in/alejo-beliz")
                        )
                );
    }

}
