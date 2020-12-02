# Logback Custom Appender for Graylog's GELF

A simple example of Logback's Appender for Graylog Extended Log Format (GELF). The interface used in Graylog for input is Http. This project is tested with Java 8.

## Features
- Logback's Appender to send log the Graylog's GELF Http Interface
- Unit Test


## Build
- Use mvn package to build the module into jar file
> mvn clean package

- Put the resulting jar in your classpath
- Add these dependencies in your pom.xml:
<dependency>
   <groupId>ch.qos.logback</groupId>
   <artifactId>logback-classic</artifactId>
   <version>1.2.3</version>
  </dependency>
  <dependency>
   <groupId>com.konghq</groupId>
   <artifactId>unirest-java</artifactId>
   <version>3.3.00</version>
  </dependency>
  

## Configuration
- Please check the logback.xml example in the src/main/resources
- There are 4 configuration for the appender:
1. graylog_http_url, this is the URL of the Graylog's GELF Http Interface
2. hostname, this is the hostname to be submitted to Graylog for every log. Default is localhost
3. gelf_version, the GELF version, default is 1.1
4. verify_ssl, to verify_ssl/not when using Https. Default is true.

## Feedback
For feedback, please raise issues in the issue section of the repository. Periodically, I will update the example with more real-life use case example. Enjoy!!.

