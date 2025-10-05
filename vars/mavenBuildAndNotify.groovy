def call(Map config = [:]) {
    // Default values
    def repoUrl = config.get('repoUrl', '')
    def branch = config.get('branch', 'main')
    def mvnCommand = config.get('mvnCommand', 'clean package')
    def recipient = config.get('recipient', 'modihardik19@gmail.com')

    stage('Checkout Code') {
        echo "🔹 Cloning repository: ${repoUrl}"
        echo "🔹 Branch: ${branch}"
        checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${branch}"]],
            userRemoteConfigs: [[url: repoUrl]]
        ])
    }

    stage('Build with Maven') {
        echo "🔹 Running Maven command: ${mvnCommand}"
        try {
            sh "mvn ${mvnCommand}"
            currentBuild.result = 'SUCCESS'
        } catch (err) {
            currentBuild.result = 'FAILURE'
            error "❌ Build failed: ${err}"
        }
    }

    stage('Send Notification') {
        def status = currentBuild.result
        def subject = "Maven Build - ${status}: ${repoUrl}"
        def body = """
        📦 **Maven Build Summary**
        
        🔗 Repository: ${repoUrl}
        🌿 Branch: ${branch}
        🛠 Command: mvn ${mvnCommand}
        📊 Status: ${status}
        
        ✅ Jenkins Job: ${env.JOB_NAME}
        🔁 Build Number: ${env.BUILD_NUMBER}
        🔗 Build URL: ${env.BUILD_URL}
        """

        // Use the helper class for sending mail
        org.opstree.utils.EmailNotifier.sendMail(recipient, subject, body)
    }
}
