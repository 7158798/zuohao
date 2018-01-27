package com.szzc.common.utils.lsk.bean;

import java.math.BigDecimal;

/**
 * Created by lx on 17-5-15.
 */
public class Transaction {

    //"id": "Id of transaction. String";
    private String id;
    // "height": "Tx blockchain height. Integer";
    private Long height;
    // "blockId" "Tx blockId. String",
    private String blockId;
    // "type": "Type of transaction. Integer",
    private Long type;
    // "timestamp": "Timestamp of transaction. Integer",
    private Long timestamp;
    // "senderPublicKey": "Sender public key of transaction. Hex",
    private String senderPublicKey;
    // "senderId": "Address of transaction sender. String",
    private String senderId;
    // "recipientId": "Recipient id of transaction. String",
    private String recipientId;
    // "amount": "Amount. Integer",
    private BigDecimal amount;
    // "fee": "Fee. Integer",
    private BigDecimal fee;
    // "signature": "Signature. Hex",
    private String Signature;
    //"signatures": "Signatures. Array",
    private String[] signatures;
    // "confirmations": "Number of confirmations. Integer",
    private Long confirmations;
    //"asset": "Resources. Object"
    private Object asset;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String[] getSignatures() {
        return signatures;
    }

    public void setSignatures(String[] signatures) {
        this.signatures = signatures;
    }

    public Long getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Long confirmations) {
        this.confirmations = confirmations;
    }

    public Object getAsset() {
        return asset;
    }

    public void setAsset(Object asset) {
        this.asset = asset;
    }
}
