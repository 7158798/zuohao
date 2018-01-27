package com.jucaifu.common.mail;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.jucaifu.common.context.ApplicationContextHelper;
import com.jucaifu.common.context.SpringPropReaderHelper;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.file.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * MailSendHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/30.
 */
public class MailSendHelper {

    private static String SENDER_MAIL_ADDRESS = null;

    /**
     * Gets sender.
     *
     * @return the sender
     */
    public static String getSender() {

        if (SENDER_MAIL_ADDRESS == null) {
            SENDER_MAIL_ADDRESS = SpringPropReaderHelper.getProperty("mail.username");
        }

        return SENDER_MAIL_ADDRESS;
    }

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage simpleMailMessage;


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MailSendHelper getInstance() {
        return ApplicationContextHelper.getInstance().getBean(MailSendHelper.class);
    }

    /**
     * Send boolean.
     *
     * @param sendInfo the send info
     * @return the boolean
     */
    public boolean send(final MailSendInfo sendInfo) {

        boolean result = false;

        if (sendInfo != null) {

            try {
                simpleMailMessage.setFrom(simpleMailMessage.getFrom());
                simpleMailMessage.setTo(sendInfo.getTo());
                simpleMailMessage.setSubject(sendInfo.getSubject());
                simpleMailMessage.setText(sendInfo.getContent());

                mailSender.send(simpleMailMessage);

                result = true;

            } catch (Exception e) {
                LOG.e(MailSendHelper.class, "send-email-e", e);
            }


        }

        return result;
    }

    /**
     * 发送支持附件的邮件
     *
     * @param sendInfo the send info
     * @return the boolean
     */
    public boolean sendComplexMail(final MailSendInfo sendInfo) {
        boolean result = false;

        List<File> tempFileList = new ArrayList<>();

        if (sendInfo != null) {

            List<MailSendAttachmentInfo> attachmentList = sendInfo.getAttachmentList();

            try {
                MimeMessage msg = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(msg, true);

                helper.setFrom(simpleMailMessage.getFrom());
                helper.setTo(sendInfo.getToArray());
                helper.setCc(sendInfo.getCcArray());
                helper.setSubject(sendInfo.getSubject());
                helper.setText(sendInfo.getContent());
                if (attachmentList != null) {
                    String attachmentName;
                    File tempFile;
                    for (MailSendAttachmentInfo attachment : attachmentList) {
                        attachmentName = attachment.getName();

                        tempFile = FileHelper.getTempFile(attachmentName, attachment.getInStream());
                        tempFileList.add(tempFile);

                        helper.addAttachment(attachmentName, tempFile);
                    }
                }

                mailSender.send(msg);

                result = true;

            } catch (Exception e) {

                LOG.e(MailSendHelper.class, "sendComplexMail-e", e);

            } finally {

//                if (attachmentList != null) {
//                    for (MailSendAttachmentInfo attachmentInfo : attachmentList) {
//                        try {
//                            attachmentInfo.getInStream().close();
//                        } catch (IOException e) {
//                        }
//                    }
//                }

                for (File tempFile : tempFileList) {
                    FileHelper.deleteFile(tempFile);
                }

            }

        }

        return result;
    }

}
