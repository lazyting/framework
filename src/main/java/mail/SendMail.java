package mail;

import exception.ToolException;
import model.mail.MailModel;
import system.SystemConfig;
import utils.EmptyUtil;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SendMail {
    /**
     * 发送邮件（仅限qq邮箱）
     *
     * @param senderAccount  发件人账户名
     * @param senderPassword 发件人账户密码
     * @param mailModel      邮件信息
     * @throws Exception
     */
    public static void sendMail(String senderAccount, String senderPassword, MailModel mailModel) throws Exception {
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.qq.com");
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        session.setDebug(Boolean.parseBoolean(SystemConfig.getProperty("ifConsoleInfo")));
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session, mailModel);
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(senderAccount, senderPassword);
        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg, msg.getAllRecipients());

        //如果只想发送给指定的人，可以如下写法
        //transport.sendMessage(msg, new Address[]{new InternetAddress("xxx@qq.com")});

        //5、关闭邮件连接
        transport.close();
    }

    /**
     * 获得创建一封邮件的实例对象
     *
     * @param session
     * @return
     * @throws MessagingException
     * @throws Exception
     */
    public static MimeMessage getMimeMessage(Session session, MailModel mailModel) throws Exception {
        if (EmptyUtil.isEmpty(mailModel)) {
            throw new ToolException("邮件交流双方信息为空！！！");
        }
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        String sendMailPerson = mailModel.getSendMailPerson();
        if (EmptyUtil.isEmpty(sendMailPerson)) {
            throw new ToolException("邮件发送人不能为空！！！");
        }
        msg.setFrom(new InternetAddress(sendMailPerson));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        String receiveMailPerson = mailModel.getReceiveMailPerson();
        if (EmptyUtil.isEmpty(receiveMailPerson)) {
            throw new ToolException("邮件收送人不能为空！！！");
        }
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMailPerson));
        String ccMailPerson = mailModel.getCcMailPerson();
        if (EmptyUtil.isNotEmpty(ccMailPerson)) {
            msg.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(ccMailPerson));
        }
        String bccMailPerson = mailModel.getBccMailPerson();
        if (EmptyUtil.isNotEmpty(bccMailPerson)) {
            msg.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(bccMailPerson));
        }
        //设置邮件主题
        msg.setSubject(mailModel.getMailTitle(), "UTF-8");
        //设置邮件正文
        msg.setContent(mailModel.getMailText(), "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        return msg;
    }
}
