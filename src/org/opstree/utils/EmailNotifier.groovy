package org.opstree.utils

class EmailNotifier {
    static void sendMail(String to, String subject, String body) {
        // Jenkins mail step
        mail to: to,
             subject: subject,
             body: body,
             mimeType: 'text/html'
    }
}
