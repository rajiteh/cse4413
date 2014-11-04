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

