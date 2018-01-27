package com.szzc.job;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.common.utils.BTCMessage;
import com.szzc.common.utils.lbc.LBCUtils;
import com.szzc.common.utils.lbc.bean.Outputs;
import com.szzc.common.utils.lbc.bean.TransactionDetail;
import com.szzc.common.utils.lbc.bean.Transactions;
import com.szzc.common.utils.lbc.parse.*;
import com.szzc.common.utils.lbc.resp.TransactionDetailResp;
import com.szzc.common.utils.lbc.resp.TransactionResp;
import com.szzc.core.wallet.pool.WalletBizPool;
import com.szzc.facade.wallet.pojo.cond.VirtualCapitaloperationCond;
import com.szzc.facade.wallet.pojo.po.VirtualCapitaloperation;
import com.szzc.facade.wallet.pojo.po.VirtualCoinType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 17-6-1.
 */
@Component
public class LBCRecharge extends ABSRecharge {

    @Scheduled(cron = "40 2/3 * * * ?")
    public void syncRecharge(){
        synchronized (this) {
            syncRecord();
        }
    }

    @Override
    List<String> getCoinType() {
        List<String> names = new ArrayList<>();
        names.add(LBC);
        return names;
    }

    @Override
    void syncInfo(VirtualCoinType virtualCoinType, BTCMessage btcMessage) throws Exception {
        LBCUtils lbcUtils = new LBCUtils(btcMessage);

        TransactionResp json = lbcUtils.transaction_list();
        if (json != null && json.getResult() != null && json.getResult().size() > 0){
            LOG.dStart(this, virtualCoinType.getFname() + "-> 同步钱包记录开始");
            for (Transactions resp : json.getResult()){
                if (resp.getValue().compareTo(BigDecimal.ZERO) == -1){
                    // 出账的记录．跳过
                    continue;
                }
                // 交易id
                String txId = resp.getTxid();
                TransactionDetailResp detailResp = lbcUtils.transaction_show(txId);
                if (detailResp != null){
                    TransactionDetail detail = detailResp.getResult();
                    if (detail != null){
                        List<Outputs> outputses = detail.getOutputs();
                        if (outputses != null && outputses.size() > 0){
                            for (Outputs outputs : outputses){
                                BigDecimal temp = lbcUtils.receiveFormat(outputs.getValue());
                                if (temp.compareTo(resp.getValue()) == 0){
                                    // 增加充值记录
                                    addFvirtualcaptualoperation(outputs.getAddress(),resp.getValue().doubleValue(),txId,virtualCoinType,Boolean.FALSE);
                                }
                            }
                        } else {
                            LOG.i(this,virtualCoinType.getFname() + "->outputses数据为空,txid=" + txId);
                        }
                    } else {
                        LOG.i(this,virtualCoinType.getFname() + "->详情数据为空,txid=" + txId);
                    }
                }
                /*
                // 区块浏览去解析的方式
                VirtualCapitaloperationCond cond = new VirtualCapitaloperationCond();
                cond.setTxId(txId);
                cond.setCoinId(virtualCoinType.getFid());
                List<VirtualCapitaloperation> rList = WalletBizPool.getInstance().virtualCapitaloperationBiz.queryListByCondition(cond);
                if (rList == null || rList.size() == 0){
                    // 未充值过
                    Transaction transaction = parse(txId);
                    if (transaction != null){
                        Hits hits = transaction.getHits();
                        List<HitsBean> hList = hits.getHits();
                        for (HitsBean hitsBean : hList){
                            Source source = hitsBean.getSource();
                            List<Vout> vList = source.getVout();
                            if (vList != null && vList.size() > 1){
                                for (Vout vout : vList){
                                    if (vout.getValue().compareTo(resp.getValue()) == 0){
                                        // 金额是相等，获取地址
                                        ScriptPubKey pubKey = vout.getScriptPubKey();
                                        List<String> list = pubKey.getAddresses();
                                        if (list != null && list.size() == 1){
                                            // 刚好一个地址
                                            addFvirtualcaptualoperation(list.get(0),resp.getValue().doubleValue(),txId,virtualCoinType,Boolean.FALSE);
                                        }
                                    }
                                }
                            } else {
                                LOG.i(this,virtualCoinType.getFname() + "->解析记录中没有输出项目,txid=" + txId);
                            }
                        }
                    } else {
                        LOG.i(this,virtualCoinType.getFname() + "->解析交易记录失败,txid=" + txId);
                    }
                }*/

            }
            LOG.dEnd(this, virtualCoinType.getFname() + "-> 同步钱包记录结束");
        }
    }

    public static String post(String url,String data) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json, text/plain, */*");
        con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
        con.setRequestProperty("Authorization", "Basic ZWxhc3RpYzpjaGFuZ2VtZQ==");
        con.setRequestProperty("Connection", "keep-alive");
        con.setRequestProperty("content-type", "application/json");
        con.setRequestProperty("Host", "elastic.cortesexplorer.com:9200");
        con.setRequestProperty("Origin", "https://explorer.lbry.io");
        con.setRequestProperty("Referer", "https://explorer.lbry.io/");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
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

    private Transaction parse(String txid){
        try {
            String str = "{\"query\":{\"constant_score\":{\"filter\":{\"term\":{\"txid\":\""+ txid+"\"}}}},\"size\":1}";
            String result = post("https://elastic.cortesexplorer.com:9200/lbry-transactions/_search", str);
            result  = result.replaceAll("\"_","\"");
            return JsonHelper.jsonStr2Obj(result,Transaction.class);
        } catch (Exception e) {
            LOG.e(this,"解析失败" + e.getMessage(),e);
        }
        return null;
    }

    @Override
    Long getBlock(BTCMessage btcMessage, VirtualCapitaloperation capitaloperation) throws Exception {
        return null;
    }

    public static void main(String[] args) throws Exception {
        //Document document = JsoupHelper.parse("https://explorer.lbry.io/#!/transaction/95001809afccede23ebc80df7f04d14fce96d4005e225471050201500ee2e2dd");

        //System.three.println("sdsad");

        String pa = "{\"query\":{\"constant_score\":{\"filter\":{\"term\":{\"txid\":\"95001809afccede23ebc80df7f04d14fce96d4005e225471050201500ee2e2dd\"}}}},\"size\":1}";
        String str = post("https://elastic.cortesexplorer.com:9200/lbry-transactions/_search", pa);
        str  = str.replaceAll("\"_","\"");
        Transaction transaction = JsonHelper.jsonStr2Obj(str,Transaction.class);
        System.out.println("sdsad");
    }
}
