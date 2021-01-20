package br.com.dmsec.starter.tracing.configuration

import io.opentracing.contrib.java.spring.jaeger.starter.TracerBuilderCustomizer
import br.com.dmsec.starter.tracing.customizer.ScopeManagerTracerBuilderCustomizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import sun.tools.java.Constants.tracing

@Configuration
@AutoConfigureBefore(name= ["io.opentracing.contrib.java.spring.jaeger.starter.JaegerAutoConfiguration"])
@ConditionalOnProperty(value= ["app.tracing.enabled"], havingValue = "true", matchIfMissing = true)
@PropertySource("classpath:application.yml")
class TracingAutoConfiguration {

    @Value("\${spring.application.name}")
    private lateinit var name: String

    @Configuration
    class ScopeManagerTraceConfiguration {

        @Bean
        fun scopeManagerJaegerTracerCustomizer(): TracerBuilderCustomizer = ScopeManagerTracerBuilderCustomizer()
    }
}
