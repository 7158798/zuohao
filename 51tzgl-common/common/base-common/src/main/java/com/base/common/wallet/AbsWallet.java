package com.base.common.wallet;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

/**
 * Created by lx on 17-4-17.
 */
public abstract class AbsWallet implements Wallet {

    protected WalletParam param;
    private String HMAC_SHA1_ALGORITHM = "HmacSHA1";


    //The easiest way to tell Java to use HTTP Basic authentication is to set a default Authenticator:
    private void authenticator() {
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(param.getACCESS_KEY(), param.getSECRET_KEY().toCharArray());
            }
        });
    }

    public String getSignature(String data, String key) throws Exception {
        // get an hmac_sha1 key from the raw key bytes
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
                HMAC_SHA1_ALGORITHM);

        // get an hmac_sha1 Mac instance and initialize with the signing key
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);

        // compute the hmac on input data bytes
        byte[] rawHmac = mac.doFinal(data.getBytes());
        return bytArrayToHex(rawHmac);
    }

    private String bytArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for (byte b : a)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }

    /**
     * 连接钱包
     * @param method
     * @param condition
     * @return
     * @throws Exception
     */
    public String toWallet(String method,String condition) throws Exception {
        String result;
        String tonce = "" + (System.currentTimeMillis() * 1000);
        authenticator();

        String params = "tonce=" + tonce.toString() + "&accesskey="
                + param.getACCESS_KEY()
                + "&requestmethod=post&id=1&method="+method+"&params="+condition;

        String hash = getSignature(params, param.getSECRET_KEY());

        String url = "http://"+param.getACCESS_KEY()+":"+param.getSECRET_KEY()+"@"+param.getIP()+":"+param.getPORT();
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        String userpass = param.getACCESS_KEY() + ":" + hash;
        String basicAuth = "Basic "
                + DatatypeConverter.printBase64Binary(userpass.getBytes());

        // add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
        con.setRequestProperty("Authorization", basicAuth);

        String postdata = "{\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postdata);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        if(responseCode != 200){
            return "{\"result\":null,\"error\":"+responseCode+",\"id\":1}";
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        inputLine = in.readLine();
        response.append(inputLine);
        in.close();
        result = response.toString();
        return result;
    }
}
