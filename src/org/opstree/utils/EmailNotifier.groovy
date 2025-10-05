package org.opstree.utils

class EmailNotifier implements Serializable {
    def script

    EmailNotifier(script) {
        this.script = script
    }

    void mail(Map params) {
        script.emailext(
            to: params.to,
            subject: params.subject,
            body: params.body
        )
    }
}
