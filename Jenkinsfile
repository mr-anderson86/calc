properties([pipelineTriggers([[$class: 'GitHubPushTrigger']])])

node('calc_build_node') {
   def mvnHome = env.M2_HOME
   stage('Pull changes') { // for display purposes
      // Get some code from a GitHub repository
      git poll: true, url: 'https://github.com/mr-anderson86/calc.git'
   }
   stage('Build and pack') {
      // Run the maven - compile, test, and pack to rpm
      sh "'${mvnHome}/bin/mvn' clean package"
   }
   stage('Install') {
      // Install the rpm
      sh "sudo rpm -Uvh --force ${WORKSPACE}/target/rpm/taboola_calc/RPMS/x86_64/taboola_calc*.rpm"
   }
}