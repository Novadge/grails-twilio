grails.project.work.dir = 'target'

grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {
    inherits 'global'
    log 'warn'

    repositories {
        mavenLocal()
        grailsCentral()
        mavenCentral()
    }

    dependencies {
        String httpclientVersion = '4.3.6'
        compile "org.apache.httpcomponents:httpclient:$httpclientVersion"
        compile "org.apache.httpcomponents:fluent-hc:$httpclientVersion"
        compile "org.apache.httpcomponents:httpclient-cache:$httpclientVersion"
        compile "org.apache.httpcomponents:httpmime:$httpclientVersion"

        // compile 'cglib:cglib-nodep:2.2'
        // Temporary inclusion due to bug in 2.4.2
        // compile("cglib:cglib-nodep:2.2.2") {
        //     export = false
        // }
    }

    plugins {
        build ':release:3.0.1', ':rest-client-builder:2.0.3', {
            export = false
        }
    }
}
