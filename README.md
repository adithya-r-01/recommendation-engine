# CueBack Recommender

A project built on top of Java frameworks utilizing User-Based collabarative filtering techniques. This project specifically utilizes Manhattan Distance to create efficient recommendations.

## Requirements
 
This project is built with Java and utilizes Apache Maven. Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information (the pom.xml file). This provides the same functionality as using packages. Much like when using the ArrayList structure in Java you don't necessarily need to provide the source code.

The java version that I am using:

```bash
openjdk version "15.0.2" 2021-01-19
OpenJDK Runtime Environment (build 15.0.2+7-27)
OpenJDK 64-Bit Server VM (build 15.0.2+7-27, mixed mode, sharing)
```

The maven build that I am using:

```bash
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: /Users/adithya/Downloads/apache-maven-3.6.3
Java version: 15.0.2, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-15.0.2.jdk/Contents/Home
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.16", arch: "x86_64", family: "mac"
```

For information. about how to install maven: https://maven.apache.org/

Additionally, it would be beneficial to use intellij as the IDE to open this project's source code as it has the best support for maven projects.

## File Structure

**.idea** - A folder specific to my IDE (ignore)

**src** - Source code directory

**target** - Compiler output (ignore)

**gitattributes** - Ignore

**dataset.csv** - The dataset which is a csv file created by python script reading a mySQL table

**pom.xml** - Maven specfic file (ignore)

## Configuration Options

The driver.java has the main method which runs the program. The configuration option are as follows:

```java
// The first parameter in the ThresholdUserNeighborhood constructor is the similarity threshold
UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.0,similarity, model);
```

```java
// The First argument is the userID and the Second parameter is 'HOW MANY'
List<RecommendedItem> recommendations = recommender.recommend(2, 2);
```

### User-Based Recommendation

To leverage these three parameters use the command line interface. The first parameter is the threshold, the second parameter is the user_id, and the third parameter is the count:

```bash
java Driver 0.0 2 2
```

### Popularity Recommendation

Sometimes it becomes neccesary to get the top N popular prompts. To request this via the CLI the first paramater is the threshold and the second parameter is the count:

```bash
java Driver 0.0 1
```


The output will be printed to the system in the form of:

```bash
RecommendedItem[item:12, value:4.857143]
RecommendedItem[item:14, value:3.357143]
```




