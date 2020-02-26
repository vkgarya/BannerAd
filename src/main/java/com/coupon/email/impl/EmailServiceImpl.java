package com.coupon.email.impl;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.coupon.email.EmailDetails;
import com.coupon.email.EmailService;

@Component
public class EmailServiceImpl implements EmailService {
    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(final EmailDetails details) {
        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            Boolean multipart = !(details.getAttachments().isEmpty());
            helper = new MimeMessageHelper(msg, multipart);
            helper.setTo(details.getToEmail());
            helper.setSubject(details.getTitle());

            helper.setText(details.getContent());

            for (String fileName : details.getAttachments().keySet()) {
                helper.addAttachment(fileName, details.getAttachments().get(fileName));
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(msg);
    }
}
