/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goback;

import java.io.ObjectInputStream;

/**
 *
 * @author Aakash
 */
class GoBackNListener implements Runnable 
{ 
    Thread t; 
    ObjectInputStream ois; 
    int reply,x; 
    
    GoBackNListener(ObjectInputStream o,int i) 
    { 
        t=new Thread(this); 
        ois=o; 
        reply=-2; 
        x=i; 
    } 
    @Override 
    public void run() { 
        System.out.print("run");
        try 
        { 
            int temp=0; 
            while(reply!=-1) 
            { 
                reply=(Integer)ois.readObject(); 
                if(reply!=-1 && reply!=temp+1) 
                    reply=temp; 
                if(reply!=-1) 
                { 
                    temp=reply; 
                    System.out.println("Acknowledgement of frame no " + (reply%x) + " recieved."); 
                    System.out.println(); 
                } 
            } 
            reply=temp; 
        } 
        catch(Exception e) 
        { 
            System.out.println("Exception => " + e); 
        } 
    } 
}
