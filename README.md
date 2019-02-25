# Simple calculator application + CI/CD Process

## Description:

The Purpose of this repository is to show more of the power of Maven.  
This repository represents a Jenkins pipeline, which runs a whole CI-CD process, which builds a very simple calculator.  
The final output is the calculator application, which all you need to do is login to the machine, and run command "calc", and you're in the calculator app. 

Screenshot of final result can be found in the screenshots directory :-)

Meaning:
1. The pipeline first builds the application using Maven.
2. Updates the app and pom versions (prepare stage).
3. builds the application and packs it into rpm.
4. Install the rpm.
5. Final output: end user can login to the machine, and run command "calc", and enter in the calculator app. .

The repository holds:
1. The Java application (all under src)
2. Jenkinsfile - scripted (Maybe also declerative in the future)
3. A simple python script which  prints a spesific build details like node, status, duration and more  
(under "scripts" dir)

## Prerequisites:
1. Jenkins of course, with relevant node configured and running.
2. The builder machine needs git, JDK 1.8, Maven (3.3.9 is good).  
(Tested only on RedHat server, but it all might work also on ubuntu, CentOS and others)
4. Add \<your jenkins url\>/github-webhook/ into your repository Webhooks.  
(Go to Settings -> Webhooks -> and add there your webhook).

### 1. The calculator application:
* Based on Java 1.8 (Uses Maven for the build stage)
* when entering the application, it prints out the current version  
(Both files src/main/java/resources/project.properties and src/main/java/Calc.java, and it's mentioned as "resource" in the main pom file).
* The command in prepare stage actually updates the app version in the pom, and therefore it's reflected in the project.properties file.
* Usage examples (after entering "calc" command):  
A = 2  
B = 3  
C = A + B  
D = ++A  
hit ctrl+D, and the application will print the values of A,B,C and D.  

### 2. The Jenkinsfile:
* It starts automatically on each git hub push event  
(but you do need to run the job manually for the first time, after that this configuration is set for good.)
* Agent is running on "calc_build_node"  
(if you fork this repo, you can always change the node name)
* Pull changes from the git repository
* Prepare release stage: Prepare a release, updating the project and pom versions.
* Build and pack stage:  
Builds the project and run all the tests.  
Packs the relevant files into rpm file.  
(All of the prepare, build and pack stages are running only with Maven).
* Install stage: install the rpm (update mode).

### 2. The get_job_details.py script (under scripts dir):
* Prints job name
* Prints build number
* Prints "started by"
* Prints build status
* Prints build duration
* Prints the node name
* It also outputs all into csv file.

## Other Ideas:
* you can always install Nexus or Artifactory and deploy the jar/rpm files there.
* Then you can run the install stage on a different host (node): pull the rpm from Nexus/Artifactory and install it there.

### The end, enjoy :)

