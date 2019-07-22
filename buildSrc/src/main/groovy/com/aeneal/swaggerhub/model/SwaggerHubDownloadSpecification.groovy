package com.aeneal.swaggerhub.model

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional

class SwaggerHubDownloadSpecification {

    /**
     * An instance is created in the plugin class, because
     * there we have access to the container() method
     * of the Project object.
     */
    @Input
    String version

    @Input
    @Optional
    String outputDirectory

    @Input
    @Optional
    String outputFileName

    @Input
    @Optional
    String format = "json"

    @Input
    @Optional
    Boolean resolve = false

    @Input
    @Optional
    String type = "api"

    @Input
    String name


    /**
     * We need this constructor so Gradle can create an instance
     * from the DSL.
     */
    SwaggerHubDownloadSpecification(String name) {
        this.name = name
    }

    @Override
    String toString() {
        return "SwaggerHubSpecification {" +
                "version='" + version + '\'' +
                ", outputDirectory='" + outputDirectory + '\'' +
                ", outputFileName='" + outputFileName + '\'' +
                ", format='" + format + '\'' +
                ", resolve=" + resolve +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}'
    }
}
