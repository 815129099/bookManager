package com.smart.service;

import com.smart.bean.Inform;
import com.smart.dao.BookDao;
import com.smart.dao.UserDao;
import com.smart.redis.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
public class SchedulerMail {
    @Autowired
    private JavaMailSender javaMailSender;//在spring中配置的邮件发送的bean
    @Autowired
    private BookDao bookDao;
    @Autowired
    private UserDao userDao;


    public void test(){
        System.out.println("每隔10秒钟，啦啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
    }

/*"0 0 12 * * ?" 每天中午12点触发
"0 15 10 ? * *" 每天上午10:15触发
"0 15 10 * * ?" 每天上午10:15触发
*/
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendMail03(){
        List<Map<String,Object>> list = bookDao.getRecordList();
        for (Map<String,Object> map:list) {
            int flag = 0;
            try {
                flag = DateUtil.dateNumber(map.get("lendTime").toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(flag==21){
                sendMail(7,map.get("geName").toString(),map.get("email").toString(),map.get("bookName").toString(),map.get("lendTime").toString(),map.get("geNumber").toString());
            }else if(flag==25){
                sendMail(3,map.get("geName").toString(),map.get("email").toString(),map.get("bookName").toString(),map.get("lendTime").toString(),map.get("geNumber").toString());
            }else if(flag==28){
                sendMail(0,map.get("geName").toString(),map.get("email").toString(),map.get("bookName").toString(),map.get("lendTime").toString(),map.get("geNumber").toString());
            }

        }


    }

    public void sendMail(int num,String geName,String email,String bookName,String lendTime,String geNumber){
        System.out.println(num+","+geName+","+email+","+bookName+","+lendTime);
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
            mMessageHelper.setTo(email);//收件人邮箱
            mMessageHelper.setSubject("工会书籍"+num+"天后到期通知");//邮件的主题
            mMessageHelper.setText("<p>"+geName+"您好，您"+lendTime+"借阅的《"+bookName+"》还剩"+num+"天，请及时归还</p><br/>" +
                    "逾期将扣额外金额<br/>" ,true);//邮件的文本内容，true表示文本以html格式打开
            //File file=new File("F:/googleDowload/图片/back1.jpg");//在邮件中添加一张图片
            //FileSystemResource resource=new FileSystemResource(file);
           // mMessageHelper.addInline("fengye", resource);//这里指定一个id,在上面引用
            //mMessageHelper.addAttachment("back1.jpg", resource);//在邮件中添加一个附件
            Inform inform = new Inform();
            inform.setGeNumber(geNumber);
            inform.setTitle("工会书籍"+num+"天后到期通知");
            inform.setDetail(geName+"您好，您"+lendTime+"借阅的《"+bookName+"》还剩"+num+"天，请及时归还" +
                    "逾期将扣额外金额");
            inform.setCreateTime(DateUtil.getDate());
            inform.setUpdateTime(DateUtil.getDate());
            userDao.addInform(inform);
            javaMailSender.send(mMessage);//发送邮件
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
