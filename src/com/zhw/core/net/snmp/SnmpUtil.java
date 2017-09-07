package com.zhw.core.net.snmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Session;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import com.alibaba.fastjson.JSONArray;

public class SnmpUtil {

	private Snmp snmp = null;

	private Address targetAddress = null;
	private String community = "public";

	public void initComm(String agentIp, String community) throws IOException {
		// 设置Agent方要查询的Oid
		if(community!=null){
			this.community = community;			
		}
		// 设置Agent方的IP和端口
		targetAddress = GenericAddress.parse("udp:" + agentIp + "/161");
		TransportMapping transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		transport.listen();
	}
	public Vector<VariableBinding> sendPDU(int type,String[] oids, int MaxRepetitions) throws IOException {
		// 设置 target
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(targetAddress);
		// 通信不成功时的重试次数
		target.setRetries(2);
		// 超时时间
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
		// 创建 PDU
		PDU pdu = new PDU();
		pdu.setType(type);
		pdu.setMaxRepetitions(MaxRepetitions); // must set it, default is 0
		pdu.setNonRepeaters(0);
		// pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1"))); //system
		for (String one : oids) {
			pdu.add(new VariableBinding(new OID(one)));
		}

		ResponseEvent respEvnt = snmp.send(pdu, target);
		// 解析Response
		if (respEvnt != null && respEvnt.getResponse() != null) {
			Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
			for (int i = 0; i < recVBs.size(); i++) {
				VariableBinding recVB = recVBs.elementAt(i);
				System.out.println(recVB.getOid() + " : " + recVB.getVariable());
			}
			return recVBs;

		}
		return null;
	}
	public Vector<VariableBinding> sendPDUByGet(String[] oids) throws IOException {
		// 设置 target
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(targetAddress);
		// 通信不成功时的重试次数
		target.setRetries(2);
		// 超时时间
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
		// 创建 PDU
		PDU pdu = new PDU();
		// pdu.add(new VariableBinding(new OID(new int[] { 1, 3, 6,1, 2, 1, 1,
		// 5, 0 })));
		// MIB的访问方式
		pdu.setType(PDU.GET);
		for (String one : oids) {
			pdu.add(new VariableBinding(new OID(one)));
		}

		ResponseEvent respEvnt = snmp.send(pdu, target);
		// 解析Response
		Vector<VariableBinding> recVBs = null;
		if (respEvnt != null && respEvnt.getResponse() != null) {
			recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
			for (int i = 0; i < recVBs.size(); i++) {
				VariableBinding recVB = recVBs.elementAt(i);
				System.out.println(recVB.getOid() + ":"+recVB.getSyntax()+":" + recVB.getVariable());
			}

		}
		return recVBs;
	}

	public Vector<VariableBinding> sendPDUbyGetNext(String[] oids) throws IOException {
		// 设置 target
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(targetAddress);
		// 通信不成功时的重试次数
		target.setRetries(2);
		// 超时时间
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
		// 创建 PDU
		PDU pdu = new PDU();
		pdu.setType(PDU.GETNEXT);
		// pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.2.3.1.3")));
		// //sysUpTime
		for (String one : oids) {
			pdu.add(new VariableBinding(new OID(one)));
		}

		ResponseEvent respEvnt = snmp.send(pdu, target);
		// 解析Response
		Vector<VariableBinding> recVBs = null;
		if (respEvnt != null && respEvnt.getResponse() != null) {
			recVBs = (Vector<VariableBinding>) (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
			for (int i = 0; i < recVBs.size(); i++) {
				VariableBinding recVB = recVBs.elementAt(i);
				System.out.println(recVB.getOid() + " : " + recVB.getVariable());
			}
		}
		return recVBs;
	}
	//	
	public Vector<VariableBinding> sendPDUbyGetBulk(String[] oids, int MaxRepetitions) throws IOException {
		// 设置 target
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(targetAddress);
		// 通信不成功时的重试次数
		target.setRetries(2);
		// 超时时间
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
		// 创建 PDU
		PDU pdu = new PDU();
		pdu.setType(PDU.GETBULK);
		pdu.setMaxRepetitions(MaxRepetitions); // must set it, default is 0
		pdu.setNonRepeaters(0);
		// pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1"))); //system
		for (String one : oids) {
			pdu.add(new VariableBinding(new OID(one)));
		}

		ResponseEvent respEvnt = snmp.send(pdu, target);
		// 解析Response
		if (respEvnt != null && respEvnt.getResponse() != null) {
			Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
			for (int i = 0; i < recVBs.size(); i++) {
				VariableBinding recVB = recVBs.elementAt(i);
				System.out.println(recVB.getOid() + " : " + recVB.getVariable());
			}
			return recVBs;

		}
		return null;
	}

	public List<TableEvent> sendPDUbyWalk(String[] oids, int MaxRepetitions) throws IOException {
		// 设置 target
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setAddress(targetAddress);
		// 通信不成功时的重试次数
		target.setRetries(2);
		// 超时时间
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);

		TableUtils utils = new TableUtils(snmp, new DefaultPDUFactory(PDU.GETBULK));// GETNEXT
		utils.setMaxNumRowsPerPDU(MaxRepetitions);//only for GETBULK, set max-repetitions, default is 10 															// or
																					// GETBULK
		OID[] columnOids = new OID[oids.length];
		for (int i=0;i<oids.length;i++) {
			columnOids[i] = new OID(oids[i]);
		}
		// If not null, all returned rows have an index in a range
		// (lowerBoundIndex, upperBoundIndex]
		List<TableEvent> l = utils.getTable(target, columnOids, new OID("0"), new OID("10"));
		for (TableEvent e : l) {
			System.out.println(e);
		}
		return l;
	}

	public static void main(String[] args) {
		try {
			SnmpUtil util = new SnmpUtil();
			List<int[]> oids = new ArrayList<int[]>();
			// oids.add(new int[] { 1, 3, 6,1, 2, 1, 1, 5, 0 });
			// oids.add(new int[] {1,3,6,1,2,1,25,2,2,0 }); //内存大小
			// oids.add(new int[] {1,3,6,1,2,1,1,1,0});//硬件信息
			oids.add(new int[] { 1, 3, 6, 1, 2, 1, 25, 2, 3, 1, 5, 1 }); // C盘的族数
			oids.add(new int[] { 1, 3, 6, 1, 2, 1, 25, 2, 3, 1, 4, 1 }); // C盘的一个族的大小，单位为B，字节
			oids.add(new int[] { 1, 3, 6, 1, 2, 1, 25, 2, 3, 1, 3, 1 }); // C盘的名称
			oids.add(new int[] { 1, 3, 6, 1, 2, 1, 25, 2, 3, 1, 6, 1 }); // C盘已使用的族数
			// oids.add(new int[] { 1,3,6,1,2,1,25,3,3,1,2});
			// util.initComm("127.0.0.1",oids);
			util.initComm("172.24.2.55",null);
			// util.initComm("10.194.111.28",oids);
			// util.initComm("10.194.55.86",oids);

			// util.sendPDU();
			// util.sendPDUbyGetNext();
			// util.sendPDUbyGetBulk();
//			util.sendPDUbyWalk();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
