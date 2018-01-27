package com.base.wallet.utils.lbc;

import com.base.wallet.utils.BTCMessage;
import com.base.wallet.utils.IWalletUtil;
import com.base.wallet.utils.ans.resp.BaseJson;
import com.jucaifu.common.util.JsonHelper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lx on 17-6-1.
 */
public class LBCUtils implements IWalletUtil {
    //用户名
    private  String ACCESS_KEY = null;
    //密码
    private  String SECRET_KEY = null;
    //钱包IP地址
    private  String IP = null;
    //端口
    private  String PORT = null;
    //比特币钱包密码
    private  String PASSWORD = null;

    public LBCUtils(BTCMessage btcMessage){
        this.ACCESS_KEY = btcMessage.getACCESS_KEY();
        this.SECRET_KEY = btcMessage.getSECRET_KEY();
        this.IP = btcMessage.getIP();
        this.PORT = btcMessage.getPORT();
        this.PASSWORD = btcMessage.getPASSWORD();
    }

    private String main(String method,String condition) throws Exception {
        String result = "";
        String tonce = "" + (System.currentTimeMillis() * 1000);
        String url = "http://"+IP+":"+PORT + "/lbryapi";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());

        String postdata = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";
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
        while ((inputLine=in.readLine())!=null){
            response.append(inputLine);
        }
        in.close();
        result = response.toString();
        return result;
    }

    public BaseJson<BigDecimal> wallet_balance() throws Exception {
        String s = main("wallet_balance","[]");
        return JsonHelper.jsonStr2Obj(s,BaseJson.class);
    }

    public BaseJson<String> wallet_new_address() throws Exception {
        String s = main("wallet_new_address", "[]");
        return JsonHelper.jsonStr2Obj(s,BaseJson.class);
    }

    public String transaction_show(String txId) throws Exception {
        String s = main("transaction_show", "[{\"txid\":\"" + txId +"\"}]");
        return s;
    }


    public String transaction_list() throws Exception {
        String s = main("transaction_list","[]");
        return s;
    }

    public String wallet_list() throws Exception {
        String s = main("wallet_list", "[]");
        return s;
    }

    public String send_amount_to_address(BigDecimal amount,String address) throws Exception {
        // 发送金额
        String s = main("send_amount_to_address", "[{\"amount\":\"" + amount +"\"},{\"address\":\"\" + address +\"\"}]");
        return s;
    }

    //public

    public String help(String commands) throws Exception {
        String s = main("help","[{\"command\":\"" + commands +"\"}]");
        return s;
    }

    public String version() throws Exception {
        String s = main("version", "[]");
        return s;
    }

    public String listunspent() throws Exception{
        String s = main("listunspent", "[]");
        return s;
    }

    public static void main(String[] args) throws Exception {
        BTCMessage btcMessage = new BTCMessage() ;
        /*btcMessage.setIP("localhost");
        btcMessage.setPORT("7000");*/
        //
        // btcMessage.setIP("localhost");
        btcMessage.setIP("130.252.101.153");
        btcMessage.setPORT("5279");
        btcMessage.setPASSWORD("sax1314");
        btcMessage.setACCESS_KEY("eth123456");
        btcMessage.setSECRET_KEY("eth123456");
        LBCUtils lbcUtils = new LBCUtils(btcMessage);
        //String str = lbcUtils.version();
        //String str = lbcUtils.transaction_show("b116e8fc03c8cfbb0a65eb6a76d0905b0f7a8e7d8289f5d34bda44221eb04a1e");
        //BaseJson resp = lbcUtils.wallet_balance();
        //System.out.println("----> " + resp.getResult());
        //String str = lbcUtils.help("transaction_show");
        // String str = lbcUtils.help(null);
        //BaseJson base = lbcUtils.wallet_new_address();
        //String str = lbcUtils.transaction_list();
        //String str = lbcUtils.transaction_show("95001809afccede23ebc80df7f04d14fce96d4005e225471050201500ee2e2dd");
        String str = lbcUtils.transaction_list();
        //String str = lbcUtils.transaction_show("eeb23af0ff0c0d1e0ae413960ca84206e7eb225c0377f28ad9206222b53eca66");
        //String str = lbcUtils.help("transaction_show");
        System.out.println("----> " + str);
    }


}
