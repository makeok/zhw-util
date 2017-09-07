package com.zhw.core.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

import com.mysql.fabric.xmlrpc.base.Array;
import com.zhw.core.log.Syslog;

public class LocalhostUtil {

	public static String getLocalHostIP() {
		String localHostIP = "";
		try {
			Enumeration<?> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				System.out.println(netInterface.getName());
				Enumeration<?> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						localHostIP = ip.getHostAddress();
						break;
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
			// Log.error("获取本机IP地址出错：" + ex.getMessage(), ex);
		}

		return localHostIP;
	}

	public static InetAddress getInetAddress() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("unknown host!");
		}
		return null;
	}

	public static String getHostIp() throws UnknownHostException {
		InetAddress netAddress = InetAddress.getLocalHost();
		if (null == netAddress) {
			return null;
		}
		String ip = netAddress.getHostAddress(); // get the ip address
		return ip;
	}

	public static String getHostName() throws UnknownHostException {
		InetAddress netAddress = InetAddress.getLocalHost();
		if (null == netAddress) {
			return null;
		}
		String name = netAddress.getHostName(); // get the host address
		return name;
	}

	/**
	 * 判断当前系统是否windows
	 * 
	 * @return
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	/**
	 * 取当前系统站点本地地址 linux下 和 window下可用 add by RWW
	 * 
	 * @return
	 */
	public static String getLocal1stIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
//				System.out.println(ip.getHostAddress());
				sIP += ip.getHostAddress();
//				sIP+=",";
			}
			// 如果是Linux操作系统
			else {
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
//							System.out.println(ip.getHostAddress());
							sIP += ip.getHostAddress();
//							sIP+=",";
							 break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Syslog.error("MultiThreadedTrapReceiver 453: " + e.getMessage());
		}
		return sIP;
	}

	public static String[] getAllLocalHostIP() {
		List<String> ret = new ArrayList<String>();
		try {
			String hostName = getHostName();
			if (hostName.length() > 0) {
				InetAddress[] addrs = InetAddress.getAllByName(hostName);
				if (addrs.length > 0) {
					for (int i = 0; i < addrs.length; i++) {
						InetAddress ip = addrs[i];
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							ret.add(ip.getHostAddress());
						}
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String[] arr = new String[ret.size()];
		return ret.toArray(arr);
	}
	public static String[] getAllLocalMac() {
		List<String> ret = new ArrayList<String>();
		try {
			String hostName = getHostName();
			if (hostName.length() > 0) {
				InetAddress[] addrs = InetAddress.getAllByName(hostName);
				if (addrs.length > 0) {
					for (int i = 0; i < addrs.length; i++) {
						InetAddress ip = addrs[i];
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							NetworkInterface netCard = NetworkInterface.getByInetAddress(ip);
							byte[] addr = netCard.getHardwareAddress();
	//						ret[i] = addrs[i].getHostAddress();
							ret.add(toMacString(addr));
						}
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String[] arr = new String[ret.size()];
		return ret.toArray(arr);
	}

	private static String toMacString(byte[] bys) {
		if (bys == null || bys.length <= 0) {
			return "";
		}
		char[] HEX = "0123456789ABCDEF".toCharArray();
		char[] chs = new char[bys.length * 3 - 1];
		for (int i = 0, k = 0; i < bys.length; i++) {
			if (i > 0) {
				chs[k++] = '-';
			}
			chs[k++] = HEX[(bys[i] >> 4) & 0xf];
			chs[k++] = HEX[bys[i] & 0xf];
		}
		return new String(chs);
	}

	public static void testnetcard() throws SocketException {
		for (Enumeration<NetworkInterface> i = NetworkInterface.getNetworkInterfaces(); i.hasMoreElements();) {
			NetworkInterface ni = i.nextElement();
			System.out.println("NETWORK CARD NAME:" + ni.getDisplayName());
			// ni.getInetAddresses().nextElement().getHostAddress();
			System.out.println("MAC:" + toMacString(ni.getHardwareAddress()));
			for (Enumeration<InetAddress> j = ni.getInetAddresses(); j.hasMoreElements();) {
				System.out.println("IP:" + j.nextElement().getHostAddress());
			}
		}
	}

	public static String getLocalMacAddr() {
		try {
			InetAddress address = InetAddress.getLocalHost();
			NetworkInterface netCard = NetworkInterface.getByInetAddress(address);
			byte[] addr = netCard.getHardwareAddress();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < addr.length; i++) {
				if (i != 0) {
					sb.append("-");
				}

				String string = Integer.toHexString(addr[i] & 0xff);
				sb.append(string.length() == 1 ? "0" + string : string);
			}

			return sb.toString().toUpperCase();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getCpuSerialId() {
//		System.out.println(System.getProperties().toString());
		String os = System.getProperty("os.name").toLowerCase();
//		System.out.println(os);
		String cpuid = "";
		if (os.contains("windows")) {
			cpuid = getWindowsCpuSerialId();
		} else if (os.contains("linux")) {
			cpuid = getLinuxCpuSerialId();
		}
		return cpuid;
	}

	public static String getWindowsCpuSerialId() {

		try {
//			long start = System.currentTimeMillis();
			Process process = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" });
			process.getOutputStream().close();
			Scanner sc = new Scanner(process.getInputStream());
			String property = sc.next();
			String serial = sc.next();
//			System.out.println(property + ": " + serial);
//			System.out.println("time:" + (System.currentTimeMillis() - start));
			return serial;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static String getLinuxCpuSerialId() {
		String linux_cmd = "dmidecode -t processor |grep ID";
		try {
			long start = System.currentTimeMillis();
			Process process = Runtime.getRuntime().exec(linux_cmd);
			process.getOutputStream().close();
			Scanner sc = new Scanner(process.getInputStream());
			String property = sc.nextLine();// " ID: F2 06 03 00 FF FB 8B 0F"
			String serial = property.substring(property.indexOf(':'), -1).trim();
			System.out.println("ProcessorId" + ": " + serial);
			System.out.println("time:" + (System.currentTimeMillis() - start));
			return serial;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static String getHdSerialInfo() {
		String line = "";
		String HdSerial = "";// 定义变量硬盘序列号
		try {
			Process proces = Runtime.getRuntime().exec("cmd /c \"dir c:\"");// 获取命令行参数
			// proces.getOutputStream().close();
			// Scanner sc = new Scanner(proces.getInputStream());
			// while (sc.hasNext()) {
			// line = sc.nextLine();
			// System.out.println(new
			// String(line.getBytes(Charset.forName("GBK")),"utf-8"));
			// if (line.indexOf("卷的序列号是 ") != -1) { // 读取参数并获取硬盘序列号
			// HdSerial = line.substring(line.indexOf("卷的序列号是") +
			// "卷的序列号是".length(), line.length());
			// break;
			// // System.out.println(HdSerial);
			// }
			// }
			// System.out.println(Charset.forName("GBK"));
			BufferedReader buffreader = new BufferedReader(
					new InputStreamReader(proces.getInputStream(), Charset.forName("GBK")));
			while ((line = buffreader.readLine()) != null) {
				System.out.println(line);
				// System.out.println(new
				// String(line.getBytes(Charset.forName("GBK")),"utf-8"));
				if (line.indexOf("卷的序列号是 ") != -1) { // 读取参数并获取硬盘序列号
					HdSerial = line.substring(line.indexOf("卷的序列号是") + "卷的序列号是".length(), line.length()).trim();
					System.out.println(HdSerial);
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return HdSerial;// 返回硬盘序列号卷的序列 非物理
	}
}
