package com.zhw.core.poi;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.IURIResolver;
import org.apache.poi.xwpf.converter.xhtml.DefaultContentHandlerFactory;
import org.apache.poi.xwpf.converter.xhtml.IContentHandlerFactory;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;

import com.zhw.core.util.MyDateUtil;
import com.zhw.core.util.MyEncodeUtil;

import sun.misc.BASE64Encoder;


public class Word2Html {
	private static final String encoding = "UTF-8";  
	/**
	 * 转换doc
	 * @param fileName
	 * @param request
	 * @return
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static String convert2Html(String fileName,HttpServletRequest request)
			throws TransformerException, IOException,
			ParserConfigurationException {
		final String contextPath=request.getContextPath();
		final String uuid=UUID.randomUUID().toString();
		HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(fileName));
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument());
		 wordToHtmlConverter.setPicturesManager( new PicturesManager()
         {
             public String savePicture( byte[] content,
                     PictureType pictureType, String suggestedName,
                     float widthInches, float heightInches )
             {
                 return contextPath+"/upload/"+MyDateUtil.DateToStr(new Date(), "yyyyMMdd")+"/"+uuid+suggestedName;
             }
         } );
		wordToHtmlConverter.processDocument(wordDocument);
		//save pictures
		List pics=wordDocument.getPicturesTable().getAllPictures();
		if(pics!=null){
			for(int i=0;i<pics.size();i++){
				Picture pic = (Picture)pics.get(i);
				try {
					File folder=new File(request.getRealPath("/upload/"+MyDateUtil.DateToStr(new Date(), "yyyyMMdd")));
					if (!folder.exists()) {
						folder.mkdirs();
					}
					pic.writeImageContent(new FileOutputStream(request.getRealPath("/upload/"+MyDateUtil.DateToStr(new Date(), "yyyyMMdd")
							+"/" +uuid+pic.suggestFullFileName())));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}  
			}
		}
		Document htmlDocument = wordToHtmlConverter.getDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(out);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		out.close();
		return new String(out.toByteArray());
	}

	/**
	 * 转换docx
	 * @param fileName
	 * @param request
	 * @return
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static String convert2Htmlx(String fileName,HttpServletRequest request)
			throws TransformerException, IOException,
			ParserConfigurationException {
		final String contextPath=request.getContextPath();
		final String uuid=UUID.randomUUID().toString();

		
		
		XWPFDocument document = new XWPFDocument(new FileInputStream(new File(  
				fileName)));  
		final String imageUrl=request.getContextPath()+"/upload/"+MyDateUtil.DateToStr(new Date(), "yyyyMMdd")+"/"+uuid+"/";
        XHTMLOptions options = XHTMLOptions.create();// .indent( 4 );  
        IContentHandlerFactory f = new DefaultContentHandlerFactory();  
		File folder=new File(request.getRealPath("/upload/"+MyDateUtil.DateToStr(new Date(), "yyyyMMdd")+"/"+uuid));
		if (!folder.exists()) {
			folder.mkdirs();
		}
        // Extract image  
        options.setExtractor(new FileImageExtractor(folder));  
        // URI resolver  
        options.URIResolver(new IURIResolver() {  
            public String resolve(String uri) {  
                return imageUrl + uri;  
            }  
        });  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        ContentHandler contentHandler = f.create(out, null, options);  
        XHTMLConverter.getInstance().convert(document, out, options);  
        out.close();  
        return MyEncodeUtil.unicode2Ascii(out.toString()); 

	}
	
	
	/**
	 * 转换doc
	 * @param fileName
	 * @param request
	 * @return
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static String convert2Html(String fileName)
			throws TransformerException, IOException,
			ParserConfigurationException {
		HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(fileName));
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument());
		 wordToHtmlConverter.setPicturesManager( new PicturesManager()
         {
             public String savePicture( byte[] content,
                     PictureType pictureType, String suggestedName,
                     float widthInches, float heightInches )
             {
            	 String out = "data:"+pictureType.getMime()+";base64,";
            	 BASE64Encoder be = new BASE64Encoder();
            	 out += be.encode(content);
                 return out; 
             }
         } );
		wordToHtmlConverter.processDocument(wordDocument);
		Document htmlDocument = wordToHtmlConverter.getDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(out);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, encoding);
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		out.close();
		return new String(out.toByteArray());
	}
	/**
	 * 转换docx
	 * @param fileName
	 * @param request
	 * @return
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static String convert2Htmlx(String fileName)
			throws TransformerException, IOException,
			ParserConfigurationException {
//		String ss = "";
//		XWPFDocument xwdoc = new XWPFDocument(POIXMLDocument.openPackage(fileName));
//		POIXMLTextExtractor ex = new XWPFWordExtractor(xwdoc);
//		ss = ex.getText().trim();
//		return ss;
		
		final XWPFDocument document = new XWPFDocument(new FileInputStream(new File(  
				fileName)));  
//		XWPFDocument document = new XWPFDocument( AbstractXWPFPOIConverterTest.class.getResourceAsStream( fileInName ) );  
		
//		final String imageUrl=ClassLoader.getSystemResource("").getFile();
        XHTMLOptions options = XHTMLOptions.create();//.indent( 4 );  
        options.setOmitHeaderFooterPages(false);
        options.setIgnoreStylesIfUnused(true);
        options.setFragment(false);
        IContentHandlerFactory f = new DefaultContentHandlerFactory();  
//		File folder=new File(imageUrl+"/upload/");
//		if (!folder.exists()) {
//			folder.mkdirs();
//		}
        // Extract image  
//        options.setExtractor(new FileImageExtractor(folder){
//        	public void extract(java.lang.String imagePath, byte[] imageData) throws java.io.IOException{
//        		System.out.println(imagePath);
//        		String type = new MimetypesFileTypeMap().getContentType(imagePath);
//        	}
//        });  
        
        // URI resolver  
        options.URIResolver(new IURIResolver() {
            public String resolve(String uri) {  
            	List<XWPFPictureData> plist= document.getAllPictures();
            	for(XWPFPictureData each:plist){
            		
//            		System.out.println(each.toString()+","+each.getFileName());
            		

                	String pname = each.getPackagePart().getPartName().getName();
            		if(pname.endsWith(uri)){
//                    	XWPFPictureData pd = document.getPictureDataByID(uri);
//            			System.out.println("ok");
//                    	int type = each.getPictureType();
                    	byte[] pdata = each.getData();
                    	String name = each.getPackagePart().getContentType();
	                   	String out = "data:"+name+";base64,";
	                	BASE64Encoder be = new BASE64Encoder();
	                	out += be.encode(pdata);
	                    return out; 
            		}
            	}

                return uri;  
            }  
        });  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        ContentHandler contentHandler = f.create(out, null, options);  
        XHTMLConverter.getInstance().convert(document, out, options);
        
//		DOMSource domSource = new DOMSource(document);
//		StreamResult streamResult = new StreamResult(out);
//		TransformerFactory tf = TransformerFactory.newInstance();
//		Transformer serializer = tf.newTransformer();
//		serializer.setOutputProperty(OutputKeys.ENCODING, encoding);
//		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
//		serializer.setOutputProperty(OutputKeys.METHOD, "html");
//		serializer.transform(domSource, streamResult);
        
        out.close();  
        return MyEncodeUtil.unicode2Ascii(out.toString().replace("<head>", "<head>\n"+
        		"<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">")); 

	}
	public static String word2Html(String fileName) throws TransformerException, IOException, ParserConfigurationException{
        if( fileName == null || "".equals(fileName) ) return "";  
        File file = new File(fileName);  
        if( !(file.exists() && file.isFile()) )  {
            return "【"+fileName+"】不存在";  
        }
		String  prefix=fileName.substring(fileName.lastIndexOf(".")+1);//获取文件后缀名
		if("doc".equals(prefix) || "DOC".equals(prefix)){
			return convert2Html(fileName);
		}else if("docx".equals(prefix) || "DOCX".equals(prefix)){
			return convert2Htmlx(fileName);
		}
		return "文件类型不是doc或者docx！";
	}
	
	public static void main(String [] args) throws TransformerException, IOException, ParserConfigurationException{
		String html = word2Html("D://xxx.doc");
		FileUtils.writeStringToFile(new File("C:\\Users\\Administrator\\Desktop\\testword.html"), html);
		//System.out.println(html);
	}
}

