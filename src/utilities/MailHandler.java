package utilities;

import java.io.IOException;


import com.sendgrid.*;

import model.User;


public class MailHandler {
	
	public static void send(User u, String sub, String message) {
	    Email from = new Email("noreply@biblioteca.ingsw");
	    String subject = sub;
	    Email to = new Email(u.getEmail());
	    Content content = new Content("text/plain", message);
	    Mail mail = new Mail(from, subject, to, content);

	    SendGrid sg = new SendGrid("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");
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      System.out.println("[MailHandler] Mail sent to " + u.getEmail() + ": " + subject + "["+ response.getStatusCode() + "]");
	    } catch (IOException ex) {
	      System.out.println(ex.toString());;
	    }
	  }

}
