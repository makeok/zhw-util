package com.zhw.core.log.system;

//import sun.management.ManagementFactory;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
public class SystemOutLog {

	static {
		Log4jPrintStream.redirectSystemOut();

		// get name representing the running Java virtual machine.
		RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
		String name = rb.getName();
		System.out.println(rb.toString());
		System.out.println(name);
		System.out.println(rb.getUptime());
		System.out.println(rb.getClassPath());
		System.out.println(rb.getBootClassPath());
		System.out.println(rb.getInputArguments());
		System.out.println(rb.getLibraryPath());
		System.out.println(rb.getManagementSpecVersion());
		System.out.println(rb.getSpecName());
		System.out.println(rb.getSpecVendor());
		System.out.println(rb.getSpecVersion());
		System.out.println(rb.getVmName());
		System.out.println(rb.getVmVendor());
		System.out.println(rb.getVmVersion());
		System.out.println(rb.getObjectName());
		System.out.println(rb.getSystemProperties().toString());
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.print("abc");
			System.out.print(i);
			System.out.print((char) (i + 0 % 21));
		}
	}
}
