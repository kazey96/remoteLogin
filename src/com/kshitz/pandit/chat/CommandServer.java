/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kshitz.pandit.chat;

import java.io.IOException;
import java.net.Socket;
import org.mads.iotapipub.rmi.exception.RMIException;
import org.mads.iotapipub.rmi.handler.CallHandler;
import org.mads.iotapipub.rmi.handler.CallLookup;
import org.mads.iotapipub.rmi.network.IServerListener;
import org.mads.iotapipub.rmi.network.RMIServer;

/**
 *
 * @author xetiz
 */
public class CommandServer implements ICommandServer, IServerListener{
    public boolean authenticated=false;
    public static final int port=34578;
    
    public static void main(String[] args) throws IOException, RMIException {
        CommandServer commandServer=new CommandServer();
        CallHandler callHandler=new CallHandler();
        callHandler.registerGlobal(ICommandServer.class, commandServer);
        RMIServer rMIServer=new RMIServer();
        rMIServer.addServerListener(commandServer);
        rMIServer.bind(port, callHandler);
    }

    @Override
    public void authenticate(String sessionCode) {
        //code to authenticate
        
        authenticated=true;
    }

    @Override
    public void shutdown() {
        if(!checkAuthentication()){
            return;
        }
        
        System.out.println("Running shutdown");
        //code to shutdown
        
    }

    @Override
    public void restart() {
        if(!checkAuthentication()){
            return;
        }
        //code to restart
    }

    private boolean checkAuthentication() {
        if(!authenticated){
            Socket socket=CallLookup.getCurrentSocket();
            try{socket.close();}catch (Exception e){}
        }
        return authenticated;
    }

    public boolean one_client=false;
    
    @Override
    public void onClientConnected(Socket socket) {
        //code when client is connected
        System.out.println("Client connected");
        if(one_client){
            try{
            socket.close();
            }catch(Exception e){}
        }
        one_client=true;
    }

    @Override
    public void onClientDisconnected(Socket socket) {
        //code when client is disconnected
        System.out.println("Client disconnected");
    }
}
