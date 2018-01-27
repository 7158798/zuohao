package com.base.wallet.utils.lsk;


import com.base.wallet.utils.BTCMessage;
import com.base.wallet.utils.IWalletUtil;
import com.base.wallet.utils.lsk.resp.*;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-5-15.
 */
public class LSKUtils implements IWalletUtil {
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
     * 打开帐号
     * @param secret
     * @return
     * @throws IOException
     */
    public AccountResp open(String secret) throws IOException {
        String url = getBaseUrl("/accounts/open");
        Map<String,String> map = new HashMap<>();
        map.put("secret",secret);
        return JsonHelper.jsonStr2Obj(HttpClientHelper.post(url, map),AccountResp.class);
    }

    /**
     * 获取信息失败
     * @param address
     * @return
     */
    public AccountResp get_account(String address){
        String url = getBaseUrl("/accounts?address=" + address);
        String resp = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        return JsonHelper.jsonStr2Obj(resp, AccountResp.class);
    }

    /**
     * 获取帐号的余额
     * @param address
     * @return
     */
    public BalanceResp get_balance(String address){
        String url = getBaseUrl("/accounts/getBalance?address=" + address);
        return JsonHelper.jsonStr2Obj(HttpClientHelper.sendGetRequest(url, Boolean.FALSE),BalanceResp.class);
    }

    /**
     * 获取多个地址余额
     * @param list
     * @return
     */
    public BigDecimal get_balance(List<String> list){
        BigDecimal result = BigDecimal.ZERO;
        for (String address : list){
            BalanceResp resp = get_balance(address);
            result = result.add(resp.getBalance());
        }
        return receiveFormat(result);
    }

    // 格式化接收金额
    public BigDecimal receiveFormat(BigDecimal amount){
        if (amount == null){
            return amount;
        }
        return amount.divide(digit);
    }

    /**
     * 格式化发送金额
     * @param amount
     * @return
     */
    public BigDecimal sendFormat(BigDecimal amount){
        if (amount == null){
            return amount;
        }
        return amount.multiply(digit);
    }

    /**
     * 获取区块的高度
     * @return
     */
    public BlockHeightResp get_height(){
        String url = getBaseUrl("/blocks/getHeight");
        return JsonHelper.jsonStr2Obj(HttpClientHelper.sendGetRequest(url, Boolean.FALSE),BlockHeightResp.class);
    }

    /**
     * 获取区块
     * @param height
     * @return
     */
    public BlocksResp get_blocks(Long height){
        String url = getBaseUrl("/blocks?height=" + height);
        String str = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        return JsonHelper.jsonStr2Obj(str,BlocksResp.class);
    }

    /**
     * 获取区块
     * @param id
     * @return
     */
    public BlockResp get_block(String id){
        String url = getBaseUrl("/blocks/get?id=" + id);
        return JsonHelper.jsonStr2Obj(HttpClientHelper.sendGetRequest(url, Boolean.FALSE),BlockResp.class);
    }

    /**
     * 获取交易id
     * @param blockId
     * @param limit
     * @param offset
     * @return
     * @throws IOException
     */
    public TransactionsResp transactions(String blockId,Long limit,Long offset) throws IOException {
        String url = getBaseUrl("/transactions?blockId=" + blockId + "&limit=" + limit + "&offset=" + offset);
        return JsonHelper.jsonStr2Obj(HttpClientHelper.sendGetRequest(url, Boolean.FALSE),TransactionsResp.class);
    }

    /**
     * 获取交易id
     * @param txId
     * @return
     * @throws IOException
     */
    public TransactionResp transactions(String txId) throws IOException {
        String url = getBaseUrl("/transactions/get?id=" + txId);
        return JsonHelper.jsonStr2Obj(HttpClientHelper.sendGetRequest(url, Boolean.FALSE),TransactionResp.class);
    }

    /**
     * 发送一笔交易
     * @param secret
     * @param value
     * @param recipientId
     * @return
     * @throws IOException
     */
    public TransactionResp send_transactions(String secret,BigDecimal value,String recipientId) throws Exception {
        String url = getBaseUrl("/transactions");
        String data = "{\"secret\":\"" + secret + "\",\"amount\":" + value.intValue() + ",\"recipientId\":\"" + recipientId + "\"}";
        String resp = put(url,data);
        return JsonHelper.jsonStr2Obj(resp,TransactionResp.class);
    }

    /**
     * 汇总到主账户
     * @param mainAddress
     * @param secretList
     * @throws IOException
     */
    public void sendMain(String mainAddress,List<String> secretList) throws Exception {
        // 0.1
        BigDecimal fees = digit.divide(new BigDecimal(10));
        for (String secret : secretList){
            AccountResp resp = open(secret);
            if (resp.getSuccess() && resp.getAccount() != null){
                if (resp.getAccount().getBalance().compareTo(fees) == 1){
                    // 大于手续费，发送到主地址
                    send_transactions(secret,resp.getAccount().getBalance(),mainAddress);
                }
            }
        }
    }

