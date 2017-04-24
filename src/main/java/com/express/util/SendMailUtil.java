package com.express.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailUtil {
	// 建立会话
	private MimeMessage message;
	private Session s;

	public SendMailUtil() throws IOException {
		Properties props = System.getProperties();
		props.setProperty(PropertyUtil.getProperty("KEY_SMTP"), PropertyUtil.getProperty("SERVER_SMTP"));
		props.put("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		s = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				try {
					return new PasswordAuthentication(PropertyUtil.getProperty("EmailAddress"),
							PropertyUtil.getProperty("EmailPassword"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		});
		s.setDebug(true);
		message = new MimeMessage(s);
	}
	
	 /**
     * 发送邮件
     * 
     * @param headName
     *            邮件头文件名
     * @param sendHtml
     *            邮件内容
     * @param receiveUser
     *            收件人地址
	 * @throws IOException 
     */
    public void doSendHtmlEmail(String headName, String sendHtml,
            String receiveUser) throws IOException {
        try {
            // 发件人
            InternetAddress from = new InternetAddress(PropertyUtil.getProperty("EmailAddress"));
            message.setFrom(from);
            // 收件人
            InternetAddress to = new InternetAddress(receiveUser);
            message.setRecipient(Message.RecipientType.TO, to);
            // 邮件标题
            message.setSubject(headName);
            String content = sendHtml.toString();
            // 邮件内容,也可以使纯文本"text/plain"
            message.setContent(content, "text/html;charset=GBK");
            message.saveChanges();
            Transport transport = s.getTransport("smtp");
            // smtp验证，用于发邮件的邮箱用户名密码，QQ需到用户设置对应的授权码
            transport.connect(PropertyUtil.getProperty("SERVER_SMTP"), PropertyUtil.getProperty("EmailAddress"), PropertyUtil.getProperty("EmailPassword"));
            // 发送
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("send success!");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
