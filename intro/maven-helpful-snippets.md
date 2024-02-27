## Maven helpful snippets 

Generate project structure:
```sh
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4
```

Use lombok library:
```xml
<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <version>1.18.6</version>
  <scope>provided</scope>
</dependency>
```

Add to your pom.xml:
```xml
<!-- Add to 'build' element  -->
	<filters>
      <filter>src/main/resources/config.${env}.properties</filter>
    </filters>

    <!--  resources -->
    <resources>
      <resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
        <includes>
          <include>*.properties</include>
        </includes>
      </resource>
    </resources>
```

Add to your pom.xml:
```xml	
<!-- add to 'project' element -->	
<profiles>
    <profile>
      <id>dev</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <env>dev</env>
      </properties>
    </profile>

    <profile>
      <id>prod</id>
      <properties>
        <env>prod</env>
      </properties>
    </profile>
</profiles>
```
	
Read properties:
```java	
    public static Properties loadProperties() {
        App app = new App();
        Properties prop = app.loadPropertiesFile("config.properties");
        prop.forEach((k, v) -> System.out.println(k + ":" + v));
        return prop;
    }

    public Properties loadPropertiesFile(String filePath) {

        Properties prop = new Properties();

        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            prop.load(resourceAsStream);
        } catch (IOException e) {
            System.err.println("Unable to load properties file : " + filePath);
        }
        return prop;
    }
```
