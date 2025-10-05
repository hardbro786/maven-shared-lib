def call(Map config = [:]) {
    script {
        echo "🔹 Running Maven command: ${config.mavenCommand ?: 'clean package'}"
        sh "${config.mavenCommand ?: 'mvn clean package'}"

        // Email notifier instance
        def notifier = new org.opstree.utils.EmailNotifier(this)

        notifier.mail([
            to: config.to ?: 'modihardik19@gmail.com',
            subject: "Maven Build - ${currentBuild.currentResult}: ${config.repo ?: env.GIT_URL}",
            body: """
                Job: ${env.JOB_NAME}
                Build Number: ${env.BUILD_NUMBER}
                Status: ${currentBuild.currentResult}
                Repo: ${config.repo ?: env.GIT_URL}
                Console: ${env.BUILD_URL}
            """
        ])
    }
}
