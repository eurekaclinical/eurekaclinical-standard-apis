# Eureka! Clinical Web Application Standard APIs
Standard APIs and related functionality that all Eureka! Clinical projects use

### Latest development release
The latest development release is 2.0-Alpha-1.

# Version history
## Version 1.0
The initial release provides standards-based implementations for creating JPA entities for managing users, roles and authorization templates; creating data access objects; accessing role information for authorization; reading application configuration; and throwing meaningful exceptions when REST API responses have an error status code. It uses the standard versions specified by the [Eureka! Clinical Standard Dependencies parent pom](https://github.com/eurekaclinical/eurekaclinical-parent-standard-deps), which are:
* JPA 2.1 (Java Persistence)
* Servlet API 3.0.1
* JAX-RS 2.0.1 (Java API for RESTful Web Services)
* JSR-330 (standard @Inject and @Provider annotations)

# What does it do?
The [Eureka! Clinical Common Web Application Framework](https://github.com/eurekaclinical/eurekaclinical-common)
supports standard web application APIs, including JPA, the Servlet API, JAX-RS, and JSR-330. This project
sets the supported versions of those standards as dependencies. It also provides base classes and interfaces for building
web applications that use those standards, including:
* Reading application configuration from a properties file (`org.eurekaclinical.standardapis.props`)
* An exception for communicating HTTP status for REST API responses (`org.eurekaclinical.standardapis.exception`)
* Interfaces for JPA entities for users, roles and authorization templates (`org.eurekaclinical.standardapis.entity`)
* Interfaces for implementing data access objects, and abstract classes for implementing data access objects for users, roles and authorization templates (`org.eurekaclinical.standardapis.dao`)
* A filter for getting the user's roles using JPA and assigning them to the user principal (`org.eurekaclinical.standardapis.filter`)

## Building it
The project uses the maven build tool. Typically, you build it by invoking `mvn clean install` at the command line. For simple file changes, not additions or deletions, you can usually use `mvn install`. See https://github.com/eurekaclinical/dev-wiki/wiki/Building-Eureka!-Clinical-projects for more details.

## Maven dependency
### Latest development release
```
<dependency>
    <groupId>org.eurekaclinical</groupId>
    <artifactId>eurekaclinical-common</artifactId>
    <version>2.0-Alpha-1</version>
</dependency>
```

### Latest final release
```
<dependency>
    <groupId>org.eurekaclinical</groupId>
    <artifactId>eurekaclinical-common</artifactId>
    <version>1.0</version>
</dependency>
```

## Development documentation
* [Javadoc for latest development release](http://javadoc.io/doc/org.eurekaclinical/eurekaclinical-standard-apis) [![Javadocs](http://javadoc.io/badge/org.eurekaclinical/eurekaclinical-standard-apis.svg)](http://javadoc.io/doc/org.eurekaclinical/eurekaclinical-standard-apis)
* [Javadoc for version 1.0](http://javadoc.io/doc/org.eurekaclinical/eurekaclinical-standard-apis/1.0)

## Getting help
Feel free to contact us at help@eurekaclinical.org.
