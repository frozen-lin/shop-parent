package com.frozen.pc.web.controller;

import com.frozen.pay.api.PayCallbackService;
import com.frozen.pay.bean.PayInfo;
import com.frozen.pay.bean.PaymentEntity;
import com.frozen.pc.web.comm.WebConstants;
import com.frozen.pc.web.service.PayFeignService;
import com.frozen.pc.web.utils.WebUtil;
import com.frozen.response.ResponseDataEntity;
import com.frozen.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description> 支付控制层 </description>
 *
 * @author : lw
 * @date : 2020-04-10 16:35
 **/
@Slf4j
@Controller
public class PayController {
    @Autowired
    private PayFeignService payFeignService;
    @Autowired
    private PayCallbackService payCallbackService;

    @PostMapping("/private/pay/ali")
    public void toAliPayPage(String totalAmount, HttpServletResponse response) throws IOException {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPrice(new BigDecimal(totalAmount));
        paymentEntity.setUserId(WebUtil.getLoginUser().getId());
        try {
            response.setContentType("text/html;charset=utf-8");
            ResponseDataEntity<String> payResponse = payFeignService.createPay(paymentEntity);
            if (!ResponseUtil.checkResponseOk(payResponse)) {
                response.sendRedirect(WebConstants.ERROR_PAGE);
            }
            final ResponseDataEntity<String> payPageResponse = payFeignService.getAlipayPage(payResponse.getData());
            if (!ResponseUtil.checkResponseOk(payPageResponse)) {
                response.sendRedirect(WebConstants.ERROR_PAGE);
            }
            //将跳转阿里页面的form表单写入流中
            final PrintWriter writer = response.getWriter();
            writer.write(payPageResponse.getData());
            writer.close();
        } catch (Exception e) {
            log.error("去阿里支付页面出错啦", e);
            response.sendRedirect(WebConstants.ERROR_PAGE);
        }
    }

    /**
     * <description> 阿里异步支付通知 </description>
     *
     * @param request  :
     * @param response :
     * @return : void
     * @author : lw
     * @date : 2020/4/11 15:59
     */
    @PostMapping("/pay/alipay/notify")
    public void aliPaySuccessNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("###异步通知开始###");
        final Map<String, String> alipayParams = getAlipayParams(request, response);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            ResponseDataEntity<String> responseData = payCallbackService.asyncCallBack(alipayParams);
            if (!ResponseUtil.checkResponseOk(responseData)) {
                writer.write(WebConstants.FAIL);
                return;
            }
            writer.write(responseData.getData());
        } catch (Exception e) {
            log.error("异步通知异常", e);
            assert writer != null;
            writer.write(WebConstants.FAIL);
        } finally {
            log.info("###异步通知结束###");
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * <description> 阿里同步支付 </description>
     *
     * @param request  :
     * @param response :
     * @return : void
     * @author : lw
     * @date : 2020/4/11 15:59
     */
    @GetMapping("/private/pay/alipay/return")
    public void aliPaySuccessReturn(HttpServletRequest request, HttpServletResponse response) {
        final Map<String, String> alipayParams = getAlipayParams(request, response);
        PrintWriter writer = null;
        try {
            ResponseDataEntity<PayInfo> payInfoResponse = payCallbackService.syncCallBack(alipayParams);
            if (!ResponseUtil.checkResponseOk(payInfoResponse)) {
                log.error("创建订单失败!!{}", payInfoResponse.toString());
                return;
            }
            PayInfo payInfo = payInfoResponse.getData();
            String htmlForm = "<form name='punchout_form'"
                    + " method='post' action='"+WebConstants.PAY_SUCCESS_PAGE+"' >"
                    + "<input type='hidden' name='orderId' value='" + payInfo.getOrderId() + "'>"
                    + "<input type='hidden' name='tradeNo' value='" + payInfo.getTradeNo() + "'>"
                    + "<input type='hidden' name='totalAmount' value='" + payInfo.getTotalAmount() + "'>"
                    + "<input type='submit' value='支付成功' style='display:none'>"
                    + "</form><script>document.forms[0].submit();" + "</script>";
            writer = response.getWriter();
            writer.write(htmlForm);
        } catch (Exception e) {
            log.error("同步支付通知失败", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

    @PostMapping(WebConstants.PAY_SUCCESS_PAGE)
    public String toPaySuccessPage(HttpServletRequest request, String orderId, String tradeNo, String totalAmount) {
        request.setAttribute("userName", WebUtil.getLoginUser().getUsername());
        request.setAttribute("orderId", orderId);
        request.setAttribute("tradeNo", tradeNo);
        request.setAttribute("totalAmount", totalAmount);
        return WebConstants.PAY_SUCCESS_PAGE;
    }

    /**
     * <description> 获取支付宝参数 </description>
     *
     * @param request  :
     * @param response :
     * @return : java.util.Map<java.lang.String,java.lang.String>
     * @author : lw
     * @date : 2020/4/11 16:00
     */
    private Map<String, String> getAlipayParams(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<>();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

}
