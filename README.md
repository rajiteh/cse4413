#CSE4413

##Week 1
Basics. Setting up VM, etc.
###Tomcat
- Coyote : Request handler
- Catalina : Servlet engine
- Jasper : Templating engine

##Week 2
- WebContent : Contains meta data. Files are public-accessible.

###WEB-INF (web.xml)
Configuration unique to this app. 

- Welcome file list : Default file names to resolve when user requests root path.
- Error page : Provide an error code or exception type and matching page.

###META-INF

##Week 3
### Packages
- Seperation of concerns.
- Communication between packages.

Seperation of packages in tomcat.

1. Controller
2. Analytics
3. Servlets
4. JSPX
5. Models (Business Rules) - Pojo 
		
### MVC

#### Controller
- Finite state machine
- Must be simple.

#### Model
- Is a POJO
- Contains business logic
- Is application agnostic (Acts as a plugin)
- Model gets complex in terms of logic 
- Should not store session unique data

#### View
- JSP / JSPX

### Pub/Sub
- *Context Scope* - Aka Application scope, available app-wide. Typed.
- *Request Scope* - Limited to current request.
- *Subscribe* - Usinga listener

### Jasper
- JSTL
- Expression Language

##Week 4

####Ad-hoc Changes
- Peripheral to the business.
- Quick changes. Pilotting a feature (?)
- Ad-hoc changes should not affect the main app (no recompile)


###Filters
- Can be hooked in to any stage of the app (req -> ctrl -> view)
- Facilitate ad-hoc changes. 
- Continue/Abort the chain.
- Can redirect requests to other views/ctrllers


##Appendix
- **Pojo** : Plain Old Java Object
- **Session** : `session.setMaxInactiveInterval()`

## Week 5

### Javascript

## Week 6

### Bean
- Pojo
- Private Attribute
- Getter and Setter

## DAO (Data access object) / DAL (Data Access Layer)
- Uses Connections, Statement, Execute

## Week 7

### XML
- Message Transport
- Self describing
- Self validating
- Declarative language (Not procedural)
- Commincate data between independent heterogeneous systems
- Strict parsing syntax

#### Well formed?
- Only one root
- Strict nesting. 
- Tag name start with letter.
#### Valid?
- Schema validation with grammar
- Name, order and values of tags
#### Parsing
#### Binding
### XML Schema
- Primitive Types (Like javva + more)
- Simple Types : Provide validation restrictions such as bounds
- Sequence tag ensures the order of elements.

## Week 8

### XSL
- Declarative styling/formatting guide for XML
- Can inject css and js.

## Custom Tags
- Content manipulation within JSP

## Week 10

### Fragmented View
- jsp includes

### Front controller pattern
- forward urls using patterns

## Week 11

### htaccess
- Use PAM (pluggable authentication module)
- Perform authentication by checking the response code.
- SSL is important
- POST request
3


Code appearing in this project is entirely written by <rajiteh@gmail.com> while following the class curriculum. The author makes no guarantee of the accuracy or the correctness of the code. 

The MIT License (MIT)  
Copyright (c) 2014 Raj Perera(@rajiteh)  
