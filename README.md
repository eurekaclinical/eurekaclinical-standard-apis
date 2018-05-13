# Eureka! Clinical Web Application Standard APIs
[Georgia Clinical and Translational Science Alliance (Georgia CTSA)](http://www.georgiactsa.org), [Emory University](http://www.emory.edu), Atlanta, GA

## What does it do?
It provides standard APIs and related functionality that all Eureka! Clinical projects use. The [Eureka! Clinical Common Web Application Framework](https://github.com/eurekaclinical/eurekaclinical-common)
supports standard web application APIs, including JPA, the Servlet API, JAX-RS, and JSR-330. This project
sets the supported versions of those standards as dependencies. It also provides base classes and interfaces for building
web applications that use those standards, including:
* Reading application configuration from a properties file (`org.eurekaclinical.standardapis.props`)
* An exception for communicating HTTP status for REST API responses (`org.eurekaclinical.standardapis.exception`)
* Interfaces for JPA entities for users, roles and authorization templates (`org.eurekaclinical.standardapis.entity`)
* Interfaces for implementing data access objects, and abstract classes for implementing data access objects for users, roles and authorization templates (`org.eurekaclinical.standardapis.dao`)
* A filter for getting the user's roles using JPA and assigning them to the user principal (`org.eurekaclinical.standardapis.filter`)

## Version 2.0 development series
Latest release: [![Latest release](https://maven-badges.herokuapp.com/maven-central/org.eurekaclinical/eurekaclinical-standard-apis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.eurekaclinical/eurekaclinical-standard-apis)

The goal of the 2.0 series is to have sufficient functionality for all Eureka! Clinical web applications to depend on eurekaclinical-standard-apis.

## Version history
### Version 1.0
The initial release provides standards-based implementations for creating JPA entities for managing users, roles and authorization templates; creating data access objects; accessing role information for authorization; reading application configuration; and throwing meaningful exceptions when REST API responses have an error status code. It uses the standard versions specified by the [Eureka! Clinical Standard Dependencies parent pom](https://github.com/eurekaclinical/eurekaclinical-parent-standard-deps), which are:
* JPA 2.1 (Java Persistence)
* Servlet API 3.0.1
* JAX-RS 2.0.1 (Java API for RESTful Web Services)
* JSR-330 (standard @Inject and @Provider annotations)

## Build requirements
* [Oracle Java JDK 8](http://www.oracle.com/technetwork/java/javase/overview/index.html)
* [Maven 3.2.5 or greater](https://maven.apache.org)

## Runtime requirements
* [Oracle Java JRE 8](http://www.oracle.com/technetwork/java/javase/overview/index.html)
* [Tomcat 7](https://tomcat.apache.org)

## Building it
The project uses the maven build tool. Typically, you build it by invoking `mvn clean install` at the command line. For simple file changes, not additions or deletions, you can usually use `mvn install`. See https://github.com/eurekaclinical/dev-wiki/wiki/Building-Eureka!-Clinical-projects for more details.

## Maven dependency
```
<dependency>
    <groupId>org.eurekaclinical</groupId>
    <artifactId>eurekaclinical-standard-apis</artifactId>
    <version>version</version>
</dependency>
```

## Developer documentation
* [Javadoc for latest development release](http://javadoc.io/doc/org.eurekaclinical/eurekaclinical-standard-apis) [![Javadocs](http://javadoc.io/badge/org.eurekaclinical/eurekaclinical-standard-apis.svg)](http://javadoc.io/doc/org.eurekaclinical/eurekaclinical-standard-apis)
* [Javadoc for version 1.0](http://javadoc.io/doc/org.eurekaclinical/eurekaclinical-standard-apis/1.0)

## Getting help
Feel free to contact us at help@eurekaclinical.org.
