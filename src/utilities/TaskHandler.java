package utilities;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import dao.jdbc.BookDescriptionDaoJDBC;
import dao.jdbc.BookLoanDaoJDBC;
import dao.jdbc.UserDaoJDBC;
import model.BookLoan;

//1. Data restituzione < Data corrente & !arrearsPayed = false (invia mail, setArrears(adjustArrear(data di restituzione))
//2. Data restituzione < Data corrente & arrearsPayed = true (invia mail, onlineArrearExceded)

public class TaskHandler {
	Calendar today;
	Timer timer;

	public TaskHandler() {
		today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 2);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);

		timer = new Timer();
		timer.schedule(new ArrearsTask(), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
	}

	class ArrearsTask extends TimerTask {

		@Override
		public void run() {
			BookLoanDaoJDBC bookloan_dao = new BookLoanDaoJDBC();
			UserDaoJDBC user_dao = new UserDaoJDBC();

			List<BookLoan> bookloans = bookloan_dao.findAll();

			for (BookLoan loan : bookloans) {
				if (loan.getReturn_date()
						.before(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
					// Caso 1
					if (!loan.isArrear_payed()) {
						bookloan_dao.setArrears(loan.getId());
						String mail_message = "Gentile utente, il termine per la restituzione di "
								+ loan.getBook().getBookDescription().getTitle()
								+ " e' terminato e potresti incorrere nel pagamento di una mora."
								+ "\nSaluti, Biblioteca INGSW";
						MailHandler.send(user_dao.getbyKey(loan.getUser_id()), "Attenzione! Hai un libro da restiture",
								mail_message);
						// Caso 2
					} else {
						bookloan_dao.onlineArrearExceeded(loan.getId());
						String mail_message = "Gentile utente, il termine per la restituzione di "
								+ loan.getBook().getBookDescription().getTitle()
								+ " e' terminato e potresti incorrere nel pagamento di una mora."
								+ "\nSaluti, Biblioteca INGSW";
						MailHandler.send(user_dao.getbyKey(loan.getUser_id()), "Attenzione! Hai un libro da restiture",
								mail_message);

					}
				}
			}

		}

	}

	public static void main(String args[]) {
		new TaskHandler();
		System.out.println("[TaskHandler] Tasks scheduled.");

	}

}
