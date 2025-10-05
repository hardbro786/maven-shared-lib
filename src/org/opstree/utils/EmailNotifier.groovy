package org.opstree.utils

class EmailNotifier implements Serializable {

    def steps  // Jenkins pipeline context

    EmailNotifier(steps) {
        this.steps = steps
    }

    void mail(Map params) {
        if (!params.to || !params.subject || !params.body) {
            steps.error("Missing required parameters for EmailNotifier.mail()")
        }

        // Jenkins mail step through pipeline context
        steps.mail(
            to: params.to,
            subject: params.subject,
            body: params.body
        )
    }

    void success(Map params) {
        mail([
            to: params.to,
            subject: "SUCCESS: ${params.subject}",
            body: params.body
        ])
    }

    void failure(Map params) {
        mail([
            to: params.to,
            subject: "FAILURE: ${params.subject}",
            body: params.body
        ])
    }
}
