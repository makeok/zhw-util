package com.zhw.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


public class FileUtil {
	public static final String REALNAME = "realName";
	public static final String STORENAME = "storeName";
	public static final String SIZE = "size";
	public static final String SUFFIX = "suffix";
	public static final String CONTENTTYPE = "contentType";
	public static final String CREATETIME = "createTime";
//	public static final String UPLOADDIR = "uploadDir/";
	public static final String BYTES = "bytes";
	/**
	 * 写文件 
	 * @param f
	 * @param content
	 */
	public static void writeFile(File f,String content){
		  writeFile(f, content,"utf-8");
	}
	/**
	 * 写文件 
	 * @param f
	 * @param content
	 */
	public static void writeFile(File f,String content,String encode){
		  try {
		   if (!f.exists()) {
		    f.createNewFile();
		   }
		   OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(f),encode);
		   BufferedWriter utput = new BufferedWriter(osw);
		   utput.write(content);
		   utput.close();
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	}
	/**
	 * 写文件
	 * @param path
	 * @param content
	 */
	public static void writeFile(String path, String content,String encode) {
	       File f = new File(path);
	       writeFile(f, content,encode);
	}
	/**
	 * 写文件
	 * @param path
	 * @param content
	 */
	public static void writeFile(String path, String content) {
	       File f = new File(path);
	       writeFile(f, content,"utf-8");
	}

	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
	public static String readFile(File file){
		return readFile(file,"UTF-8");
	}
	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
	public static String readFile(File file,String encode){
		String output = "";

		if (file.exists()) {
			if (file.isFile()) {
				try {
					InputStreamReader isr=new InputStreamReader(new FileInputStream(file),encode);
					BufferedReader input = new BufferedReader(isr);
					StringBuffer buffer = new StringBuffer();
					String text;
					while ((text = input.readLine()) != null)
						buffer.append(text + "\n");
					output = buffer.toString();

				} catch (IOException ioException) {
					System.err.println("File Error！");
				}
			} else if (file.isDirectory()) {
				String[] dir = file.list();
				output += "Directory contents：\n";
				for (int i = 0; i < dir.length; i++) {
					output += dir[i] + "\n";
				}
			}

		} else {
			System.err.println("Does not exist！");
		}

		return output;
	}
	/**
	 * 读取文件
	 * @param fileName
	 * @return
	 */
	public static String readFile(String fileName,String encode) {
		File file = new File(fileName);
		return readFile(file,encode);
	}
	/**
	 * 读取文件
	 * @param fileName
	 * @return
	 */
	public static String readFile(String fileName) {
		return readFile(fileName,"utf-8");
	}


