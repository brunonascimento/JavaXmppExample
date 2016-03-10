/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chatsimple;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.MessageListener;


/**
 *
 * @author Bruno
 */
public class SimpleChat {

public static void main(String[] args) 
{
    ConnectionConfiguration config = new ConnectionConfiguration(
            "localhost", 5222, "produban.com.br");
    XMPPConnection connection = new XMPPConnection(config);
    Presence presence;
    String status;

    try {
        connection.connect();
        connection.login("bruno", "12345");
        status = "DND";

        presence = new Presence(Presence.Type.available, status, 24,
                Presence.Mode.available);
        
        Chat chat = connection.getChatManager().createChat("fred@produban.com", new MessageListener() {

             public void processMessage(Chat chat, Message message) {
                 // Print out any messages we get back to standard out.
                 System.out.println("Received message: " + message);
             }
         });
         chat.sendMessage("Ola Fred");
         
         while (true) {
            status = set(status);
            presence.setStatus(status);
            connection.sendPacket(presence);
            Thread.sleep(1000);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        connection.disconnect();
    }
}

private static String set(String input) {
    return input.substring(1) + input.charAt(0);
}
    
}
