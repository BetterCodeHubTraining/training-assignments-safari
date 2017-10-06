package eu.sig.safari.exercises.stubs.simianarmy;

public class AWSEmailNotifier {

	public AWSEmailNotifier(AmazonSimpleEmailServiceClient sesClient) {
		// TODO Auto-generated constructor stub
	}

	protected boolean isValidEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void sendEmail(String email, String subject, String emailBody) {
		// TODO Auto-generated method stub
		
	}

	public String buildEmailSubject(String to) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getCcAddresses(String to) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSourceAddress(String to) {
		// TODO Auto-generated method stub
		return null;
	}
}
