package grails.plugins.twilio
import grails.plugins.*
class TwilioGrailsPlugin extends Plugin{
    
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.0.1 > *"
	
    def title = "Twilio Plugin"
    def description = 'Provides SMS sending capabilities to a Grails application.'
    def documentation = "http://grails.org/plugin/twilio"
    def license = "APACHE"
    def organization = [ name: "Novadge", url: "http://www.novadge.com/" ]
    def developers = [ [ name: "Omasiri Joseph Udeinya", email: "omasiri@novadge.com" ]]
    def issueManagement = [url: 'https://github.com/Novadge/twilio-grails3/issues']
    def scm = [url: "https://github.com/Novadge/twilio-grails3" ]
}
