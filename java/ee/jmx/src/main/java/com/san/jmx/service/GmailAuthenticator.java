package com.san.jmx.service;

import javax.mail.PasswordAuthentication;

public class GmailAuthenticator extends javax.mail.Authenticator{

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("sandeepronjon@gmail.com", "sand1503");
	}
}
