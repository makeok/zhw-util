package com.zhw.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

import com.zhw.core.util.JsonUtil;
import com.zhw.web.util.ServletUtils;


public class AbstractController {

	public Logger logger = Logger.getLogger(AbstractController.class);

	/**
	 * 打印text数据
	 * 
	 * @param xml
	 * @param response
	 */
	public void printTextData(String text, HttpServletResponse response) {

		ServletUtils.setTextAjaxResponseHeader(response);

		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(text);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印text数据
	 * 
	 * @param xml
	 * @param response
	 */
	public void printXmlData(String text, HttpServletResponse response) {

		ServletUtils.setXmlAjaxResponseHeader(response);

		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(text);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * object 转json
	 * 
	 * @param response
	 */
	public void printJsonObjectData(Object obj, HttpServletResponse response) {
		ServletUtils.setJsonAjaxResponseHeader(response);
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(JsonUtil.toJSON(obj));
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * object 转json
	 * 
	 * @param response
	 */
	public void printJsonObjectData2(Object obj, HttpServletResponse response) {
		ServletUtils.setJsonAjaxResponseHeader(response);
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(JsonUtil.toJSON(obj));
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * object 转json
	 * 
	 * @param response
	 */
	public void printJsonArrayData(Object obj, HttpServletResponse response) {
		ServletUtils.setJsonAjaxResponseHeader(response);
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(JsonUtil.toJSON(obj));
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * object 转json
	 * 
	 * @param response
	 */
	public void printJsonTextData(String str, HttpServletResponse response) {
		ServletUtils.setJsonAjaxResponseHeader(response);
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(str);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出图片
	 * 
	 * @param f
	 * @param response
	 */
	public void printImg(File f, HttpServletResponse response) {
		ServletUtils.setImgResponseHeader(response);
		try {
			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(new FileInputStream(f), out);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 输出文件
	 * 
	 * @param f
	 * @param response
	 */
	public void printFile(File f, HttpServletResponse response) {
		ServletUtils.setFileDownloadHeader(response, f.getName());
		try {
			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(new FileInputStream(f), out);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
