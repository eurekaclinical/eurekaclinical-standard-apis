# Eureka! Clinical Web Application Standard APIs
Standard APIs and related functionality that all Eureka! Clinical projects use

# Version history
## Version 1.0
The initial release provides standards-based implementations for creating JPA entities for managing users, roles and authorization templates; creating data access objects; accessing role information for authorization; reading application configuration; and throwing meaningful exceptions when REST API responses have an error status code.

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

## Releasing it
First, ensure that there is no uncommitted code in your repo. Release it by invoking `mvn release:prepare` followed by `mvn release:perform`. See https://github.com/eurekaclinical/dev-wiki/wiki/Project-release-process for more details.
