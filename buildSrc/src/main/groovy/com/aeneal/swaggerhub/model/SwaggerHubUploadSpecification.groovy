package com.aeneal.swaggerhub.model

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional

class SwaggerHubUploadSpecification {

    /**
     * An instance is created in the plugin class, because
     * there we have access to the container() method
     * of the Project object.
     */
    @Input
    String version

    @Input
    String inputFileName

    @Input
    @Optional
    String format = "json"

    @Input
    @Optional
    Boolean isPrivate = false

    @Input
    @Optional
    String type = "api"

    @Input
    String name


    /**
     * We need this constructor so Gradle can create an instance
     * from the DSL.
     */
    SwaggerHubUploadSpecification(String name) {
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
