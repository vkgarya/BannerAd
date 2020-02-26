package com.coupon.utils;

import com.coupon.properties.PropertyManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CampaignUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignUtil.class);
    private static SimpleDateFormat sdf_yyMMddHHmmss=new SimpleDateFormat("yyMMddHHmmss");
    public static String getTimestamp_yyMMddHHmmss(){
        return  sdf_yyMMddHHmmss.format(new Date());
    }

    public static  String getRandom(int length){
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String getRandomTxnIdForAdRequest(){
        return getTimestamp_yyMMddHHmmss()+getRandom(10);
    }

    public static String getRandomTxnIdForCampaign(){
        return getRandom(10);
    }

    private static List<Integer> list = new ArrayList<>();

    public static String getTimestamp(){
        return getTimestamp_yyMMddHHmmss();
    }

    public static String getClientIP(final HttpServletRequest request){
        String remoteAdr = request.getHeader("X-FORWADED-FOR");
        if (remoteAdr == null || "".equals(remoteAdr.trim())) {
            remoteAdr = request.getRemoteAddr();
        }
        return  remoteAdr;
    }

    public static String getTimeStamp() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
        return sdf.format(new Date());
    }

    static {
        for (Integer i = 10; i < 99; i++) {
            list.add(i);
        }
    }

    public static String getRandomID() {
        return getTimeStamp() + list.get(new Random().nextInt(list.size()));
    }

    public static String uploadFile(final MultipartFile multipartFile){
        String url =null;
        String fileName=multipartFile.getOriginalFilename();
        LOGGER.info("Uploaded File : "+multipartFile.getName());
        String newFilePath= "/tmp/"+CampaignUtil.getRandomID() + "_" + fileName;
        File newFile=new File(newFilePath);
        LOGGER.info("NEW FILE NAME : "+newFilePath);

        try {
            multipartFile.transferTo(newFile);
            LOGGER.info(" "+newFile.getAbsolutePath()+" exist : "+newFile.exists());
            url=runS3Command(newFile);
            LOGGER.info(" "+newFile.getAbsolutePath()+" exist : "+newFile.exists());
        }catch (Exception e){
            LOGGER.error("Exception while writing file to disk : "+e);
            e.printStackTrace();
        }
        return url;
    }


    public static String  runS3Command(final File file) {
        String fileHttpUrl =null;
        try {
            LOGGER.info("UPLOADED FILE PATH : "+file.getAbsolutePath());

            File fileExist=new File(file.getAbsolutePath());
            LOGGER.info(" "+file.getAbsolutePath()+" exist : "+file.exists());


            String cmd = "aws s3 cp @@OLD_FILE_NAME@@ s3://we4global/empay/assets/@@FILE_NAME@@";
            cmd = cmd.replace("@@OLD_FILE_NAME@@", file.getAbsolutePath());
            cmd = cmd.replace("@@FILE_NAME@@", file.getName());
            LOGGER.info("S3 Command : "+cmd);
            LOGGER.info(" "+file.getAbsolutePath()+" exist : "+file.exists());
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(cmd);
            pr.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "";
            while ((line = buf.readLine()) != null) {
                LOGGER.error(" S3 Command output : "+line);
            }
            LOGGER.info(" "+file.getAbsolutePath()+" exist : "+file.exists());
             fileHttpUrl=PropertyManager.getS3AssetsURL() + File.separator + file.getName();

        } catch (Exception e) {
            LOGGER.error("Exception while uploading file to S3 : "+e);
            e.printStackTrace();
            return null;
        }finally{
            try{
                LOGGER.info(" Deleted file : "+ Files.deleteIfExists(Paths.get(file.getAbsolutePath())));
            }catch (Exception e){
                LOGGER.error("Exception while deleting file from server : "+e);
                e.printStackTrace();
            }
        }
        return fileHttpUrl;
    }

}
