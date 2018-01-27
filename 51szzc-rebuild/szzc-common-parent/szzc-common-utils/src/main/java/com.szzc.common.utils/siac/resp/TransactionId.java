package com.szzc.common.utils.siac.resp;

import java.util.List;

/**
 * Created by a123 on 17-6-19.
 */
public class TransactionId {

    /**
     * transaction : {"transaction":{"siacoininputs":[{"parentid":"48c7265a8a5f5818bbfd706ca1ffe3abedb6953a4ad1cf9b4849c0dc013911a0","unlockconditions":{"timelock":0,"publickeys":[{"algorithm":"ed25519","key":"PhObxFWw/y7V8IlpBmqdq69qHQnzQdqX/klj6RhmEFY="}],"signaturesrequired":1}}],"siacoinoutputs":[{"value":"10000000000000000000000000","unlockhash":"67e389119dba751634bd724358b7762e69726984a7821c01de462b4ea2477fa79936fcefc4fc"}],"filecontracts":null,"filecontractrevisions":null,"storageproofs":null,"siafundinputs":null,"siafundoutputs":null,"minerfees":["10000000000000000000000000"],"arbitrarydata":null,"transactionsignatures":[{"parentid":"48c7265a8a5f5818bbfd706ca1ffe3abedb6953a4ad1cf9b4849c0dc013911a0","publickeyindex":0,"timelock":0,"coveredfields":{"wholetransaction":true,"siacoininputs":null,"siacoinoutputs":null,"filecontracts":null,"filecontractrevisions":null,"storageproofs":null,"siafundinputs":null,"siafundoutputs":null,"minerfees":null,"arbitrarydata":null,"transactionsignatures":null},"signature":"3vgTkzrhmvcZeT/Lu5Q1DNXUlDMudck5zrIQp2Vk/FU3EkrwSPCNfyz/gJDz4Wcqf815d3AQIcICaYfFZpw3CQ=="}]},"transactionid":"3a19798fde612d2b54d00175c82ead4e650f09040b0c6e8216106ddf095de7a3","confirmationheight":110320,"confirmationtimestamp":1497801913,"inputs":[{"parentid":"48c7265a8a5f5818bbfd706ca1ffe3abedb6953a4ad1cf9b4849c0dc013911a0","fundtype":"siacoin input","walletaddress":false,"relatedaddress":"1b3db72b7c5ec7fdd2ff4762c10baed46467c5d321799c6622b001a3c9e1752ab0df82cc35fa","value":"20000000000000000000000000"}],"outputs":[{"id":"7a5fe4ca78764e239e0b7df943d9d92c6672e46de3a1682a6c5cfa57eb09fa31","fundtype":"siacoin output","maturityheight":110320,"walletaddress":true,"relatedaddress":"67e389119dba751634bd724358b7762e69726984a7821c01de462b4ea2477fa79936fcefc4fc","value":"10000000000000000000000000"},{"id":"0000000000000000000000000000000000000000000000000000000000000000","fundtype":"miner fee","maturityheight":0,"walletaddress":false,"relatedaddress":"000000000000000000000000000000000000000000000000000000000000000089eb0d6a8a69","value":"10000000000000000000000000"}]}
     */

    private TransactionBeanX transaction;

