# Novi Sad Parliament system

Novi Sad Parliament system is simple web-based application about simulating the way parliament functions. You are able to propose legislation, an amendment to the law, search laws and amendments, withdraw them, and simply simulate parliament session and voting. 
Technology stack used is:

  - Play framework 1.2.5 server
  - MarkLogic XML database
  - JAXB 
  - XQuery
  - xPath
  - Java DOM parser
  - XSLT
  - XSL-FO
  - Angular
App is result of a teamwork and need some improvements to be fulyl functional.

Installation:
  - Install play framework 1.2.5
  - We didn't use maven for dependncies, so you'll have to find libs yourself or find them on some of the first commits. They are removed now.
  - Install bower dependencies: 
      ``` 
      $ bower install 
      ```
  - You need to set connection properties for marklogic database
  - Start server: 
      ```
      $ play run XML2016 
      ```
