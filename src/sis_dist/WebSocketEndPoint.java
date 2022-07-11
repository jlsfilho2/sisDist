package sis_dist;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

//https://github.com/DawidLokiec/web_socket_test_automation_framework/blob/master/src/main/java/com/example/end2end/WebSocketClientEndpoint.java

@ServerEndpoint("/teste")
public class WebSocketEndPoint {
	
	
	@Inject
	private DialogosService dialogoService;
	
	private Session session;
	
	private boolean boasVindas= false;
	
	private String userId;

	private static ConcurrentHashMap<String, WebSocketEndPoint> webSocketMap = new ConcurrentHashMap<>();
	
	
	
	
	 @OnMessage
	 public void echoMessage(String message, Session session) {
	   
	     try {
	    	 if(!boasVindas) {
			session.getBasicRemote().sendText(dialogoService.geraBoasVindas() + session + " !");
			boasVindas = false;
	    	 } else 
	    		 if(message.isEmpty()) {
	    			 session.getBasicRemote().sendText(dialogoService.geraPergunta());
	    	 } else {
	    		 session.getBasicRemote().sendText(dialogoService.geraResposta(message));
	    	 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	 
	 @OnOpen
	 public void abrir(Session session, @PathParam("userId") String userId) {
		 this.session = session;
		    this.userId = userId;
		    if (webSocketMap.containsKey(userId)) {
		        webSocketMap.remove(userId);
		        webSocketMap.put(userId, this);
		    }
	 }

	 @OnClose
	 public void fechar(CloseReason c) {
		 if (webSocketMap.containsKey(userId)) {
	            webSocketMap.remove(userId);
		 }
	 }

	 @OnError
	 public void erro(Throwable error) {
		 error.printStackTrace();
	 }

}
