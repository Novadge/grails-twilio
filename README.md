Twilio Grails
========

<h2>Description</h2>

The twilio-grails plug-in provides sms sending capability to a Grails application via twilio api. 


<h2>Configuration</h2>

Add your twilio properties to grails configuration file: Example
Assuming you have a twilio account, then add the required information to your grails config file.

<code>

    twilio {
        // Enter your host address
        host = 'https://api.twilio.com'
        apiID = 'enter your api Id'
        apiPass = 'enter your api password'
        smsUrl = '/2010-04-01/Accounts/' + apiID + '/Messages.json'
        number = ""
    }


</code>

<h2>BuildConfig.groovy</h2>
Copy and paste the following to your BuildConfig.groovy File
<code>
compile(group:'org.apache.httpcomponents',name:'httpclient',version:'4.3.6')
         compile(group:'org.apache.httpcomponents',name:'fluent-hc',version:'4.3.6')
         compile(group:'org.apache.httpcomponents',name:'httpclient-cache',version:'4.3.6')
         compile(group:'org.apache.httpcomponents',name:'httpmime',version:'4.3.6')
</code>

<h2>Usage</h2>

Inject smsService into your class

<code>def smsService</code>

<em>smsService</em> is a Grails service that provides a method called send() that can take mapped parameters.
Please note that 'send()' is overloaded 'see http://en.wikipedia.org/wiki/Function_overloading' and can take various variations of parameters. 

<br/>
One simple form is:
<code>
send(Map map)
</code>

Where ......

map contains parameters...
map.to: phone number of recipient eg +1234444444

map.from: your twilio assigned number eg. +09899898989

map.body: "The body of your message"
map.mediaUrl: "Url for any attachment" (optional )

<h2>Example</h2>

An example usage can be seen below.

<code>

    Class YourController{
     
        def smsService
        ...
        def yourMethod(){
            def map = [to:"070987878787",from:"09808000000",body:"SMS BODY"]
            smsService.send(map)
        
        }
    
    }

</code>

