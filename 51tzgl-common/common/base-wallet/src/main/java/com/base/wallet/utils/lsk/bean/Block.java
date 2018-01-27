package com.base.wallet.utils.lsk.bean;

/**
 * Created by lx on 17-5-15.
 */
public class Block {

    private String id;
    private Long version;
    private Long height;
    private String previousBlock;
    private Long numberOfTransactions;
    private Long totalAmount;
    private Long totalFee;
    private Long reward;
    private Long payloadLength;
    private String payloadHash;
    private String generatorPublicKey;
    private String generatorId;
    private String blockSignature;
    private Long confirmations;
    private Long totalForged;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getPreviousBlock() {
        return previousBlock;
    }

    public void setPreviousBlock(String previousBlock) {
        this.previousBlock = previousBlock;
    }

    public Long getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(Long numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public Long getReward() {
        return reward;
    }

    public void setReward(Long reward) {
        this.reward = reward;
    }

    public Long getPayloadLength() {
        return payloadLength;
    }

    public void setPayloadLength(Long payloadLength) {
        this.payloadLength = payloadLength;
    }

    public String getPayloadHash() {
        return payloadHash;
    }

    public void setPayloadHash(String payloadHash) {
        this.payloadHash = payloadHash;
    }

    public String getGeneratorPublicKey() {
        return generatorPublicKey;
    }

    public void setGeneratorPublicKey(String generatorPublicKey) {
        this.generatorPublicKey = generatorPublicKey;
    }

    public String getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(String generatorId) {
        this.generatorId = generatorId;
    }

    public String getBlockSignature() {
        return blockSignature;
    }

    public void setBlockSignature(String blockSignature) {
        this.blockSignature = blockSignature;
    }

    public Long getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Long confirmations) {
        this.confirmations = confirmations;
    }

    public Long getTotalForged() {
        return totalForged;
    }

    public void setTotalForged(Long totalForged) {
        this.totalForged = totalForged;
    }
}
