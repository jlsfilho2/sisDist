package sis_dist;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import akka.testkit.javadsl.TestKit;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

class socketTest {

	 private static ActorSystem actorSystem;

	    @BeforeClass
	    public static void setUp() {
	        actorSystem = ActorSystem.create("web_socket_test_automation_system");
	    }

	    @AfterClass
	    public static void tearDown() {
	        actorSystem.terminate();
	        actorSystem = null;
	    }

	    @Test
	    public void echoTest() {
	        new TestKit(actorSystem) {
	            {
	                final ActorRef clientReference = 
	                    actorSystem.actorOf( Props.create(WebSocketEndPoint.class) );
	                final String msg = "Hello Server";
	                // Stimulation: Send echo to server's web socket
	                clientReference.tell(msg, getRef() );
	                // Tests {
	                // The expected message should be equal to msg
	                expectMsg(Duration.ofSeconds(2), msg);
	                // No more messages are expected.
	                expectNoMessage();
	                // }
	            }
	        };
	    }

}
