package com.email.main;

import com.email.serviceImpl.EmailService;

public class Main {

	public Main() {
		try {
			testEmail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testEmail() throws Exception {
		System.out.println("Email demo");
		EmailService emailService = new EmailService();
		int emailCount = emailService.getEmailUnReadCount("test", "test");
		//System.out.println("emailCount:"+emailCount);
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
	
}
