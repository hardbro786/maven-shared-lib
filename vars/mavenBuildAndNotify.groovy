stage('Maven Build & Notify') {
    steps {
        dir("${env.POM_DIR}") {
            mavenBuildAndNotify(
                to: EMAIL_TO,
                mavenCommand: MAVEN_CMD,
                repo: env.GIT_URL
            )
        }
    }
}
