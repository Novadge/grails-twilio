includeTargets << grailsScript('_GrailsBootstrap')

target(twilioStart: 'Adds attributes to config') {
    depends(checkVersion, configureProxy, packageApp, classpath)

    def configFile = new File(basedir, 'grails-app/conf/Config.groovy')
    if (!configFile.exists()) {
        return
    }

    configFile.withWriterAppend {
        it.writeLine '''

// Added by the twilio plugiin:
    twilio {
        // Enter your host address
        host = 'https://api.twilio.com'
        apiID = 'enter your api Id'
        apiPass = 'enter your api password'
        smsUrl = '/2010-04-01/Accounts/' + apiID + '/Messages.json'
        number = ""
    }
'''
    }

    event 'StatusUpdate', """
*********************************************************
* Your grails-app/conf/Config.groovy has been updated   *
* with config attributes. You may modify as needed.     *
*********************************************************
"""
}

setDefaultTarget 'twilioStart'
