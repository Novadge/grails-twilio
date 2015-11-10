package grails.plugins.twilio
import grails.plugins.*
class TwilioGrailsPlugin extends Plugin{
    
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.0.1 > *"
	
    def title = "Twilio Plugin"
    def description = 'Provides SMS sending capabilities to a Grails application.'
    def documentation = "http://grails.org/plugin/grails-twilio"
    def license = "APACHE 2.0"
    def organization = [ name: "Novadge", url: "http://www.novadge.com/" ]
    def developers = [ [ name: "Omasirichukwu Joseph Udeinya", email: "omasiri@novadge.com" ]]
    def issueManagement = [url: 'https://github.com/Novadge/grails-twilio/issues']
    def scm = [url: "https://github.com/Novadge/grails-twilio" ]
}
