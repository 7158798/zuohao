package com.jucaifu.common.dfs;

import java.io.File;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import org.junit.Test;

/** 
* FastDFSHelper Tester. 
* 
* @author scofieldcai-dev 
* @since  十二月 11, 2015 
* @version 1.0 
*/ 
public class FastDFSHelperTest extends BaseSpringTest{ 


    @Override
    public void doTest() {
        //TODO: Test goes here...
    } 

    @Test
    public void testUploadFile() throws Exception { 
    }

    /**
     *
     * Method: upload(MultipartFile file)
     * 
     */ 
    @Test
    public void testUpload() throws Exception {
        uploadFile("/Users/scofieldcai-dev/Downloads/jcf-文档/协议html/发行机构-风险揭示书.html","发行机构-风险揭示书.html");
    }

    private void uploadFile(String filePath,String fileName){
        File file = new File(filePath);
        DFSFilePo po = FastDFSHelper.uploadFile(file, fileName);
        LOG.d(this, po);
        LOG.d(this, po.buildFullPath());
    }

    /** 
     * 
     * Method: modifyFile(String oldFileId, InputStream inStream, String fileName) 
     * 
     */ 
    @Test
    public void testModifyFile() throws Exception { 
        //TODO: Test goes here... 
    } 

    /** 
     * 
     * Method: downloadFile(String fileId) 
     * 
     */ 
    @Test
    public void testDownloadFile() throws Exception { 
        //TODO: Test goes here... 
    } 

    /** 
     * 
     * Method: getFullPath(String fileId) 
     * 
     */ 
    @Test
    public void testGetFullPath() throws Exception { 
        //TODO: Test goes here... 
    } 

    /** 
     * 
     * Method: getFastdfsHost() 
     * 
     */ 
    @Test
    public void testGetFastdfsHost() throws Exception { 
        //TODO: Test goes here... 
    } 


} 
