package com.ecov.multinivel.service;

import com.ecov.multinivel.config.ConstantsConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final ConstantsConfig constantsConfig;

    public void SendEmail(String to, String subject, String messageText) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(messageText, true);
        javaMailSender.send(message);
    }
    public void _SendNewAffiliate(String completeName, String email, String tokenConfirm) throws MessagingException{
        System.out.println(completeName);
        String htmlBody = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Reset Password Eco-v</title>"+
                "    <style>\n" +
                "       span, strong, h1, div{\n" +
                "            font-family: sans-serif;\n" +
                "        }\n" +
                "        .card-ecov {\n" +
                "            padding: 50px;\n" +
                "            border-radius: 15px;\n" +
                "            box-shadow: 0 0 0 0 rgba(0, 0, 0, 0.5);\n" +
                "            margin: auto;\n" +
                "            color: gray;\n" +
                "            font-weight: bold;\n" +
                "            width: 350px;\n" +
                "            text-align: justify;\n" +
                "            background-color: white;\n" +
                "        }\n" +
                "        .link-ecov {\n" +
                "            color: gray;\n" +
                "        }"+
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div style=\"background-color: #182236; padding: 50px;\" >\n" +
                "        <div class=\"card-ecov\">\n" +
                "            <p>Hola "+completeName+", se ha creado su usuario con éxito, favor de confirmar dando click en la siguiente liga.</p>\n" +
                "           <br />\n" +
                "            <br />\n" +
                "           <div style=\"text-align: center;\">\n" +
                "                <a class=\"link-ecov\" href=\""+constantsConfig.getUrlFront()+"/confirm-account?token="+tokenConfirm+"\">De click aquí para confirmar su usuario</a>\n" +
                "            </div>"+
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        this.SendEmail(email, "Confirmación de usuario", htmlBody);
    }

    public void _SendRecoveryPassword(String completeName, String email, String token) throws MessagingException {
        String htmlBody = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Reset Password Eco-v</title>"+
                "    <style>\n" +
                "       span, strong, h1, div{\n" +
                "            font-family: sans-serif;\n" +
                "        }\n" +
                "        .card-ecov {\n" +
                "            padding: 50px;\n" +
                "            border-radius: 15px;\n" +
                "            box-shadow: 0 0 0 0 rgba(0, 0, 0, 0.5);\n" +
                "            margin: auto;\n" +
                "            color: gray;\n" +
                "            font-weight: bold;\n" +
                "            width: 350px;\n" +
                "            text-align: justify;\n" +
                "            background-color: white;\n" +
                "        }\n" +
                "        .link-ecov {\n" +
                "            color: gray;\n" +
                "        }"+
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div style=\"background-color: #182236; padding: 50px;\" >\n" +
                "        <div class=\"card-ecov\">\n" +
                "            <p>Hola "+completeName+", se solicto un cambio de contraseña, a continuacion ingrese al siguiente link para cambiar su contraseña, si usted no realizo esta solicitud favor de ignorar este mensaje.</p>\n" +
                "           <br />\n" +
                "            <br />\n" +
                "           <div style=\"text-align: center;\">\n" +
                "                <a class=\"link-ecov\" href=\""+constantsConfig.getUrlFront()+"/reset-new-password?token="+token+"\">De click aquí, para restablecer su contraseña</a>\n" +
                "            </div>"+
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        this.SendEmail(email, "Eco-v recuperación de contraseña", htmlBody);
    }
}
