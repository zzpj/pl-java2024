package pl.zzpj.solid.isp.contactbook.violation;

class Emailer {
	
	
	public void sendMessage(Contact emailable, String subject, String body) {
		
		String emailAddress = emailable.getEmailAddress();
		
		sendEmail(emailAddress, subject, body);
	}

	
	
	
	
	private void sendEmail(String emailAddress, String subject, String body) {
		// TODO Auto-generated method stub
		
	}
}