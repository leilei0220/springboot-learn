package com.leilei.support;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.leilei.config.SmsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: lei-p
 * Date: 2020/4/6 17:43
 * desc: 短信发送支持  根据官方demo 修改
 */
@Component
public class SendSms {

    @Autowired
    private SmsBean smsBean;

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    static final String product = "Dysmsapi";

    /**
     * 产品域名,开发者无需替换
     */

    static final String domain = "dysmsapi.aliyuncs.com";

    //    public  CommonResponse sendSms(String phones, String code) {
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsBean.getAccessKeyId(), smsBean.getAccessKeySecret());
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setSysMethod(MethodType.POST);
//        request.setSysDomain(domain);
//        request.setSysVersion("2020-04-06");
//        request.setSysAction("SendSms");
//        request.putQueryParameter("RegionId", "cn-hangzhou");
//        //短信签名名称。请在控制台签名管理页面签名名称一列查看。
//        request.putQueryParameter("SignName", smsBean.getSignName());
//        //短信模板ID。请在控制台模板管理页面模板CODE一列查看。
//        request.putQueryParameter("TemplateCode", smsBean.getTemplateCode());
//        //接收短信的手机号码。 支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
//        request.putQueryParameter("PhoneNumbers", phones);
//        //短信模板变量对应的实际值，JSON格式。
//        //如果JSON中需要带换行符，请参照标准的JSON协议处理。
//        request.putQueryParameter("TemplateCode", "{\"code\":\""+code+"\"}");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//            return response;
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public SendSmsResponse sendSms(String phone, String code){
        try {
            //可自助调整超时时间
            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsBean.getAccessKeyId(), smsBean.getAccessKeySecret());
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(smsBean.getSignName());
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(smsBean.getTemplateCode());
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam("{\"code\":\"" + code + "\"}");

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            //request.setOutId("yourOutId");

            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            return sendSmsResponse;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}