	/**
	 * 获取目录下所有文件
	 * @param folder
	 * @return
	 */
	public static List<File> getFiles(String folder){
		File file=new File(folder);
		List<File> files=new ArrayList<File>();
		if (file.exists()) {
			File[] sonFiles=file.listFiles();
			if (sonFiles!=null && sonFiles.length>0) {
				for (int i = 0; i < sonFiles.length; i++) {
					if (!sonFiles[i].isDirectory()) {
						files.add(sonFiles[i]);
					}
				}
			}
		}
		return files;
	}
	/**
	 * 获取目录下所有文件夹
	 * @param folder
	 * @return
	 */
	public static List<File> getFolders(String folder){
		File file=new File(folder);
		List<File> files=new ArrayList<File>();
		if (file.exists()) {
			File[] sonFiles=file.listFiles();
			if (sonFiles!=null && sonFiles.length>0) {
				for (int i = 0; i < sonFiles.length; i++) {
					if (sonFiles[i].isDirectory()) {
						files.add(sonFiles[i]);
					}
				}
			}
		}
		return files;
	}
	/**
	 * 判断是否有子目录
	 * @param folder
	 * @return
	 */
	public static boolean hasSonFolder(String folder){
		File file=new File(folder);
		return hasSonFolder(file);
	}
	/**
	 * 判断是否有子目录
	 * @param folder
	 * @return
	 */
	public static boolean hasSonFolder(File file){
		if (file.exists()) {
			File[] sonFiles=file.listFiles();
			if (sonFiles!=null && sonFiles.length>0) {
				for (int i = 0; i < sonFiles.length; i++) {
					if (sonFiles[i].isDirectory()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * 创建目录
	 * @param folder
	 */
	public static void mkdir(String folder){
		File file=new File(folder);
		if (!file.exists()) {
			file.mkdir();
		}
	}
	/**
	 * 复制文件
	 * @param src
	 * @param dst
	 */
	public static void copy(File src, File dst) {
		try {
			int BUFFER_SIZE = 32 * 1024;
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(src);
				out = new FileOutputStream(dst);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count;
				while ((count = in.read(buffer)) != -1) {
					out.write(buffer, 0, count);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 复制文件
	 * @param src
	 * @param dst
	 */
	public static void copy(MultipartFile src, File dst) {
		try {
			if (src!=null) {
				InputStream strinput = src.getInputStream();
				OutputStream stroutput = new FileOutputStream(dst);
				int byteread = 0;
				byte[] buffer = new byte[8192];
				while ((byteread = strinput.read(buffer, 0, 8192)) != -1) {
					stroutput.write(buffer, 0, byteread);
				}
				strinput.close();
				stroutput.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * 复制文件夹
     * @param sourceDir
     * @param targetDir
     * @throws IOException
     */
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
    	if (new File(sourceDir).exists()) {
            // 新建目标目录
        	File targetFolder=new File(targetDir);
        	if (!targetFolder.exists()) {
    			targetFolder.mkdirs();
    		}
            // 获取源文件夹当前下的文件或目录
            File[] file = (new File(sourceDir)).listFiles();
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()) {
                    // 源文件
                    File sourceFile = file[i];
                    // 目标文件
                    File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                    copy(sourceFile, targetFile);
                }
                if (file[i].isDirectory()) {
                    // 准备复制的源文件夹
                    String dir1 = sourceDir + "/" + file[i].getName();
                    // 准备复制的目标文件夹
                    String dir2 = targetDir + "/" + file[i].getName();
                    copyDirectiory(dir1, dir2);
                }
            }
		}
    }

	/**
	 * 获取扩展名
	 */
	public static String getExt(File src){
		if (src!=null) {
			String name=src.getName();
			return name.substring(name.lastIndexOf("."), name.length());
		}
		return "";
	}
	/**
	 * 获取文件名
	 */
	public static String getFileName(MultipartFile uploadFile){
		if (uploadFile!=null) {
			String name=uploadFile.getOriginalFilename().replace("\\", "/");
			return name.substring(name.lastIndexOf("/")+1, name.length());
		}
		return "";
	}
	/**
	 * 获取扩展名
	 */
	public static String getExt(String src){
		if (src!=null) {
			return src.substring(src.lastIndexOf("."), src.length());
		}
		return "";
	}
	/**
	 * 删除指定文件
	 * @param path
	 */
	public static void del(String path){
		File file=new File(path);
		deleteFile(file);
	}
	/**
	 * 递归删除文件夹下所有文件
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.exists()) { //判断文件是否存在
			if (file.isFile()) { //判断是否是文件
				file.delete(); //delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { //否则如果它是一个目录
				File files[] = file.listFiles(); //声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { //遍历目录下所有的文件
					deleteFile(files[i]); //把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		}
	} 
	/**
	 * 输出流
	 * @param response
	 * @param filepath
	 */
	public static void outPut(HttpServletResponse response,String filepath,String name){
		try {
			File file = new File(filepath);
			String filename = file.getName();
			if(MyStringUtil.isEmpty(name)){
				name = filename; 
			}
			String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
			InputStream fis = new BufferedInputStream(new FileInputStream(filepath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			response.addHeader("Content-Length", "" + file.length());
			response.addHeader("Content-Disposition", "attachment; filename=" + new String(name.getBytes("utf-8"),"ISO8859-1"));//解决在弹出文件下载框不能打开文件的问题   
			response.setContentLength((int) file.length());    
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			if (ext.equals("xls")) {
				response.setContentType("application/msexcel");
			} else {
				response.setContentType("application/octet-stream;charset=utf-8");
			}
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public static void upzip(String inputfile,String outpath) throws Exception{
		File file = new File(inputfile);//压缩文件  
		ZipFile zipFile = new ZipFile(file);//实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile  
		//实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象  
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));  
		ZipEntry zipEntry = null;  
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {  
			String fileName = zipEntry.getName();  
			File temp = new File(outpath + fileName);  
			temp.getParentFile().mkdirs();  
			OutputStream os = new FileOutputStream(temp);  
			//通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流  
			InputStream is = zipFile.getInputStream(zipEntry);  
			int len = 0;  
			while ((len = is.read()) != -1)  
				os.write(len);  
			os.close();  
			is.close();  
		}  
		zipInputStream.close();  
	}
	/**
	 * 获取上传文件的详细信息：包括文件名，文件字节 byte[]
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> getUploadFileDetails(
			HttpServletRequest request) throws IOException {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = mRequest.getFileMap();

		String fileName = null;
		int i = 0;
		for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet()
				.iterator(); it.hasNext(); i++) {

			Map.Entry<String, MultipartFile> entry = it.next();
			MultipartFile mFile = entry.getValue();
			fileName = mFile.getOriginalFilename();

			Map<String, Object> map = new HashMap<String, Object>();
			// 固定参数值对
			map.put(REALNAME, fileName);
			map.put(SUFFIX,
					fileName.substring(fileName.lastIndexOf(".") + 1,
							fileName.length()));
			map.put(CONTENTTYPE, "application/octet-stream");
			map.put(CREATETIME, new Date());
			map.put(BYTES, mFile.getBytes());
			result.add(map);
		}
		return result;
	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param storeName
	 * @param contentType
	 * @param realName
	 * @throws Exception
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String storeName, String contentType,
			String realName) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String ctxPath = request.getSession().getServletContext()
				.getRealPath("/");
		String downLoadPath = ctxPath + storeName;

		long fileLength = new File(downLoadPath).length();

		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(realName.getBytes("utf-8"), "ISO8859-1"));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}

		bis.close();
		bos.close();
	}
	
	public static void download2(HttpServletRequest request,
			HttpServletResponse response, String url, String contentType,
			String realName) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		/*String ctxPath = request.getSession().getServletContext()
				.getRealPath("/");
		String downLoadPath = "E:\\Users\\sumist\\workspace3.7\\WeathModif\\src\\main\\webapp\\resources\\docs\\ttt.doc";*/

		long fileLength = new File(url).length();
		response.reset();  
		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(realName.getBytes("utf-8"), "ISO8859-1"));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(url));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}

		bis.close();
		bos.close();
	}
	
	
	/**
	 * @param response
	 * @param product
	 * @throws IOException
	 */
	public static void down(HttpServletResponse response, byte[] data,
			String fileName, Long length) throws IOException {
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
		response.addHeader("Content-Length", "" + length);
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outputStream = new BufferedOutputStream(
				response.getOutputStream());
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
	}
	
    /** 
     * 获得类的基路径，打成jar包也可以正确获得路径 
     * @return  
     */  
    public static String getBasePath(){  
        /* 
        /D:/zhao/Documents/NetBeansProjects/docCompare/build/classes/ 
        /D:/zhao/Documents/NetBeansProjects/docCompare/dist/bundles/docCompare/app/docCompare.jar 
        */  
        String filePath = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();  
        if (filePath.endsWith(".jar")){  
            filePath = filePath.substring(0, filePath.lastIndexOf("/"));  
            try {  
                filePath = URLDecoder.decode(filePath, "UTF-8"); //解决路径中有空格%20的问题  
            } catch (UnsupportedEncodingException ex) {  
            	ex.printStackTrace();
            }    
        }  
        File file = new File(filePath);  
        filePath = file.getAbsolutePath();  
        return filePath;  
    }  
    
	public static void main(String[] args) {
		try {
			upzip("D:\\test.zip","D:\\un\\");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
