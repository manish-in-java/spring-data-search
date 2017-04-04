Spring Data Search Extensions
=============================

This is an umbrella project for providing a Spring Data style programming
interface to popular Java-based search engines, such as, Compass and Apache
Solr.

Spring Data Search Extensions make it easier for application developers to
integrate Java-based search technologies by providing a familiar, Spring Data
style programming interface, while abstracting the developers away from the
intricate details of the underlying search technologies. 


I. Objectives
-------------
This project has the following objectives:

* Provide a means for integrating Java-based search engine technologies into
[Spring](http://www.springsource.org)-based Java applications;
* Provide a programming interface along the lines of that provided by the
[Spring Data project](http://www.springsource.org/spring-data);
* Provide alternate styles of using search engines in Spring-based Java
applications, such as `Template`, `Repository`, etc.;
* Provide an intuitive yet flexible mechanism for configuring underlying
Search technologies.


II. Background
--------------
It is a common requirement for applications to provide
[full-text search](http://en.wikipedia.org/wiki/Full_text_search) on data
stored within the application.  In this context, the term **full text search**
means the ability to search the complete text content stored by an application
and not just selective data fields such as record names and titles.