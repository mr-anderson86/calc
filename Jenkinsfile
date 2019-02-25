properties([
   pipelineTriggers([
      [$class: "GitHubPushTrigger"]
   ]),
   disableConcurrentBuilds()
])

node('calc_build_node') {
   def mvnHome = env.M2_HOME
   stage('Pull changes') { // for display purposes
      // Get some code from a GitHub repository
      checkout scm
   }
   stage('Prepare release') {
      //Prepare a release, updating the project versions.
      sh "'${mvnHome}/bin/mvn' --batch-mode release:prepare"
   }
   stage('Build and pack') {
      // Run the maven - compile, test, and pack to rpm
      sh "'${mvnHome}/bin/mvn' clean package"
   }
   stage('Install') {
      // Install the rpm
      sh "sudo rpm -Uvh --force ${WORKSPACE}/target/rpm/calc/RPMS/x86_64/calc*.rpm"
   }
}
