package myapp.ebank.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * The type Email util.
 */
@Service
public class EmailUtil {
	@Autowired
	final private JavaMailSender javaMailSender;

	private final String body = " A new account was created using your email on e- bank app,  please enter following code to verify: ";
	private final String subject = " User verification email";

	/**
	 * Instantiates a new Email util.
	 *
	 * @param javaMailSender the java mail sender
	 */
	public EmailUtil(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * Send mail response entity.
	 *
	 * @param toEmail the to email
	 * @param token   the token
	 * @return the response entity
	 */
	public ResponseEntity<Object> sendMail(String toEmail, int token) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(toEmail);
			msg.setSubject(subject);
			msg.setText(body + token);
			javaMailSender.send(msg);
			return new ResponseEntity<>("user added and email sent successfully please verify it", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getCause());
			return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