    /**
     * 基础url
     * @param url
     * @return
     */
    public String getBaseUrl(String url){
        return "http://"+IP+":"+PORT + "/api" + url;
    }

    public LSKUtils(BTCMessage btcMessage){
        this.ACCESS_KEY = btcMessage.getACCESS_KEY();
        this.SECRET_KEY = btcMessage.getSECRET_KEY();
        this.IP = btcMessage.getIP();
        this.PORT = btcMessage.getPORT();
        this.PASSWORD = btcMessage.getPASSWORD();
    }

    // /api/accounts/generatePublicKey
    public String generatePublicKey(String secret) throws IOException {

        String url = getBaseUrl("/accounts/generatePublicKey");
        Map<String,String> map = new HashMap<>();
        map.put("secret",secret);
        return HttpClientHelper.post(url, map);
    }
    public String getPublicKey(String address){
        String url = getBaseUrl("/accounts/getPublicKey?address=" + address);
        return HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
    }

    // /api/multisignatures/sign
    public String sign(String secret,String txId) throws IOException {
        String url = getBaseUrl("/multisignatures/sign");
        Map<String,String> map = new HashMap<>();
        map.put("secret",secret);
        map.put("transactionId",txId);
        return HttpClientHelper.post(url, map);
    }

    public String unconfirmed(String id){
        String url = getBaseUrl("/transactions/unconfirmed/get?id=" + id);
        return HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
    }

    public String put(String url,String data) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(data);
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
        return response.toString();
    }

    public static void main(String[] args) throws Exception {
        BTCMessage btcMessage = new BTCMessage() ;
        /*btcMessage.setIP("localhost");
        btcMessage.setPORT("7000");*/
        btcMessage.setIP("130.252.100.247");
        btcMessage.setPORT("8000");
        btcMessage.setPASSWORD("sax1314");

        btcMessage.setACCESS_KEY("eth123456");
        btcMessage.setSECRET_KEY("eth123456") ;
        LSKUtils lskUtils = new LSKUtils(btcMessage) ;
        BigDecimal value = new BigDecimal("0.7");
        value = lskUtils.sendFormat(value);
       /* System.out.println("1 : " + value);
        value = lskUtils.receiveFormat(value);
        System.out.println("2 : " + value);*/
        //TransactionResp resp = lskUtils.send_transactions("music witness type ladder pink claim car design drift train style tourist",value,"6274700093986119664L");
        //TransactionResp resp = lskUtils.send_transactions("sweet dismiss horror ocean phrase cat meat duty urge kit help iron",value,"12464043237682539975L");
        //String str = lskUtils.get_balance("16011036285940334666L");
        //BaseJson json = lskUtils.transactions("7807109686729042739",103l,0l);
        /*BaseJson json = lskUtils.transactions("16747648617094017103");
        lskUtils.get_balance("16011036285940334666L");*/
        //16747648617094017103

        //System.out.println(json.toString());
        //BalanceResp resp = lskUtils.get_balance("12464043237682539975L");

        /*String test = "a2c 568 0b- 012 4-4 6b3 -99 ac- 578 2cb ea7 879";*/
         //AccountResp resp = lskUtils.open("label asthma noise grab story renew speak good capital glove scrub hobby");
        //7115645286039227036  11492954751665084419
        //TransactionResp resp = lskUtils.transactions("11492954751665084419");
        AccountResp resp = lskUtils.get_account("6274700093986119664L");
        // AccountResp resp = lskUtils.open("music witness type ladder pink claim car design drift train style tourist");
        //BalanceResp resp = lskUtils.get_balance("12464043237682539975L");
        // 测试的帐号　12464043237682539975L
        // 区块里面没有到帐的用户　10860870103084130242L
        // AccountResp resp = lskUtils.get_account("10860870103084130242L");
        //BlockHeightResp resp = lskUtils.get_height();
        // String str = lskUtils.generatePublicKey("music witness type ladder pink claim car design drift train style tourist");
        //String str = lskUtils.getPublicKey("8088166399063744074L");
        //AccountResp resp = lskUtils.open("sweet dismiss horror ocean phrase cat meat duty urge kit help iron");
        //String str = lskUtils.sign("sweet dismiss horror ocean phrase cat meat duty urge kit help iron","11492954751665084419");
        //String str = lskUtils.unconfirmed("11492954751665084419");
        // System.out.println(str);
        //BlocksResp resp = lskUtils.get_blocks(2884685l);
        // TransactionsResp resp = lskUtils.transactions("17613786873239202936", 10l, 0l);
        System.out.println(JsonHelper.obj2JsonStr(resp));
        /*String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);*/
        //AccountResp resp = lskUtils.open("1");
        // System.out.println("tttw" + resp.getAccount().getAddress());
        // BigDecimal value = new BigDecimal("112340000");
        //lskUtils
    }

    private String getKey(String key){
        int length = key.length();
        int index = length / 12;
        //for (int i = 0;)
        //8832016284773779150L
        return key;
    }

}
