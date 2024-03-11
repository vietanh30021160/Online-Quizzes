package com.swp.online_quizz.Utill;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class EmailUtil {
    @Autowired
    HttpServletRequest request;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email,String otp) throws MessagingException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");
        String domain = extractDomain(request.getRequestURL().toString());
        mimeMessageHelper.setText("""
                <div>
                    <a href="%sverifyaccount?email=%s&otp=%s" target="_blank">click link to verify account</a>
                </div>
                """.formatted(domain,email,otp),true);
        javaMailSender.send(mimeMessage);
    }
    public void sendSetPasswordEmail(String email) throws MessagingException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Set Password");
        String domain = extractDomain(request.getRequestURL().toString());
        mimeMessageHelper.setText("""
                <div>
                    <a href="%ssetpassword?email=%s" target="_blank">click link to set password</a>
                </div>
                """.formatted(domain,email),true);
        javaMailSender.send(mimeMessage);
    }
    public void sendNotication(String email, String subject, String text) throws MessagingException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText("""
                <div>
                    <p>%s</p>
                </div>
                """.formatted(text),true);
        javaMailSender.send(mimeMessage);
    }

    public static String extractDomain(String urlString) {
        try {
            URL url = new URL(urlString);

            String protocol = url.getProtocol();

            String host = url.getHost();

            int port = url.getPort();

            // Kiểm tra xem cổng có tồn tại không
            if (port != -1) {
                return protocol + "://" + host + ":" + port + "/";
            } else {
                return protocol + "://" + host + "/";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
