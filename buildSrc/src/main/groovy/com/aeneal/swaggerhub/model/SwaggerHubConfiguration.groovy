package com.aeneal.swaggerhub.model


import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.Optional

class SwaggerHubConfiguration {

    @Input
    @Optional
    int port = 443
    @Input
    @Optional
    String protocol = "https"
    @Input
    @Optional
    String host = "api.swaggerhub.com"
    @Input
    String name
    @Input
    @Optional
    String token

    @Nested
    NamedDomainObjectContainer<SwaggerHubDownloadSpecification> downloadSpecifications

    @Nested
    NamedDomainObjectContainer<SwaggerHubUploadSpecification> uploadSpecifications
    /**
     * We need this constructor so Gradle can create an instance
     * from the DSL.
     */
    SwaggerHubConfiguration(String name) {
        this.name = name
    }

    def downloadSpecifications(final Closure configureClosure) {
        downloadSpecifications.configure(configureClosure)
    }

    def uploadSpecifications(final Closure configureClosure) {
        uploadSpecifications.configure(configureClosure)
    }



    @Override
    String toString() {
        return "SwaggerHubConfiguration {" +
                "port=" + port +
                ", protocol='" + protocol + '\'' +
                ", host='" + host + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                '}'
    }
}
