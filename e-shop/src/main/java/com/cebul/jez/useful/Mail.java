package com.cebul.jez.useful;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * Klasa wysyła mail w którym znajduje się link potwierdzajacy torzsamość i uaktywniający
 * konto użytkownika.
 * używa mechanizmu DI do wstrzykiwania zależnosći
 * @author Mateusz
 *
 */
@Component
public class Mail
{
	@Autowired
	private MailSender mailSender;
	 
	/**
	 * Setter ustawiajacy wartość pola mailSender
	 * @param mailSender
	 */
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
 
	/**
	 * Wysyła maila na podany adres, używana do wysyłania prostych wiadomosci nie zawierających HTML'a
	 * @param from z jakiego maila ma zostac wysłany mail
	 * @param to na jaki adres email ma zostać wysłana wiadomość
	 * @param subject temat wiadomosći
	 * @param msg właściwa treść wiadomości
	 * @throws MessagingException wyrzuca wyjatek gdy wysłanie maila jest niemożliwe
	 */
	public void sendMail(String from, String to, String subject, String msg) throws MessagingException {
 
		SimpleMailMessage message = new SimpleMailMessage();
 
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);	
		

	}
	/**
	 * Wysyła maila na podany adres, umożliwia wysyłanie wiadomości zawierajach tagi HTML
	 * @param url zawiera tag <a> przygotowany w taki sposób aby umożliwić aktywację konta użytkownika
	 * @param from mail z jakiego nalezy wysłać wiadomość
	 * @param to mail na jaki należy wysłać wiadomość
	 * @param subject temat wiadomości
	 * @param msg właściwa treść wiadomości
	 * @throws Exception wyjatek jest wyrzucany gdy nie można wysłać wiadomości
	 */
	public void sendMimeMessage(String url, String from, String[] to, String subject, String msg) throws Exception{
		  MimeMessage mime = ((JavaMailSenderImpl) this.mailSender).createMimeMessage();
		  MimeMessageHelper helper = new MimeMessageHelper(mime, true);
		  helper.setFrom(from);
		  helper.setTo(to);
		  helper.setSubject(subject);
		  
		  //String adress = "http://"+pageContext.request.serverName+":"+pageContext.request.serverPort+pageContext.request.contextPath;
		  String htmlText = "<p>Aby konto zostalo aktywowane prosimy kliknac ponizszy link:</p>" +
		  		"<a href='"+url+"'>Aktywuj konto</a>";
		    helper.setText(htmlText,true);
		    ((JavaMailSenderImpl)this.mailSender).send(mime);
		    }
}
