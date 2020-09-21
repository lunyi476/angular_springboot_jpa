This two projects are angular/springboot/jpa(hibernate) application.

(1) for server side springboot/jpa project, in eclipse, setup maven plugin, then import maven 
project from this project folder.
to run/debug it as normal java application from main in SpringBootWebApplication.java class.
to run/debug test as normal junit test from SpringBootIntegrationTest.java class.

default database is h2 in memory embedded. 
Postgresql also was tested.

To change diffrent database , go to META-INF/persistence.xml file and follow existed format to edit it.


(2) for client side angular project, setup visual studio codes, then open this project from folder.
from terminal of visual code screen run:  npm start   (may be need to run first :  npm install)
browser will  open application.


