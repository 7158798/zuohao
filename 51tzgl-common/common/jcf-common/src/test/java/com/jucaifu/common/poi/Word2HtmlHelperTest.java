package com.jucaifu.common.poi;

import java.io.FileInputStream;
import java.io.InputStream;

import com.base.BaseSpringTest;
import com.jucaifu.common.enums.EnumOfficeVersion;
import com.jucaifu.common.log.LOG;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Word2HtmlHelper Tester.
 *
 * @author scofieldcai-dev
 * @version 1.0
 * @since 十二月 21, 2015
 */
public class Word2HtmlHelperTest extends BaseSpringTest {

    @Before
    public void before() throws Exception {
        super.after();
    }

    @After
    public void after() throws Exception {
        super.after();
    }

    @Override
    public void doTest() {
        //TODO: Test goes here...
    }

    /**
     * Method: word2HtmlStr(EnumOfficeVersion version, InputStream inStream)
     */
    @Test
    public void testWord2HtmlStr() throws Exception {

        String docxFilePath = "/Users/scofieldcai-dev/Desktop/web前端inbox/12345.docx";
        InputStream inStream = new FileInputStream(docxFilePath);
        String htmlStr = Word2HtmlHelper.word2HtmlStr(EnumOfficeVersion.OFFICE2007, inStream);
        LOG.d(this, htmlStr);

        LOG.dTag(this, "docFile");
        
        String docFilePath = "/Users/scofieldcai-dev/Desktop/web前端inbox/12345.doc";
        inStream = new FileInputStream(docxFilePath);
        htmlStr = Word2HtmlHelper.word2HtmlStr(EnumOfficeVersion.OFFICE2007, inStream);
        LOG.d(this, htmlStr);
    }

} 
