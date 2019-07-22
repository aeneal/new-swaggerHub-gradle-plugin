package com.aeneal.swaggerhub

import com.aeneal.swaggerhub.model.ModelType
import com.aeneal.swaggerhub.model.SwaggerHubConfiguration
import com.aeneal.swaggerhub.model.SwaggerHubDownloadSpecification
import com.aeneal.swaggerhub.model.SwaggerHubUploadSpecification
import com.aeneal.swaggerhub.tasks.GetSwaggerHubSpecificationsTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project

class SwaggerHubPlugin implements Plugin<Project> {

    public static final String EXTENSION_NAME = 'swaggerHubSpecifications'

    public static final String DOWNLOAD_TASK_NAME = 'GetSwaggerHubSpecifications'

    public static final String UPLOAD_TASK_NAME = 'PostSwaggerHubSpecifications'

    private static final String TASK_BASE_NAME = 'swaggerHubDef_'

    private static final String DOWNLOAD_TASK_PATTERN = TASK_BASE_NAME+'GET_%s_from_%s'

    private static final String UPLOAD_TASK_PATTERN = TASK_BASE_NAME+'POST_%s_from_%s'

    private static final String TASK_GROUP_NAME = 'swaggerHubPlugin'



    void apply(final Project project) {

        setupExtension(project)
        createDownloadTasks(project)
        createUploadTasks(project)

    }

    private void setupExtension(final Project project) {


        final NamedDomainObjectContainer<SwaggerHubConfiguration> config = project.container(SwaggerHubConfiguration)


        config.all {
            downloadSpecifications = project.container(SwaggerHubDownloadSpecification)
            uploadSpecifications = project.container(SwaggerHubUploadSpecification)
        }

        project.extensions.add(EXTENSION_NAME, config)

    }

    private void createDownloadTasks(final Project project) {
        def configs = project.extensions.getByName(EXTENSION_NAME)


        configs.all {


            def configInfo = delegate

            downloadSpecifications.all {

                def specInfo = delegate

                def taskName = String.format(DOWNLOAD_TASK_PATTERN, name.capitalize(), configInfo.name.capitalize())

                // Create new task for this node.
                project.task(taskName, type: GetSwaggerHubSpecificationsTask) {

                    group = TASK_GROUP_NAME
                    config = configInfo
                    spec = specInfo

                }

            }

        }

        createDownLoadTask(project)

    }


    private void createUploadTasks(final Project project) {
        def uploadConfigs = project.extensions.getByName(EXTENSION_NAME)


        uploadConfigs.all {

            def configInfo = delegate


            uploadSpecifications.all {

                def specInfo = delegate

                def taskName = String.format(UPLOAD_TASK_PATTERN, name ,configInfo.name)

                // Create new task for this node.
                project.task(taskName, type: GetSwaggerHubSpecificationsTask) {
                    group = TASK_GROUP_NAME
                    config = configInfo
                    spec = specInfo
                }
            }


        }



        createUploadTask(project)
    }



    private void createDownLoadTask(final Project project) {
        def tasks =  project.tasks

        project.task(DOWNLOAD_TASK_NAME) {

            group = TASK_GROUP_NAME

        }

        project.getTasks().findByName(DOWNLOAD_TASK_NAME).dependsOn{tasks.findAll{ task -> task.name.startsWith(TASK_BASE_NAME)}}
        //TODO: If there are no dependent tasks then we should just throw a 'Nothing to Do message'
    }

    private void createUploadTask(final Project project) {
        def tasks =  project.tasks


        project.task(UPLOAD_TASK_NAME) {

            group = TASK_GROUP_NAME

        }

        project.getTasks().findByName(UPLOAD_TASK_NAME).dependsOn{tasks.findAll{ task -> task.name.startsWith(TASK_BASE_NAME)}}

    }


    private ModelType getModelType(String type) {


        if( type == "domain") {
            return ModelType.DOMAIN
        } else {
            return ModelType.API
        }
    }


}