package com.aeneal.swaggerhub.tasks

import com.aeneal.swaggerhub.helper.SwggerHubGetHelper
import com.aeneal.swaggerhub.model.ModelType
import com.aeneal.swaggerhub.model.SwaggerHubConfiguration
import com.aeneal.swaggerhub.model.SwaggerHubDownloadSpecification
import groovy.json.JsonGenerator
import groovy.json.JsonOutput
import io.swagger.v3.oas.models.OpenAPI
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.TaskAction

import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

class GetSwaggerHubSpecificationsTask extends DefaultTask {

    @Nested
    SwaggerHubConfiguration config

    @Nested
    SwaggerHubDownloadSpecification spec


    /**
     * Simple implementation to show we can
     * access the Server and Node instances created
     * from the DSL.
     */
    @TaskAction
    void deploy() {

        logger.info("Getting ${spec.name} from ${config.name} on host ${config.host}" )
        logger.info(config.toString())
        logger.info(spec.toString())


        OpenAPI api


        try {
            SwggerHubGetHelper getHelper = new SwggerHubGetHelper()

            api = getHelper.downloadDefinition(config.token, spec.format, spec.resolve, config.protocol, config.host, config.port, getModelType(spec.type), config.name, spec.name, spec.version)

        } catch (GradleException e) {
            throw new GradleException(e.getMessage(), e)
        }

        try {



            String fileName = buildFileName(spec.outputDirectory,spec.outputFileName, spec.name, spec.version, spec.format)
            File file = new File(fileName)

            setUpOutputDir(file)

            def generator = new JsonGenerator.Options()
                    .excludeNulls()
                    .build()


            Files.write(Paths.get(file.toString()), JsonOutput.prettyPrint(generator.toJson(api)).getBytes(Charset.forName("UTF-8")))

        } catch (IOException e) {
            throw new GradleException(e.getMessage(), e)
        }





    }




    private void setUpOutputDir(File file) {


        final File parentFile = file.getParentFile()
        if (parentFile != null) {
            parentFile.mkdirs()
        }
    }

    private static String buildFileName(String outputDir, String fileName, String spec, String version, String format) {
        String fileNameFormat = "%s-%s.%s"

        if(outputDir == null) {
            outputDir = ""
        }


        if( fileName != null) {
            return outputDir+"/"+fileName
        } else {
            return  outputDir+"/"+String.format(fileNameFormat,spec,version,format)
        }



    }

    private ModelType getModelType(String type) {


        if( type == "domain") {
            return ModelType.DOMAIN
        } else {
            return ModelType.API
        }
    }



}