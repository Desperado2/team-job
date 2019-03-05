package com.desperado.teamjob.service;

import com.desperado.teamjob.domain.ProjectTemplate;

public interface MailService {

     void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to, String subject, String content);

    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);

    void sendTemplateMail(String to,String subject,ProjectTemplate projectTemplate);
}
