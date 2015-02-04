/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import grails.util.GrailsNameUtils
import grails.util.Metadata

includeTargets << new File("$twilioPluginDir/scripts/_T2Common.groovy")
target(twilioStart: 'adds attributes to config') {
	depends(checkVersion, configureProxy, packageApp, classpath)

	
	
	updateConfig()

	printMessage """
*********************************************************
* Your grails-app/conf/Config.groovy has been updated   *
* with config attributes.                               *
* You may modify as needed.                             *
******************************************************* *
"""
}
private void updateConfig() {

    def configFile = new File(appDir, 'conf/Config.groovy')
    if (configFile.exists()) {
        configFile.withWriterAppend {
            it.writeLine '\n// Added by the twilio plugiin:'
            it.writeLine """
                twilio{
                    // Enter your host address
                    host = 'https://api.twilio.com'
                    apiID = 'enter your api Id'
                    apiPass = 'enter your api password'
                    smsUrl = '/2010-04-01/Accounts/'+ apiID + '/Messages.json'
                }
            """
			
        }
    }
}

setDefaultTarget 'twilioStart'
