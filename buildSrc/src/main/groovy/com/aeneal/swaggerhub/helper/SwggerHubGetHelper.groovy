package com.aeneal.swaggerhub.helper

import com.aeneal.swaggerhub.client.SwaggerHubClient
import com.aeneal.swaggerhub.model.ModelType
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.parser.core.models.SwaggerParseResult
import org.gradle.api.GradleException

class SwggerHubGetHelper {


    OpenAPI downloadDefinition(String token, String format, boolean resolve, String protocol, String host, int port, ModelType type, String owner, String name, String version) {

        SwaggerParseResult res

        try {

            res = SwaggerHubClient.getInstance().getSwaggerHubDefinition(token, format,resolve, protocol, host, port, type, owner, name, version)

            if (res.getOpenAPI() != null) {

                //Check for parsing Errors:
                // Basic check that If its an API and Paths are missing then its fairly fatal
                if (type == ModelType.API && res.getMessages().size() > 0 && res.getOpenAPI() != null) {

                    for (String m : res.getMessages()) {
                        if (m.contains("attribute paths is missing")) {
                            throw new GradleException(m)
                        }
                    }

                }

                // Basic check that If its a DOMAIN and Components are missing then its fairly fatal
                if (type == ModelType.DOMAIN && res.getMessages().size() > 0 && res.getOpenAPI() != null) {

                    for (String m : res.getMessages()) {
                        if (m.contains("attribute components is missing")) {
                            throw new GradleException(m)
                        }
                    }

                }


            } else {
                //Check for Download Errors
                if (res.getMessages().size() > 0 && res.getOpenAPI() == null) {
                    for (String m : res.getMessages()) {
                        if (m.contains("unable")) {
                            throw new GradleException(m)
                        }
                    }
                }
            }

        } catch (GradleException e) {
            throw new GradleException(e.getMessage())
        }


        return res.getOpenAPI()

    }

}
