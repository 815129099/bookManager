package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * created by viking on 2018/07/17
 * 测试邮件发送controller
 */
@Controller
@RequestMapping("/")
public class SendMailController {
    @Autowired
    private JavaMailSender javaMailSender;//在spring中配置的邮件发送的bean

    @RequestMapping("/send.do")
    public Object sendMail03(){
        MimeMessage mMessage=javaMailSender.createMimeMessage();//创建邮件对象
        MimeMessageHelper mMessageHelper;
        Properties prop = new Properties();
        String from;
        try {
            //从配置文件中拿到发件人邮箱地址
            prop.load(this.getClass().getResourceAsStream("/mail.properties"));
            from = prop.get("mail.smtp.username")+"";
            mMessageHelper=new MimeMessageHelper(mMessage,true);
            mMessageHelper.setFrom(from);//发件人邮箱
            mMessageHelper.setTo("liu.wenxiang@CXTC.COM");//收件人邮箱
            mMessageHelper.setSubject("Spring的邮件发送");//邮件的主题
            mMessageHelper.setText("<p>你借阅的书籍还剩3天，请及时归还</p><br/>" +
                    "逾期将扣额外金额<br/>" +
                    "<img src='cid:fengye'>",true);//邮件的文本内容，true表示文本以html格式打开
            File file=new File("F:/googleDowload/图片/back1.jpg");//在邮件中添加一张图片
            FileSystemResource resource=new FileSystemResource(file);
            mMessageHelper.addInline("fengye", resource);//这里指定一个id,在上面引用
            mMessageHelper.addAttachment("back1.jpg", resource);//在邮件中添加一个附件
            javaMailSender.send(mMessage);//发送邮件
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "发送成功";
    }
}