    public TransactionBeanX getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionBeanX transaction) {
        this.transaction = transaction;
    }

    public static class TransactionBeanX {
        /**
         * transaction : {"siacoininputs":[{"parentid":"48c7265a8a5f5818bbfd706ca1ffe3abedb6953a4ad1cf9b4849c0dc013911a0","unlockconditions":{"timelock":0,"publickeys":[{"algorithm":"ed25519","key":"PhObxFWw/y7V8IlpBmqdq69qHQnzQdqX/klj6RhmEFY="}],"signaturesrequired":1}}],"siacoinoutputs":[{"value":"10000000000000000000000000","unlockhash":"67e389119dba751634bd724358b7762e69726984a7821c01de462b4ea2477fa79936fcefc4fc"}],"filecontracts":null,"filecontractrevisions":null,"storageproofs":null,"siafundinputs":null,"siafundoutputs":null,"minerfees":["10000000000000000000000000"],"arbitrarydata":null,"transactionsignatures":[{"parentid":"48c7265a8a5f5818bbfd706ca1ffe3abedb6953a4ad1cf9b4849c0dc013911a0","publickeyindex":0,"timelock":0,"coveredfields":{"wholetransaction":true,"siacoininputs":null,"siacoinoutputs":null,"filecontracts":null,"filecontractrevisions":null,"storageproofs":null,"siafundinputs":null,"siafundoutputs":null,"minerfees":null,"arbitrarydata":null,"transactionsignatures":null},"signature":"3vgTkzrhmvcZeT/Lu5Q1DNXUlDMudck5zrIQp2Vk/FU3EkrwSPCNfyz/gJDz4Wcqf815d3AQIcICaYfFZpw3CQ=="}]}
         * transactionid : 3a19798fde612d2b54d00175c82ead4e650f09040b0c6e8216106ddf095de7a3
         * confirmationheight : 110320
         * confirmationtimestamp : 1497801913
         * inputs : [{"parentid":"48c7265a8a5f5818bbfd706ca1ffe3abedb6953a4ad1cf9b4849c0dc013911a0","fundtype":"siacoin input","walletaddress":false,"relatedaddress":"1b3db72b7c5ec7fdd2ff4762c10baed46467c5d321799c6622b001a3c9e1752ab0df82cc35fa","value":"20000000000000000000000000"}]
         * outputs : [{"id":"7a5fe4ca78764e239e0b7df943d9d92c6672e46de3a1682a6c5cfa57eb09fa31","fundtype":"siacoin output","maturityheight":110320,"walletaddress":true,"relatedaddress":"67e389119dba751634bd724358b7762e69726984a7821c01de462b4ea2477fa79936fcefc4fc","value":"10000000000000000000000000"},{"id":"0000000000000000000000000000000000000000000000000000000000000000","fundtype":"miner fee","maturityheight":0,"walletaddress":false,"relatedaddress":"000000000000000000000000000000000000000000000000000000000000000089eb0d6a8a69","value":"10000000000000000000000000"}]
         */

        private TransactionBean transaction;
        private String transactionid;
        private Long confirmationheight;
        private Long confirmationtimestamp;
        private List<InputsBean> inputs;
        private List<OutputsBean> outputs;

        public TransactionBean getTransaction() {
            return transaction;
        }

        public void setTransaction(TransactionBean transaction) {
            this.transaction = transaction;
        }

        public String getTransactionid() {
            return transactionid;
        }

        public void setTransactionid(String transactionid) {
            this.transactionid = transactionid;
        }

        public Long getConfirmationheight() {
            return confirmationheight;
        }

        public void setConfirmationheight(Long confirmationheight) {
            this.confirmationheight = confirmationheight;
        }

        public Long getConfirmationtimestamp() {
            return confirmationtimestamp;
        }

        public void setConfirmationtimestamp(Long confirmationtimestamp) {
            this.confirmationtimestamp = confirmationtimestamp;
        }

        public List<InputsBean> getInputs() {
            return inputs;
        }

        public void setInputs(List<InputsBean> inputs) {
            this.inputs = inputs;
        }

        public List<OutputsBean> getOutputs() {
            return outputs;
        }

        public void setOutputs(List<OutputsBean> outputs) {
            this.outputs = outputs;
        }

        public static class TransactionBean {
            /**
             * siacoininputs : [{"parentid":"48c7265a8a5f5818bbfd706ca1ffe3abedb6953a4ad1cf9b4849c0dc013911a0","unlockconditions":{"timelock":0,"publickeys":[{"algorithm":"ed25519","key":"PhObxFWw/y7V8IlpBmqdq69qHQnzQdqX/klj6RhmEFY="}],"signaturesrequired":1}}]
             * siacoinoutputs : [{"value":"10000000000000000000000000","unlockhash":"67e389119dba751634bd724358b7762e69726984a7821c01de462b4ea2477fa79936fcefc4fc"}]
             * filecontracts : null
             * filecontractrevisions : null
             * storageproofs : null
             * siafundinputs : null
             * siafundoutputs : null
             * minerfees : ["10000000000000000000000000"]
             * arbitrarydata : null
             * transactionsignatures : [{"parentid":"48c7265a8a5f5818bbfd706ca1ffe3abedb6953a4ad1cf9b4849c0dc013911a0","publickeyindex":0,"timelock":0,"coveredfields":{"wholetransaction":true,"siacoininputs":null,"siacoinoutputs":null,"filecontracts":null,"filecontractrevisions":null,"storageproofs":null,"siafundinputs":null,"siafundoutputs":null,"minerfees":null,"arbitrarydata":null,"transactionsignatures":null},"signature":"3vgTkzrhmvcZeT/Lu5Q1DNXUlDMudck5zrIQp2Vk/FU3EkrwSPCNfyz/gJDz4Wcqf815d3AQIcICaYfFZpw3CQ=="}]
             */

            private Object filecontracts;
            private Object filecontractrevisions;
            private Object storageproofs;
            private Object siafundinputs;
            private Object siafundoutputs;
            private Object arbitrarydata;
            private List<SiacoininputsBean> siacoininputs;
            private List<SiacoinoutputsBean> siacoinoutputs;
            private List<String> minerfees;
            private List<TransactionsignaturesBean> transactionsignatures;

            public Object getFilecontracts() {
                return filecontracts;
            }

            public void setFilecontracts(Object filecontracts) {
                this.filecontracts = filecontracts;
            }

            public Object getFilecontractrevisions() {
                return filecontractrevisions;
            }

            public void setFilecontractrevisions(Object filecontractrevisions) {
                this.filecontractrevisions = filecontractrevisions;
            }

            public Object getStorageproofs() {
                return storageproofs;
            }

            public void setStorageproofs(Object storageproofs) {
                this.storageproofs = storageproofs;
            }

            public Object getSiafundinputs() {
                return siafundinputs;
            }

            public void setSiafundinputs(Object siafundinputs) {
                this.siafundinputs = siafundinputs;
            }

            public Object getSiafundoutputs() {
                return siafundoutputs;
            }

            public void setSiafundoutputs(Object siafundoutputs) {
                this.siafundoutputs = siafundoutputs;
            }

            public Object getArbitrarydata() {
                return arbitrarydata;
            }

            public void setArbitrarydata(Object arbitrarydata) {
                this.arbitrarydata = arbitrarydata;
            }

            public List<SiacoininputsBean> getSiacoininputs() {
                return siacoininputs;
            }

            public void setSiacoininputs(List<SiacoininputsBean> siacoininputs) {
                this.siacoininputs = siacoininputs;
            }

            public List<SiacoinoutputsBean> getSiacoinoutputs() {
                return siacoinoutputs;
            }

            public void setSiacoinoutputs(List<SiacoinoutputsBean> siacoinoutputs) {
                this.siacoinoutputs = siacoinoutputs;
            }

            public List<String> getMinerfees() {
                return minerfees;
            }

            public void setMinerfees(List<String> minerfees) {
                this.minerfees = minerfees;
            }

            public List<TransactionsignaturesBean> getTransactionsignatures() {
                return transactionsignatures;
            }

            public void setTransactionsignatures(List<TransactionsignaturesBean> transactionsignatures) {
                this.transactionsignatures = transactionsignatures;
            }

            public static class SiacoininputsBean {
                /**
                 * parentid : 48c7265a8a5f5818bbfd706ca1ffe3abedb6953a4ad1cf9b4849c0dc013911a0
                 * unlockconditions : {"timelock":0,"publickeys":[{"algorithm":"ed25519","key":"PhObxFWw/y7V8IlpBmqdq69qHQnzQdqX/klj6RhmEFY="}],"signaturesrequired":1}
                 */

                private String parentid;
                private UnlockconditionsBean unlockconditions;

                public String getParentid() {
                    return parentid;
                }

                public void setParentid(String parentid) {
                    this.parentid = parentid;
                }

                public UnlockconditionsBean getUnlockconditions() {
                    return unlockconditions;
                }

                public void setUnlockconditions(UnlockconditionsBean unlockconditions) {
                    this.unlockconditions = unlockconditions;
                }

                public static class UnlockconditionsBean {
                    /**
                     * timelock : 0
                     * publickeys : [{"algorithm":"ed25519","key":"PhObxFWw/y7V8IlpBmqdq69qHQnzQdqX/klj6RhmEFY="}]
                     * signaturesrequired : 1
                     */

                    private int timelock;
                    private int signaturesrequired;
                    private List<PublickeysBean> publickeys;

                    public int getTimelock() {
                        return timelock;
                    }

                    public void setTimelock(int timelock) {
                        this.timelock = timelock;
                    }

                    public int getSignaturesrequired() {
                        return signaturesrequired;
                    }

                    public void setSignaturesrequired(int signaturesrequired) {
                        this.signaturesrequired = signaturesrequired;
                    }

                    public List<PublickeysBean> getPublickeys() {
                        return publickeys;
                    }

                    public void setPublickeys(List<PublickeysBean> publickeys) {
                        this.publickeys = publickeys;
                    }

                    public static class PublickeysBean {
                        /**
                         * algorithm : ed25519
                         * key : PhObxFWw/y7V8IlpBmqdq69qHQnzQdqX/klj6RhmEFY=
                         */

                        private String algorithm;
                        private String key;

                        public String getAlgorithm() {
                            return algorithm;
                        }

                        public void setAlgorithm(String algorithm) {
                            this.algorithm = algorithm;
                        }

                        public String getKey() {
                            return key;
                        }

                        public void setKey(String key) {
                            this.key = key;
                        }
                    }
                }
            }

            public static class SiacoinoutputsBean {
                /**
                 * value : 10000000000000000000000000
                 * unlockhash : 67e389119dba751634bd724358b7762e69726984a7821c01de462b4ea2477fa79936fcefc4fc
                 */

                private String value;
                private String unlockhash;

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getUnlockhash() {
                    return unlockhash;
                }

                public void setUnlockhash(String unlockhash) {
                    this.unlockhash = unlockhash;
                }
            }

            public static class TransactionsignaturesBean {
                /**
                 * parentid : 48c7265a8a5f5818bbfd706ca1ffe3abedb6953a4ad1cf9b4849c0dc013911a0
                 * publickeyindex : 0
                 * timelock : 0
                 * coveredfields : {"wholetransaction":true,"siacoininputs":null,"siacoinoutputs":null,"filecontracts":null,"filecontractrevisions":null,"storageproofs":null,"siafundinputs":null,"siafundoutputs":null,"minerfees":null,"arbitrarydata":null,"transactionsignatures":null}
                 * signature : 3vgTkzrhmvcZeT/Lu5Q1DNXUlDMudck5zrIQp2Vk/FU3EkrwSPCNfyz/gJDz4Wcqf815d3AQIcICaYfFZpw3CQ==
                 */

                private String parentid;
                private int publickeyindex;
                private int timelock;
                private CoveredfieldsBean coveredfields;
                private String signature;

                public String getParentid() {
                    return parentid;
                }

                public void setParentid(String parentid) {
                    this.parentid = parentid;
                }

                public int getPublickeyindex() {
                    return publickeyindex;
                }

                public void setPublickeyindex(int publickeyindex) {
                    this.publickeyindex = publickeyindex;
                }

                public int getTimelock() {
                    return timelock;
                }

                public void setTimelock(int timelock) {
                    this.timelock = timelock;
                }

                public CoveredfieldsBean getCoveredfields() {
                    return coveredfields;
                }

                public void setCoveredfields(CoveredfieldsBean coveredfields) {
                    this.coveredfields = coveredfields;
                }

                public String getSignature() {
                    return signature;
                }

                public void setSignature(String signature) {
                    this.signature = signature;
                }

                public static class CoveredfieldsBean {
                    /**
                     * wholetransaction : true
                     * siacoininputs : null
                     * siacoinoutputs : null
                     * filecontracts : null
                     * filecontractrevisions : null
                     * storageproofs : null
                     * siafundinputs : null
                     * siafundoutputs : null
                     * minerfees : null
                     * arbitrarydata : null
                     * transactionsignatures : null
                     */

                    private boolean wholetransaction;
                    private Object siacoininputs;
                    private Object siacoinoutputs;
                    private Object filecontracts;
                    private Object filecontractrevisions;
                    private Object storageproofs;
                    private Object siafundinputs;
                    private Object siafundoutputs;
                    private Object minerfees;
                    private Object arbitrarydata;
                    private Object transactionsignatures;

                    public boolean isWholetransaction() {
                        return wholetransaction;
                    }

                    public void setWholetransaction(boolean wholetransaction) {
                        this.wholetransaction = wholetransaction;
                    }

                    public Object getSiacoininputs() {
                        return siacoininputs;
                    }

                    public void setSiacoininputs(Object siacoininputs) {
                        this.siacoininputs = siacoininputs;
                    }

                    public Object getSiacoinoutputs() {
                        return siacoinoutputs;
                    }

                    public void setSiacoinoutputs(Object siacoinoutputs) {
                        this.siacoinoutputs = siacoinoutputs;
                    }

                    public Object getFilecontracts() {
                        return filecontracts;
                    }

                    public void setFilecontracts(Object filecontracts) {
                        this.filecontracts = filecontracts;
                    }

                    public Object getFilecontractrevisions() {
                        return filecontractrevisions;
                    }

                    public void setFilecontractrevisions(Object filecontractrevisions) {
                        this.filecontractrevisions = filecontractrevisions;
                    }

                    public Object getStorageproofs() {
                        return storageproofs;
                    }

                    public void setStorageproofs(Object storageproofs) {
                        this.storageproofs = storageproofs;
                    }

                    public Object getSiafundinputs() {
                        return siafundinputs;
                    }

                    public void setSiafundinputs(Object siafundinputs) {
                        this.siafundinputs = siafundinputs;
                    }

                    public Object getSiafundoutputs() {
                        return siafundoutputs;
                    }

                    public void setSiafundoutputs(Object siafundoutputs) {
                        this.siafundoutputs = siafundoutputs;
                    }

                    public Object getMinerfees() {
                        return minerfees;
                    }

                    public void setMinerfees(Object minerfees) {
                        this.minerfees = minerfees;
                    }

                    public Object getArbitrarydata() {
                        return arbitrarydata;
                    }

                    public void setArbitrarydata(Object arbitrarydata) {
                        this.arbitrarydata = arbitrarydata;
                    }

                    public Object getTransactionsignatures() {
                        return transactionsignatures;
                    }

                    public void setTransactionsignatures(Object transactionsignatures) {
                        this.transactionsignatures = transactionsignatures;
                    }
                }
            }
        }




    }
}
