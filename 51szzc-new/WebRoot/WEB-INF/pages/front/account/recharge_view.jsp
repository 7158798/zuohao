<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 银行卡充值查看 --->
<div class="modal modal-custom fade" id="lookRechargeMsg" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-mark"></div>
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <span class="modal-title"><spring:message code="recharge_information" /></span>
            </div>
            <div class="modal-body form-horizontal">
                <div class="recharge-infobox" style="margin-left: 45px">
                    <div class="form-group">
                        <span><spring:message code="regc_desc3" />：</span> <span id="fownerNameOne">xx</span>
                    </div>
                    <div class="form-group">
                        <span><spring:message code="regc_desc4"/>：</span> <span id="fbankNumberOne">--</span>
                    </div>
                    <div class="form-group">
                        <span><spring:message code="regc_desc5"/>：</span> <span id="fbankAddressOne">--</span>
                    </div>
                    <div class="form-group">
                        <span><spring:message code="regc_desc6"/>：</span> <span id="bankMoneyOne" class="text-danger font-size-16">--</span>
                    </div>
                    <div class="form-group">
                        <span><spring:message code="regc_desc7"/>：</span> <span id="bankInfoOne" class="text-danger font-size-16">--</span>
                    </div>
                    <div class="form-group">
													<span class="control-label text-left text-danger rechage-tips margin-bottom-clear" style="border-top-color:#fff0e4;line-height: 18px;padding-left: 0;display: inline-block;">
													<i class="icon"></i> <spring:message code="regc_desc8"/> <span id="bankInfotipsOne">--</span>

													</span>
                        <div class="tips text-danger" style="position:absolute">
                            *<spring:message code="regc_desc9"/>
                        </div>
                    </div>
                    <div style="margin:30px -15px 10px; height:4px;background:#9cc2fc"></div>
                    <div class="form-group">
                        <span><spring:message code="regc_user_name"/>：</span>
                        <span>${fuser.frealName }</span>
                    </div>
                    <div class="form-group">
                        <span><spring:message code="regc_desc10"/>：</span>
                        <span class="target-bankOne"></span>
                    </div>
                    <div class="form-group">
                        <span><spring:message code="regc_desc11"/>：</span>
                        <span class="target-cardOne"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

