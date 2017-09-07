package com.zhw.core.net;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;


public class FtpUtil {
	private FTPClient ftpClient;
	private String strIp;
	private int intPort;
	private String user;
	private String password;
	/**
	* 设置缓冲值
	*/
	static final int BUFFER = 8192;

	private static final String ALGORITHM = "PBEWithMD5AndDES";
	
	private static Logger logger = Logger.getLogger(FtpUtil.class.getName());

	/* *
	 * Ftp构造函数
	 */
	public FtpUtil(String strIp, int intPort, String user, String Password) {
		this.strIp = strIp;
		this.intPort = intPort;
		this.user = user;
		this.password = Password;
		this.ftpClient = new FTPClient();
	}
	/**
	 * @return 判断是否登入成功
	 * */
	public boolean ftpLogin() {
		boolean isLogin = false;
		FTPClientConfig ftpClientConfig = new FTPClientConfig();
		ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
		this.ftpClient.setControlEncoding("GBK");
		this.ftpClient.configure(ftpClientConfig);
		try {
			if (this.intPort > 0) {
				this.ftpClient.connect(this.strIp, this.intPort);
			} else {
				this.ftpClient.connect(this.strIp);
			}
			// FTP服务器连接回答
			int reply = this.ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				this.ftpClient.disconnect();
				logger.error("登录FTP服务失败！");
				return isLogin;
			}
			this.ftpClient.login(this.user, this.password);
			// 设置传输协议
			this.ftpClient.enterLocalPassiveMode();
			this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			logger.info("恭喜" + this.user + "成功登陆FTP服务器");
			isLogin = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.user + "登录FTP服务失败！" + e.getMessage());
		}
		this.ftpClient.setBufferSize(1024 * 2);
		this.ftpClient.setDataTimeout(30 * 1000);
		return isLogin;
	}

	/**
	 * @退出关闭服务器链接
	 * */
	public void ftpLogOut() {
		if (null != this.ftpClient && this.ftpClient.isConnected()) {
			try {
				boolean reuslt = this.ftpClient.logout();// 退出FTP服务器
				if (reuslt) {
					logger.info("成功退出服务器");
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.warn("退出FTP服务器异常！" + e.getMessage());
			} finally {
				try {
					this.ftpClient.disconnect();// 关闭FTP服务器的连接
				} catch (IOException e) {
					e.printStackTrace();
					logger.warn("关闭FTP服务器的连接异常！");
				}
			}
		}
	}

	/***
	 * 上传Ftp文件
	 * @param localFile 当地文件
	 * @param romotUpLoadePath上传服务器路径 - 应该以/结束
	 * @throws IOException,FileNotFoundException 
	 * */
	public boolean uploadFile(File localFile, String romotUpLoadePath) throws IOException,FileNotFoundException {
		BufferedInputStream inStream = null;
		boolean success = false;
//		try {
			this.ftpClient.changeWorkingDirectory(romotUpLoadePath);// 改变工作路径
			inStream = new BufferedInputStream(new FileInputStream(localFile));
			logger.info(localFile.getName() + "开始上传.....");
			success = this.ftpClient.storeFile(localFile.getName(), inStream);
			if (success == true) {
				logger.info(localFile.getName() + "上传成功");
				return success;
			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			logger.error(localFile + "未找到");
//		
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//		}
		return success;
	}
	/***
	 * 上传Ftp文件
	 * @param localFile 当地文件
	 * @param romotUpLoadePath上传服务器路径 - 应该以/结束
	 * @throws IOException,FileNotFoundException 
	 * */
	public boolean uploadFileStream(String name ,BufferedInputStream inStream, String romotUpLoadePath) throws IOException,FileNotFoundException {
		boolean success = false;
//		try {
			this.ftpClient.changeWorkingDirectory(romotUpLoadePath);// 改变工作路径
			logger.info(name + "开始上传.....");
			success = this.ftpClient.storeFile(name, inStream);
			if (success == true) {
				logger.info(name + "上传成功");
				return success;
			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			logger.error(localFile + "未找到");
//		
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//		}
		return success;
	}
	
	/***
	 * 下载文件
	 * @param remoteFileName   待下载文件名称
	 * @param localDires 下载到当地那个路径下
	 * @param remoteDownLoadPath remoteFileName所在的路径
	 * */

	public boolean downloadFile(String remoteFileName, String localDires,
			String remoteDownLoadPath) {
		String strFilePath = localDires + remoteFileName;
		BufferedOutputStream outStream = null;
		boolean success = false;
		try {
			this.ftpClient.changeWorkingDirectory(remoteDownLoadPath);
			outStream = new BufferedOutputStream(new FileOutputStream(
					strFilePath));
			logger.info(remoteFileName + "开始下载....");
			success = this.ftpClient.retrieveFile(remoteFileName, outStream);
			if (success == true) {
				logger.info(remoteFileName + "成功下载到" + strFilePath);
				return success;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(remoteFileName + "下载失败");
		} finally {
			if (null != outStream) {
				try {
					outStream.flush();
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (success == false) {
			logger.error(remoteFileName + "下载失败!!!");
		}
		return success;
	}

	/***
	 * @上传文件夹
	 * @param localDirectory
	 *            当地文件夹
	 * @param remoteDirectoryPath
	 *            Ftp 服务器路径 以目录"/"结束
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * */
	public boolean uploadDirectory(String localDirectory,
			String remoteDirectoryPath) throws FileNotFoundException, IOException {
		File src = new File(localDirectory);
		try {
			remoteDirectoryPath = remoteDirectoryPath + src.getName() + "/";
			this.ftpClient.makeDirectory(remoteDirectoryPath);
			// ftpClient.listDirectories();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(remoteDirectoryPath + "目录创建失败");
		}
		File[] allFile = src.listFiles();
		for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
			if (!allFile[currentFile].isDirectory()) {
				String srcName = allFile[currentFile].getPath().toString();
				uploadFile(new File(srcName), remoteDirectoryPath);
			}
		}
		for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
			if (allFile[currentFile].isDirectory()) {
				// 递归
				uploadDirectory(allFile[currentFile].getPath().toString(),
						remoteDirectoryPath);
			}
		}
		return true;
	}

	/***
	 * @下载文件夹
	 * @param localDirectoryPath本地地址
	 * @param remoteDirectory 远程文件夹
	 * */
	public boolean downLoadDirectory(String localDirectoryPath,String remoteDirectory) {
		try {
			String fileName = new File(remoteDirectory).getName();
			localDirectoryPath = localDirectoryPath + fileName + "//";
			new File(localDirectoryPath).mkdirs();
			FTPFile[] allFile = this.ftpClient.listFiles(remoteDirectory);
			for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
				if (!allFile[currentFile].isDirectory()) {
					downloadFile(allFile[currentFile].getName(),localDirectoryPath, remoteDirectory);
				}
			}
//			for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
//				if (allFile[currentFile].isDirectory()) {
//					String strremoteDirectoryPath = remoteDirectory + "/"+ allFile[currentFile].getName();
//					downLoadDirectory(localDirectoryPath,strremoteDirectoryPath);
//				}
//			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("下载文件夹失败");
			return false;
		}
		return true;
	}
	
	/***
	 * @下载文件夹
	 * @param localDirectoryPath本地地址
	 * @param remoteDirectory 远程文件夹
	 * */
	public List<FTPFile> downLoadDirectDirectory(String localDirectoryPath,String remoteDirectory) {
		List<FTPFile> downLoadedFile = new ArrayList<FTPFile>();
		try {
//			String fileName = new File(remoteDirectory).getName();
//			localDirectoryPath = localDirectoryPath + fileName + "//";
			new File(localDirectoryPath).mkdirs();
			this.ftpClient.changeWorkingDirectory(remoteDirectory);
			String[] subFileName= this.ftpClient.listNames(remoteDirectory);
			FTPFile[] allFile = this.ftpClient.listFiles(remoteDirectory);
			for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
				if (!allFile[currentFile].isDirectory()) {
					if(downloadFile(allFile[currentFile].getName(),localDirectoryPath, remoteDirectory)){ //文件下载成功
						downLoadedFile.add(allFile[currentFile]);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("下载文件夹失败");
			return downLoadedFile;
		}
		return downLoadedFile;
	}
	
	
	// FtpClient的Set 和 Get 函数
	public FTPClient getFtpClient() {
		return ftpClient;
	}
	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}
	/**
	* 
	* @param out 压缩输出流对象
	* @param file 
	* @param base
	* @throws Exception
	*/
	public static void zip(ZipOutputStream outputStream, InputStream inputStream) throws Exception {
		File file = new File("d:" + File.separator + "mldn.txt") ;	// 定义要压缩的文件
		File zipFile = new File("d:" + File.separator + "mldn.zip") ;	// 定义压缩文件名称
		InputStream input = new FileInputStream(file) ;	// 定义文件的输入流
		ZipOutputStream zipOut = null ;	// 声明压缩流对象
		zipOut = new ZipOutputStream(new FileOutputStream(zipFile)) ;
		zipOut.putNextEntry(new ZipEntry(file.getName())) ;	// 设置ZipEntry对象
		zipOut.setComment("www.mldnjava.cn") ;	// 设置注释
		int temp = 0 ;
		while((temp=input.read())!=-1){	// 读取内容
			zipOut.write(temp) ;	// 压缩输出
		}
		input.close() ;	// 关闭输入流
		zipOut.close() ;	// 关闭输出流

	}
	
    public static ByteArrayInputStream parse(OutputStream out) throws Exception
    {
        ByteArrayOutputStream   baos=new   ByteArrayOutputStream();
        baos=(ByteArrayOutputStream) out;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }
	public static void main(String[] args) throws IOException {
		FtpUtil ftp=new FtpUtil("127.0.0.1",21,"user","passwd");

		
		
//		ftp.ftpLogin();
//		
//		ftp.downLoadDirectDirectory("E://receive/rcv", "/home/eas/datasend/51000041600000/rcv");
		
		
		
		//上传文件夹
//		ftp.uploadDirectory("d://DataProtemp", "/home/data/");
		//下载文件夹
//		ftp.downLoadDirectory("d://", "/");
		
		//下载文件
//		ftp.downloadFile("哈哈.txt","d://","/");
		
//		File file = new File("D:////哈哈.txt");
//		if(file.exists()){
//			logger.info("文件存在");
//		}
//		ftp.uploadFile(file,"/home/");
//		ftp.ftpLogOut();	
		
		
		String name="testFile.zip";
		String str ="test";
		
		
//		InputStream   in_withcode   =   new   ByteArrayInputStream(str.getBytes("UTF-8")); 
//		ZipInputStream zipIn = new ZipInputStream(in_withcode);  
//		BufferedInputStream inStream = new BufferedInputStream(zipIn);
//		ftp.uploadFileStream(name, inStream, "/home/");
		
//		String textName ="E://哈哈.txt" ;
//		File inputFile = MyIOUtils.writeFileFromString(textName,"随便写点内容！");
//		File sendFile =  MyIOUtils.compressFile("E://哈哈.zip",inputFile,"");
//		ftp.uploadFile(sendFile,"/home/");
//		ftp.downloadFile("哈哈.zip","C://","/home/");
	
	}
}

   
