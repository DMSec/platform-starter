package br.com.dmsec.starter.swagger.configuration

import com.google.common.base.Predicates
import br.com.dmsec.starter.commons.annotation.SwaggerIgnore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Documentation
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@PropertySource("classpath:application.yml")
class SwaggerAutoConfiguration {

    @Autowired
    private val environment: Environment? = null

    @Value("\${swagger.api.info.title}")
    lateinit var title: String

    @Value("\${swagger.api.info.description}")
    lateinit var description: String

    @Value("\${swagger.api.info.version}")
    lateinit var version: String

    @Value("\${spring.application.name}")
    lateinit var appName: String

    @Bean
    fun api(): Docket {
        val apiInfo = Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .apis(Predicates.not(RequestHandlerSelectors.withClassAnnotation(SwaggerIgnore::class.java)))
                .apis(Predicates.not(RequestHandlerSelectors.withMethodAnnotation(SwaggerIgnore::class.java)))
                .build().apiInfo(apiEndpointsInfo())
        if(environment!!.activeProfiles.contains("production")) apiInfo.pathMapping(appName)
        return apiInfo
    }

    private fun apiEndpointsInfo(): ApiInfo =
            ApiInfoBuilder()
                    .title(title)
                    .description(description)
                    .version(version)
                    .build()

}