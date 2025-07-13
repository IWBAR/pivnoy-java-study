package ttv.poltoraha.pivka.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ttv.poltoraha.pivka.entity.MyUser;

@Service
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(MyUser user, String token) {
        String verificationUrl = "http://app.com/verify?token=" + token;
        String subject = "Подтвердите ваш email";
        String text = "Для подтверждения перейдите по ссылке: " + verificationUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
