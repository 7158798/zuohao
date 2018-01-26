package net.chrone.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class HttpHostnameVerifier implements HostnameVerifier{

	public HttpHostnameVerifier(){
	}

	public boolean verify(String hostname, SSLSession session){
		return true;
	}
}
