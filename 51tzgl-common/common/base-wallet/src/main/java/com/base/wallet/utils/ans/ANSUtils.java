package com.base.wallet.utils.ans;


import com.alibaba.fastjson.JSONObject;
import com.base.wallet.utils.BTCMessage;
import com.base.wallet.utils.IWalletUtil;
import com.base.wallet.utils.ans.resp.BalanceResp;
import com.base.wallet.utils.ans.resp.BlockResp;
import com.base.wallet.utils.ans.resp.TransactionResp;
import com.jucaifu.common.util.JsonHelper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 交易所充值提现交易的交易类型都是 ContractTransaction（无论是充值小蚁股还是小蚁币），交易所在遍历区块中的所有交易时，只关心 ContractTransaction 就可以了
 * 小蚁json-rpc
 * Created by lx on 17-4-5.
 */
public class ANSUtils implements IWalletUtil {
    // 合约交易
    public static final String CONTRACTTRANSACTION = "ContractTransaction";
    public static final String ANS_ASSET_ID = "c56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b";
    public static final String ANC_ASSET_ID = "";
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

    public ANSUtils(BTCMessage btcMessage){
        this.ACCESS_KEY = btcMessage.getACCESS_KEY();
        this.SECRET_KEY = btcMessage.getSECRET_KEY();
        this.IP = btcMessage.getIP();
        this.PORT = btcMessage.getPORT();
        this.PASSWORD = btcMessage.getPASSWORD();
    }

    // '{"jsonrpc":"2.0","method":"eth_getBalance","params":["0x407d73d8a49eeb85d32cf465507dd71d507100c1", "latest"],"id":1}'
    private String main(String method,String condition) throws Exception {
        String result = "";
        String tonce = "" + (System.currentTimeMillis() * 1000);
        String params = "tonce=" + tonce.toString() + "&accesskey="
                + ACCESS_KEY
                + "&requestmethod=post&id=1&method="+method+"&params="+condition;

        String url = "http://"+IP+":"+PORT;
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

        inputLine = in.readLine();
        response.append(inputLine);
        in.close();
        result = response.toString();
        return result;
    }

    public TransactionResp ans_getrawtransaction(String hash) throws Exception {
        String s = main("getrawtransaction", "[\"" + hash +"\",1]");
        TransactionResp json = JsonHelper.jsonStr2Obj(s, TransactionResp.class);
        return json;
    }

    public BlockResp ans_getblock(Long i) throws Exception {
        String s = main("getblock", "[" + i +",1]");
        BlockResp json = JsonHelper.jsonStr2Obj(s, BlockResp.class);
        return json;
    }

    public BlockResp ans_getblock(String hash) throws Exception {
        String s = main("getblock", "[\"" + hash +"\",1]");
        BlockResp json = JsonHelper.jsonStr2Obj(s, BlockResp.class);
        return json;
    }

    //区块高度
    public JSONObject ans_blockNumber() throws Exception {
        String s = main("getblockcount", "[]");
        JSONObject json = JsonHelper.jsonStr2JsonObj(s);
        return json;
    }

    public int ans_blockNumberValue() throws Exception {
        JSONObject jsonObject = ans_blockNumber() ;
        int count = 0 ;
        if(jsonObject.containsKey("result")){
            count = Integer.parseInt(jsonObject.getString("result")) ;
        }
        return count ;
    }

    public BalanceResp ans_getbalance(String assetId) throws Exception {
        String s = main("getbalance", "[\"" + assetId + "\"]");
        BalanceResp json = JsonHelper.jsonStr2Obj(s, BalanceResp.class);
        return json;
    }

    /**
     * 转账
     * @param assetId   资产ID
     * @param address   地址
     * @param value
     * @return
     * @throws Exception
     */
    public TransactionResp ans_sendtoaddress(String assetId,String address,Long value) throws Exception {
        String s = main("sendtoaddress", "[\"" + assetId + "\",\"" + address + "\"," + value + "]");
        TransactionResp json = JsonHelper.jsonStr2Obj(s, TransactionResp.class);
        return json;
    }

   /* public String ans_sendtoaddress(String assetId,String address,Long value) throws Exception {
        String s = main("getbalance", "[\"" + assetId + "\",\"" + address + "\"," + value + "]");
        JSONObject json = JSONObject.fromObject(s);
        String txId = null;
        if(json.containsKey("result")){
            txId = json.getString("result");
        }
        return txId;
    }*/

    public static void main(String[] args) throws Exception {

        BTCMessage btcMessage = new BTCMessage() ;
        btcMessage.setIP("localhost") ;
        btcMessage.setPORT("10332") ;
        btcMessage.setPASSWORD("sax1314");

        btcMessage.setACCESS_KEY("eth123456");
        btcMessage.setSECRET_KEY("eth123456") ;
        ANSUtils ansUtils = new ANSUtils(btcMessage) ;
        //130.252.101.153

       /*BalanceResp resp = ansUtils.ans_getbalance("c56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b");
        System.out.println("请求数据 :" + resp.getResult().getBalance());*/
        //TransactionResp resp = ansUtils.ans_sendtoaddress(ANSUtils.ANS_ASSET_ID, "AQAFDJbRRfdpgSAcVgcnUmjAHXckizYBBk", 1l);
        TransactionResp resp = ansUtils.ans_getrawtransaction("6a8d7e564ea01ad094f1d229182b5cf4ab4b14f0c9c90c34f512388e47e75e66");
        System.out.println("交易Id :" );
        /*int i = ansUtils.ans_blockNumberValue();
        System.out.println("block number " + i);

        BlockResp resp = ansUtils.ans_getblock(728989l);

        Block block = resp.getResult();

        BlockResp te = ansUtils.ans_getblock("cfa37b03570ac3526e348cfefee412bdbcf24e42f32a7571249da2c4d5f29998");

        TransactionResp transaction = ansUtils.ans_getrawtransaction("daaa42f21ccfb6c6c5d028328c43de69a0f9d95b49baccb05f768b65c0bea104");

        System.out.println("block number " + i);*/

    }

}
