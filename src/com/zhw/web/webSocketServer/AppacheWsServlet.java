package com.zhw.web.webSocketServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;

//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;


//@WebServlet(urlPatterns = { "/websocket"})  
//如果要接收浏览器的ws://协议的请求就必须实现WebSocketServlet这个类  
public class AppacheWsServlet {// extends WebSocketServlet

    private static final long serialVersionUID = -4853540828121130946L;
//    private static ArrayList<MyMessageInbound> mmiList = new ArrayList<MyMessageInbound>();

//    private class MyMessageInbound extends MessageInbound {
//        WsOutbound myoutbound;
//
//        @Override
//        public void onOpen(WsOutbound outbound) {
//            try {
//                System.out.println("Open Client.");
//                this.myoutbound = outbound;
//                mmiList.add(this);
//                outbound.writeTextMessage(CharBuffer.wrap("Hello!"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onClose(int status) {
//            System.out.println("Close Client.");
//            mmiList.remove(this);
//        }
//
//        @Override
//        public void onTextMessage(CharBuffer cb) throws IOException {
//            System.out.println("Accept Message : " + cb);
//            for (MyMessageInbound mmib : mmiList) {
//                CharBuffer buffer = CharBuffer.wrap(cb);
//                mmib.myoutbound.writeTextMessage(buffer);
//                mmib.myoutbound.flush();
//            }
//        }
//
//        @Override
//        public void onBinaryMessage(ByteBuffer bb) throws IOException {
//        }
//
//		@Override
//		public int getReadTimeout() {
//
//			return 0;
//		}
//    }
//
//	@Override
//	protected StreamInbound createWebSocketInbound(String arg0) {
//		 return new MyMessageInbound();
//	}
//	


}