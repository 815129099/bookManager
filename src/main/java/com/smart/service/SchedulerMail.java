
package com.smart.service;

import com.smart.bean.Inform;
import com.smart.bean.MoneyRecord;
import com.smart.bean.User;
import com.smart.dao.BookDao;
import com.smart.dao.UserDao;
import com.smart.redis.DateUtil;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerMail {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private UserDao userDao;

    public SchedulerMail() {
    }

    public void test() {
        System.out.println("每隔10秒钟，啦啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
    }

    @Scheduled(
            cron = "0 0 8 * * ?"
    )
    public void sendMail03() {
        List<Map<String, Object>> list = this.bookDao.getRecordList();
        Iterator var2 = list.iterator();

        while(var2.hasNext()) {
            Map<String, Object> map = (Map)var2.next();
            int flag = 0;

            try {
                flag = DateUtil.dateNumber(map.get("lendTime").toString());
            } catch (ParseException var6) {
                var6.printStackTrace();
            }

            if (flag == 21) {
                this.sendMail(7, map.get("geName").toString(), map.get("email").toString(), map.get("bookName").toString(), map.get("lendTime").toString(), map.get("geNumber").toString());
            } else if (flag == 25) {
                this.sendMail(3, map.get("geName").toString(), map.get("email").toString(), map.get("bookName").toString(), map.get("lendTime").toString(), map.get("geNumber").toString());
            } else if (flag == 28) {
                this.sendMail(0, map.get("geName").toString(), map.get("email").toString(), map.get("bookName").toString(), map.get("lendTime").toString(), map.get("geNumber").toString());
            } else if (flag > 28) {
                this.deductMoney(flag - 28, map.get("geNumber").toString(), map.get("bookName").toString());
            }
        }

    }

    public void deductMoney(int num, String geNumber, String bookName) {
        MoneyRecord moneyRecord = new MoneyRecord();
        moneyRecord.setGeNumber(geNumber);
        moneyRecord.setAccount("0.5");
        moneyRecord.setMoneyTime(DateUtil.getDate());
        moneyRecord.setReason("借阅的《" + bookName + "》逾期" + num + "天未还");
        User user = this.userDao.getUserById(geNumber);
        moneyRecord.setMoneyBefore(user.getUserMoney());
        moneyRecord.setMoneyNow(Float.parseFloat(user.getUserMoney()) - 0.5F + "");
        this.userDao.deductMoneyRecord(moneyRecord);
        user.setUserMoney(Float.parseFloat(user.getUserMoney()) - 0.5F + "");
        this.userDao.deductMoney(user);
    }

    public void sendMail(int num, String geName, String email, String bookName, String lendTime, String geNumber) {
        System.out.println(num + "," + geName + "," + email + "," + bookName + "," + lendTime);
        MimeMessage mMessage = this.javaMailSender.createMimeMessage();
        Properties prop = new Properties();

        try {
            prop.load(this.getClass().getResourceAsStream("/mail.properties"));
            String from = prop.get("mail.smtp.username") + "";
            MimeMessageHelper mMessageHelper = new MimeMessageHelper(mMessage, true);
            mMessageHelper.setFrom(from);
            mMessageHelper.setTo(email);
            mMessageHelper.setSubject("工会书籍" + num + "天后到期通知");
            mMessageHelper.setText("<p>" + geName + "您好，您" + lendTime + "借阅的《" + bookName + "》还剩" + num + "天，请及时归还</p><br/>逾期将扣额外金额<br/>", true);
            Inform inform = new Inform();
            inform.setGeNumber(geNumber);
            inform.setTitle("工会书籍" + num + "天后到期通知");
            inform.setDetail(geName + "您好，您" + lendTime + "借阅的《" + bookName + "》还剩" + num + "天，请及时归还逾期将扣额外金额");
            inform.setCreateTime(DateUtil.getDate());
            inform.setUpdateTime(DateUtil.getDate());
            this.userDao.addInform(inform);
            this.javaMailSender.send(mMessage);
        } catch (MessagingException var12) {
            var12.printStackTrace();
        } catch (IOException var13) {
            var13.printStackTrace();
        }

    }
}
