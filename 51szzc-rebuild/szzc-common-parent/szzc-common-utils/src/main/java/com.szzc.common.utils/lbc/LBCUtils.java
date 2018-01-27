package com.szzc.common.utils.lbc;

import com.jucaifu.common.util.JsonHelper;
import com.szzc.common.utils.BTCMessage;
import com.szzc.common.utils.IWalletUtil;
import com.szzc.common.utils.ans.resp.BaseJson;
import com.szzc.common.utils.lbc.resp.LBCSendTransactionResp;
import com.szzc.common.utils.lbc.resp.TransactionDetailResp;
import com.szzc.common.utils.lbc.resp.TransactionResp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

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

    private BigDecimal digit = new BigDecimal(100000000);

    /**
     * Instantiates a new Lbc utils.
     *
     * @param btcMessage the btc message
     */
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

    /**
     * Wallet balance base json.
     *
     * @return the base json
     * @throws Exception the exception
     */
    public BaseJson<BigDecimal> wallet_balance() throws Exception {
        String s = main("wallet_balance","[]");
        return JsonHelper.jsonStr2Obj(s, BaseJson.class);
    }

    /**
     * Wallet new address base json.
     *
     * @return the base json
     * @throws Exception the exception
     */
    public BaseJson<String> wallet_new_address() throws Exception {
        String s = main("wallet_new_address", "[]");
        return JsonHelper.jsonStr2Obj(s, BaseJson.class);
    }

    /**
     * Transaction show transaction detail resp.
     *
     * @param txId the tx id
     * @return the transaction detail resp
     * @throws Exception the exception
     */
    public TransactionDetailResp transaction_show(String txId) throws Exception {
        String s = main("transaction_show", "[{\"txid\":\"" + txId +"\"}]");
        return JsonHelper.jsonStr2Obj(s, TransactionDetailResp.class);
    }


    /**
     * Transaction list transaction resp.
     *
     * @return the transaction resp
     * @throws Exception the exception
     */
    public TransactionResp transaction_list() throws Exception {
        String s = main("transaction_list","[]");
        return JsonHelper.jsonStr2Obj(s, TransactionResp.class);
    }

    /**
     * Wallet list string.
     *
     * @return the string
     * @throws Exception the exception
     */
    public String wallet_list() throws Exception {
        String s = main("wallet_list", "[]");
        return s;
    }

    /**
     * Send amount to address string.
     *
     * @param amount  the amount
     * @param address the address
     * @return the string
     * @throws Exception the exception
     */
    public LBCSendTransactionResp send_amount_to_address(BigDecimal amount,String address) throws Exception {
        // 发送金额
        String s = main("send_amount_to_address", "[{\"amount\":" + amount.doubleValue() +",\"address\":\"" + address +"\"}]");
        return JsonHelper.jsonStr2Obj(s, LBCSendTransactionResp.class);
    }


    /**
     * Help string.
     *
     * @param commands the commands
     * @return the string
     * @throws Exception the exception
     */
    public String help(String commands) throws Exception {
        String s = main("help","[{\"command\":\"" + commands +"\"}]");
        return s;
    }

    /**
     * Version string.
     *
     * @return the string
     * @throws Exception the exception
     */
    public String version() throws Exception {
        String s = main("version", "[]");
        return s;
    }

    /**
     * Receive format big decimal.
     * // 格式化接收金额
     * @param amount the amount
     * @return the big decimal
     */
    public BigDecimal receiveFormat(BigDecimal amount){
        if (amount == null){
            return amount;
        }
        return amount.divide(digit);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        BTCMessage btcMessage = new BTCMessage() ;
        /*btcMessage.setIP("localhost");
        btcMessage.setPORT("7000");*/
        //
        // btcMessage.setIP("localhost");
        BigDecimal test = new BigDecimal("1499000000");
        BigDecimal digit = new BigDecimal(100000000);
        test = test.divide(digit);
        System.out.println("sdfadsa = " + test.toString());
       /* btcMessage.setIP("127.0.01");
        btcMessage.setPORT("5279");
        btcMessage.setPASSWORD("sax1314");
        btcMessage.setACCESS_KEY("eth123456");
        btcMessage.setSECRET_KEY("eth123456");
        LBCUtils lbcUtils = new LBCUtils(btcMessage);
        TransactionDetailResp resp = lbcUtils.transaction_show("95001809afccede23ebc80df7f04d14fce96d4005e225471050201500ee2e2dd");
        System.three.println("sdfadsa = " + resp);*/
        //String str = lbcUtils.version();
        //String str = lbcUtils.transaction_show("b116e8fc03c8cfbb0a65eb6a76d0905b0f7a8e7d8289f5d34bda44221eb04a1e");
        //BaseJson resp = lbcUtils.wallet_balance();
        //System.three.println("----> " + resp.getResult());
        //String str = lbcUtils.help("transaction_show");
        // String str = lbcUtils.help(null);
        //BaseJson base = lbcUtils.wallet_new_address();
        //String str = lbcUtils.transaction_list();
        //String str = lbcUtils.transaction_show("95001809afccede23ebc80df7f04d14fce96d4005e225471050201500ee2e2dd");
        //String str = lbcUtils.transaction_list();
        //String str = lbcUtils.transaction_show("eeb23af0ff0c0d1e0ae413960ca84206e7eb225c0377f28ad9206222b53eca66");
        //String str = lbcUtils.help("transaction_show");
        //System.three.println("----> " + str);
    }


}
