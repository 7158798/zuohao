package com.jucaifu.common.poi;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.jucaifu.common.constants.FilePathConstant;
import com.jucaifu.common.enums.EnumOfficeVersion;
import com.jucaifu.common.log.LOG;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

/**
 * Word2HtmlHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/21.
 */
public final class Word2HtmlHelper {

    private static final String TEMP_PATH = FilePathConstant.TEMP_CACHE_PATH;

    /**
     * Word 2 html str.
     * 优先尝试doc的转化,如果失败,继续尝试docx的转化
     *
     * @param inStream the in stream
     * @return the string
     */
    public static String word2HtmlStr(InputStream inStream) {

        String htmlStr = null;

        try {
            htmlStr = docx2HtmlStr(inStream);
        } catch (Exception docxex) {
            LOG.e(Word2HtmlHelper.class, "word2HtmlStr-docx2HtmlStr", docxex);
        }

        if (htmlStr == null) {
            try {
                htmlStr = doc2HtmlStr(inStream);
            } catch (Exception ex) {
                LOG.e(Word2HtmlHelper.class, "word2HtmlStr-doc2HtmlStr", ex);
            }
        }

        return htmlStr;
    }

    /**
     * Word 2 html str.
     *
     * @param version  the version
     * @param inStream the in stream
     * @return the string
     */
    public static String word2HtmlStr(EnumOfficeVersion version, InputStream inStream) {
        String htmlStr = null;

        try {
            if (version == EnumOfficeVersion.OFFICE2003) {
                htmlStr = doc2HtmlStr(inStream);
            } else if (version == EnumOfficeVersion.OFFICE2007) {
                htmlStr = docx2HtmlStr(inStream);
            }
        } catch (Exception e) {
            LOG.e("", "word2HtmlStr", e);
        }

        return htmlStr;
    }

    /**
     * doc转换为htmlStr
     *
     * @param fileName the file name
     * @return the string
     * @throws TransformerException         the transformer exception
     * @throws IOException                  the iO exception
     * @throws ParserConfigurationException the parser configuration exception
     */
    private static String doc2HtmlStr(String fileName) throws TransformerException, IOException, ParserConfigurationException {
        InputStream inputStream = new FileInputStream(fileName);
        return doc2HtmlStr(inputStream);
    }

    /**
     * Doc 2 html str.
     *
     * @param inStream the in stream
     * @return the string
     * @throws IOException                  the iO exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws TransformerException         the transformer exception
     */
    private static String doc2HtmlStr(InputStream inStream) throws IOException, ParserConfigurationException, TransformerException {

        long startTime = System.currentTimeMillis();
        HWPFDocument wordDocument = new HWPFDocument(inStream);

        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            @Override
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                return "test/" + suggestedName;
            }
        });
        wordToHtmlConverter.processDocument(wordDocument);

        List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
        if (pics != null) {
            for (int i = 0; i < pics.size(); i++) {
                Picture pic = (Picture) pics.get(i);
                try {
                    pic.writeImageContent(new FileOutputStream(TEMP_PATH + pic.suggestFullFileName()));
                } catch (FileNotFoundException e) {
                }
            }
        }
        Document htmlDocument = wordToHtmlConverter.getDocument();

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();

        String htmlStr = new String(outStream.toByteArray());

        LOG.dTag("doc2HtmlStr", "doc2HtmlStr:" + (System.currentTimeMillis() - startTime) + " ms.");

        return htmlStr;
    }

    /**
     * Docx 2 html.
     *
     * @param fileName the file name
     * @return the string
     * @throws TransformerException         the transformer exception
     * @throws IOException                  the iO exception
     * @throws ParserConfigurationException the parser configuration exception
     */
    private static String docx2HtmlStr(String fileName) throws TransformerException, IOException, ParserConfigurationException {
        InputStream inStream = new FileInputStream(fileName);
        return docx2HtmlStr(inStream);
    }

    /**
     * docx格式word转换为html
     *
     * @param inStream the in stream
     * @return the string
     * @throws TransformerException the transformer exception
     * @throws TransformerException the iO exception
     * @throws TransformerException the parser configuration exception
     */
    private static String docx2HtmlStr(InputStream inStream) throws TransformerException, IOException, ParserConfigurationException {

        long startTime = System.currentTimeMillis();

        XWPFDocument document = new XWPFDocument(inStream);

        XHTMLOptions options = XHTMLOptions.create().indent(4);

        try {
            // 导出图片
            File imageFolder = new File(TEMP_PATH);
            options.setExtractor(new FileImageExtractor(imageFolder));
            // URI resolver
            options.URIResolver(new FileURIResolver(imageFolder));
        } catch (Exception e) {
            LOG.e("", "ex", e);
        }

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        XHTMLConverter.getInstance().convert(document, outStream, options);

        String htmlStr = new String(outStream.toByteArray());

        LOG.dTag("docx2HtmlStr", "docx2HtmlStr:" + (System.currentTimeMillis() - startTime) + " ms.");

        return htmlStr;
    }
}
