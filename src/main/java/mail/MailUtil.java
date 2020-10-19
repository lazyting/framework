package mail;

import org.slf4j.Logger;
import utils.EmptyUtil;
import utils.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;

public class MailUtil {
    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(MailUtil.class);
    private static String piccEmailUser = "";
    private static String piccEmailPassword = "";
    private static String piccEmailHost = "";
    private static String piccEmailProtocol = "";

    public static void sendEmail(String to, String sendText, String filePath, String mailTitle, String logStr, String fromName) {
        if (EmptyUtil.isEmpty(to, sendText, logStr)) {
            throw new RuntimeException("数据不能为空");
        }
        Transport transport = null;
        try {
            java.util.Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", piccEmailHost);
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.transport.protocol", piccEmailProtocol);
            Session session = Session.getDefaultInstance(properties);
            MimeMessage message = new MimeMessage(session);
            if (StringUtils.isNotEmpty(fromName)) {
                try {
                    message.setFrom(new InternetAddress(piccEmailUser, fromName, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                message.setFrom(new InternetAddress(piccEmailUser));
            }
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(mailTitle);
            File file = null;
            Multipart multipart = new MimeMultipart();
            message.setContent(multipart);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(sendText);
            multipart.addBodyPart(messageBodyPart); //设置邮件内容
            // 设置邮件附件
            if (StringUtils.isNotEmpty(filePath)) {
                file = new File(filePath);
                if (file.exists()) {
                    messageBodyPart = new MimeBodyPart();
                    javax.activation.DataSource source = new FileDataSource(file);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(MimeUtility.encodeText(filePath.substring(filePath.lastIndexOf(File.separator) + 1)));
                    multipart.addBodyPart(messageBodyPart);
                } else {
                    throw new RuntimeException(filePath + "文件不存在");
                }
            }
            transport = session.getTransport();
            message.saveChanges();
            transport.connect(piccEmailHost, piccEmailUser, piccEmailPassword);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception mex) {
            mex.printStackTrace();
        } finally {
            if (EmptyUtil.isNotEmpty(transport)) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            logger.info("finally");
        }
    }
}
