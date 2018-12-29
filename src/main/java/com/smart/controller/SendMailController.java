package com.smart.controller;

import com.smart.bean.muFile;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    com.smart.redis.JedisClient JedisClient;

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


    @RequestMapping(value="/upload.do",method=RequestMethod.POST)
    public String upload(HttpServletRequest request,
                         @ModelAttribute muFile file, Model model) throws Exception {

        System.out.println(file.getDescription());
        //如果文件不为空，写入上传路径
        if(!file.getFile().isEmpty()) {
            //上传文件路径
            String path = "E:/code/Repository/bookManager/src/main/webapp/public/images";
            //上传文件名
            String filename = file.getFile().getOriginalFilename();
            File filepath = new File(path,filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()){
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            file.getFile().transferTo(new File(path + File.separator + filename));
            JedisClient.setFile(file.getDescription(),path);
            model.addAttribute("muFile",file);
            return "userinfo";
        } else {
            return "error";
        }

    }

    @RequestMapping(value="/download.do")
    public ResponseEntity<byte[]> download(HttpServletRequest request,
                                           @RequestParam("filename") String filename,
                                           Model model)throws Exception {
        //下载文件路径
        String path = "F:/FileDownloads/"+filename;
        File file = new File(path + File.separator + filename);

        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        String downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFielName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }


    @RequestMapping(value="/vue.do")
    public ModelAndView vue() {
        return new ModelAndView("testVue");
    }
}
