# Simple calculator application + CI/CD using aws, jenkins, and docker

## Project description -
1. Setup a CI flow for the application
2. Build and deploy the project on the local system

## Prerequisites
----
### Prerequisites - calc application (codewise):  
1. Project is based on Java (1.8) and including tests  
2. Code should be pushed to Github (public repo)

### Prerequisites - servers
Launch 2 servers in AWS console:
1. Jenkins server - ubuntu server
2. App server - RedHat server

## Prerequisites in app server:
### Server general configuration -
1. AWS security group - expose ssh access from Jenkins server, and from any other you wish (My ip/ all / other)

### Application - 
1. Install jdk 1.8, maven (3.3.9 is good), git 

### Github -   
1. Generate ssh key  
2. Add public key to Github deploy keys, and allow write access.

## Prerequisites in Jenkins server:  
### Server general configuration -   
1. AWS security group - expose ssh access from whichever your choice is (My ip / all / other)
2. AWS security group - expose TCP 80 access (or your Jenkins app port) from whichever your choice is (My ip / all / other)

### Jenkins - 
1. Install docker on the server, and luanch a Jenkins container with -p 80:8080 (or whichever ports you choose).
2. In the Jenkins container, Generate ssh key
3. Copy public key and add to authorized_keys in in the app server
4. In Jenkins ui - create ssh credentials to app account using key above
5. In Jenkins ui - create a node called 'calc_build_node', connecting via ssh with key (use credentials above).
6. In the node configuration - check "Environment variables" and add variable named 'M2_HOME' and value maven dir on app server.

### Github -  
1. Add <jenkins url>/github-webhook/ to Github Webhooks.
2. In Jenkins ui - install plugins: github-plugin, git plugin (should come already with the docker container)
3. AWS security group - expose TCP 80 access (or your Jenkins app port) from Github ip

----
# CI/CD -
Creation of CI/CD job -  
* Create a new pipeline job in Jenkins. job configuration:   
  - Pipeline definition - Pipeline script from SCM
  - SCM - git
  - Repository URL - the link to current repository.
  - Credentials - none.
  - After saving configuration, run job manually once.
    ## CI:  
      - Job is being triggered on any push event on git repository.
      - git pull 
      - Prepare a release, updating the project versions.  
      - Compile the project and run all the tests, and packs project into rpm file.
    ## CD:  
      - Installs the rpm on the local system.  


### Ehe end :) ENJOY...
