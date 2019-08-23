/**
 * 
 */
package ml.bootcode.profileapp.util;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author sunnyb
 *
 */
@Component
public class EmailSender {

	private MailSender mailSender;

	/**
	 * @param mailSender
	 */
	public EmailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * Sends email.
	 * 
	 * @param to
	 * @param body
	 */
	@Async
	public void send(String to, String body) throws MailException {

		// get a thread safe copy of template.
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setText(body);

		mailSender.send(message);
	}
}
