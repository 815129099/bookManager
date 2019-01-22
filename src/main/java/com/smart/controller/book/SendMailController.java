
package com.smart.controller.book;

import com.smart.bean.muFile;
import com.smart.redis.JedisClient;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/"})
public class SendMailController {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    JedisClient JedisClient;

    public SendMailController() {
    }

    @RequestMapping({"/send.do"})
    public Object sendMail03() {
        MimeMessage mMessage = this.javaMailSender.createMimeMessage();
        Properties prop = new Properties();

        try {
            prop.load(this.getClass().getResourceAsStream("/mail.properties"));
            String from = prop.get("mail.smtp.username") + "";
            MimeMessageHelper mMessageHelper = new MimeMessageHelper(mMessage, true);
            mMessageHelper.setFrom(from);
            mMessageHelper.setTo("liu.wenxiang@CXTC.COM");
            mMessageHelper.setSubject("Spring的邮件发送");
            mMessageHelper.setText("<p>你借阅的书籍还剩3天，请及时归还</p><br/>逾期将扣额外金额<br/><img src='cid:fengye'>", true);
            File file = new File("F:/googleDowload/图片/back1.jpg");
            FileSystemResource resource = new FileSystemResource(file);
            mMessageHelper.addInline("fengye", resource);
            mMessageHelper.addAttachment("back1.jpg", resource);
            this.javaMailSender.send(mMessage);
        } catch (MessagingException var7) {
            var7.printStackTrace();
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        return "发送成功";
    }

    @RequestMapping(
            value = {"/upload.do"},
            method = {RequestMethod.POST}
    )
    public String upload(HttpServletRequest request, @ModelAttribute muFile file, Model model) throws Exception {
        System.out.println(file.getDescription());
        if (!file.getFile().isEmpty()) {
            String path = "E:/code/Repository/bookManager/src/main/webapp/public/images";
            String filename = file.getFile().getOriginalFilename();
            File filepath = new File(path, filename);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }

            file.getFile().transferTo(new File(path + File.separator + filename));
            this.JedisClient.setFile(file.getDescription(), path);
            model.addAttribute("muFile", file);
            return "userinfo";
        } else {
            return "book/error";
        }
    }

    @RequestMapping({"/download.do"})
    public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam("filename") String filename, Model model) throws Exception {
        String path = "F:/FileDownloads/" + filename;
        File file = new File(path + File.separator + filename);
        HttpHeaders headers = new HttpHeaders();
        String downloadFielName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
        headers.setContentDispositionFormData("attachment", downloadFielName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    @RequestMapping({"/vue.do"})
    public ModelAndView vue() {
        return new ModelAndView("testVue");
    }
}
