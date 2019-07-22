package com.aeneal.swaggerhub.client

import com.aeneal.swaggerhub.model.ModelType
import io.swagger.v3.parser.OpenAPIV3Parser
import io.swagger.v3.parser.core.models.AuthorizationValue
import io.swagger.v3.parser.core.models.ParseOptions
import io.swagger.v3.parser.core.models.SwaggerParseResult
import org.gradle.api.GradleException

class SwaggerHubClient {

    static SwaggerHubClient getInstance(){
        return new SwaggerHubClient()
    }

    SwaggerParseResult getSwaggerHubDefinition(String token, String format, boolean resolve, String protocol, String host, int port, ModelType type, String owner, String name, String version ) {
        AuthorizationValue apiToken = new AuthorizationValue()
                .keyName("Authorization")  //  the name of the authorization to pass
                .value(token)        //  the value of the authorization
                .type("header")             //  the location, as either `header` or `query`

        AuthorizationValue formatHeader = new AuthorizationValue("Accept", "application/"+format, "header")

        ParseOptions opts = new ParseOptions()
        opts.setResolve(resolve)

        String definitionLocation = protocol+"://"+host+":"+port+"/"+domainOrApiSegment(type)+"/"+owner+"/"+name+"/"+version


        try {

            return new OpenAPIV3Parser().readLocation(definitionLocation, Arrays.asList(apiToken, formatHeader), opts);
        }
        catch (Exception e) {
            throw new GradleException(e.toString())
        }

    }

    private String domainOrApiSegment(ModelType type) {
        if(type == ModelType.DOMAIN) {
            return "domains"
        } else {
            return "apis"
        }
    }
}
