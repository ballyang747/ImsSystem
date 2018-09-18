package org.fkjava.ims.common;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class LoginListenerTest {
	private static HashMap mymap = new HashMap();

    public static synchronized void AddSession(HttpSession session) {
       
            mymap.put(session.getId(), session);

    }

    public static synchronized void DelSession(HttpSession session) {
      
            mymap.remove(session.getId());
        
    }

    public static synchronized HashMap getSession() {
    
        return mymap;
    }

}
