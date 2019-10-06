Build of application: mvn clean install

War is located in ./assignment-map-web/target (assignment-map-web.war). It could be deployed on any application server, there is no dependence (but tested only on JBoss 7). For run application required only configuration. It is necessary to set path in AS as JNDI resource (name java:/env/assignmentMapConfig). Default configuration you can find in /config/assignment-map.properties

Example of settings in JBoss:
        <subsystem xmlns="urn:jboss:domain:naming:1.4">
            <bindings>
		<simple name="java:/env/assignmentMapConfig" value="C:\workspace\broadcom\config\assignment-map.properties"/>		
            </bindings>
        </subsystem>

Application will be available on http://<host>:<port>/assignment-map-web/index.html

Used technologies:
- Spring (bean, boot, mvc)
- Dozer
- ehCache
- Mockito
- JUnit, JaCoCo
- Maven
- JQuery