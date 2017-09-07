package com.zhw.web.webSocketServer;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.zhw.core.util.JsonUtil;
import com.zhw.core.util.MyStringUtil;
import com.zhw.web.model.User;
import com.zhw.web.webSocketServer.msg.BaseMsg;
import com.zhw.web.webSocketServer.msg.MsgCnts;
 
@ServerEndpoint(value = "/websocket",configurator=GetHttpSessionConfigurator.class)
public class WebSocketAction{
	private final static Logger log = Logger.getLogger(WebSocketAction.class);
	
	private static Set<String> onlineHttpUser =  new CopyOnWriteArraySet<String>();
    private static final Set<WebSocketAction> onlineWebSocketUsers = new CopyOnWriteArraySet<WebSocketAction>();
    private String userid;
    private Session session;
    private HttpSession httpSession;
    
    
	public WebSocketAction(){
//		System.out.println("websocket ChatAction");
	}
   
    // http 在线用户
    public static Set<String> getOnlineUser() {
		return onlineHttpUser;
	}

	public static void setOnlineUser(Set<String> onlineUser) {
		WebSocketAction.onlineHttpUser = onlineUser;
	}

	//接收websocket
	@OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        // 获取httpsession
        if(session != null && session.getUserProperties() != null){
        	String sname = HttpSession.class.getName();
        	this.httpSession = (HttpSession) (session.getUserProperties().get(sname));
        	//用户登陆后 userInfo 存有信息
            if(httpSession!=null){
    	        User uuu =(User) httpSession.getAttribute("userInfo");
    	        if(uuu != null){
    		        this.userid = uuu.getUsername();        		        
    		        String info = String.format("*** %s %s", userid, " from websocket 上线了...");
    		        System.out.println(info);
    		        
    	            // 添加到在线列表
    	            onlineWebSocketUsers.add(this);
    	        }
            }
        }
    }
 
	//关闭websocket
    @OnClose
    public void onClose(Session session) {
        onlineWebSocketUsers.remove(this);
        String message = String.format("*** %s %s", userid, " from websocket 已经离开...");
//        broadcast(message);
    }
 
    //接收客户端发来的信息
    @OnMessage
    public void onMessage(Session session,String message, EndpointConfig config) {
        // Never trust the client
    	System.out.println("收到消息:"+message+"");
        BaseMsg recv = JSON.parseObject(message,BaseMsg.class);
        if(recv!=null){
        	//开始,添加用户列表
        	if(recv.getMsgType().equalsIgnoreCase(MsgCnts.EVENT_TYPE_START)){
//        		if(recv.getContent() != null)
//        		this.userid = recv.getContent().getString("userName");
//                this.session = session;                
//		        if(session != null && session.getUserProperties() != null){
//		        	String sname = HttpSession.class.getName();
//		        	this.httpSession = (HttpSession) (session.getUserProperties().get(sname));
//		        }
//                if(httpSession!=null){
//        	        User uuu =(User) httpSession.getAttribute("userInfo");
//        	        if(uuu != null){
//        		        this.userid = uuu.getUsername();        		        
//        		        String info = String.format("* %s %s", userid, " from websocket 上线了...");
//        		        System.out.println(info);
//        	        }
//                }                
//                // 添加到在线列表  移动到开始的时候添加
//                onlineWebSocketUsers.add(this);
                
                //测试
                com.alibaba.fastjson.JSONObject content = new com.alibaba.fastjson.JSONObject();
                content.put("orderid", "51e656f5298f4484ac4c88ecf5f019ad");
                BaseMsg send = new BaseMsg("zhangsan","role",System.currentTimeMillis(),MsgCnts.RESP_MESSAGE_TYPE_ORDER,"",content);
                SendMsg2User("zhangsan", JsonUtil.toJSONDef(send));
        	}
        }
    }
 
    //错误
    @OnError
    public void onError(Throwable t) throws Throwable {
        log.error("错误: " + t.toString(), t);
    }
 
    //广播消息
    public static void broadcast(String msg) {
        for (WebSocketAction client : onlineWebSocketUsers) {
            try {
                synchronized (client) {
                    client.session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                log.debug("错误: 消息发送失败!", e);
                onlineWebSocketUsers.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                    // Ignore
                }
//                String message = String.format("* %s %s", client.userid, " from websocket 已经离开...");
//                broadcast(message);
            }
        }
    }
    
    //广播消息
    public static void SendMsg2User(String username,String msg) {
    	if(MyStringUtil.isEmpty(username) || MyStringUtil.isEmpty(msg)){
    		return;
    	}
        for (WebSocketAction client : onlineWebSocketUsers) {
        	//查找用户
        	if(!client.userid.equals(username)){
        		continue;
        	}
        	//发送消息
            try {
                synchronized (client) {
                    client.session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                log.debug("错误: 消息发送失败!", e);
                onlineWebSocketUsers.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                //String message = String.format("* %s %s", client.userid, " from websocket 已经离开...");
                //暂时不通知其他用户
                //broadcast(message);
            }
        }
    }
    
    
    //httpsession  超时 结束websocket连接 时用
    public static void colseWebsocket(String username,HttpSession httpsession) {
        for (WebSocketAction client : onlineWebSocketUsers) {
        	//查找用户 ,一个用户有一个httpSession，同事对应有多个websocket连接
        	if(client.userid.equals(username) && client.httpSession.equals(httpsession)){
        		//从在线列表移除，并关闭websocket
        		onlineWebSocketUsers.remove(client);
        		try {
					client.session.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
         }
    }
}