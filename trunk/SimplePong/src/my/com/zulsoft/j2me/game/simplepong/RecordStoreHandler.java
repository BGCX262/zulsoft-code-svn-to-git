/*
 * RecordStoreHandler.java
 *
 * Created on January 28, 2006, 10:56 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package my.com.zulsoft.j2me.game.simplepong;

import java.io.*;
import javax.microedition.rms.*;

/**
 *
 * @author Administrator
 */
public class RecordStoreHandler {
    
    protected RecordStore rsBestScore;
    protected static int MAXSCORE = 5;    
    
    /** Creates a new instance of RecordStoreHandler */
    public RecordStoreHandler() throws  RecordStoreException {
        
        rsBestScore = RecordStore.openRecordStore("pong", true);
        //fill rsBestScore with dummy value
        if(rsBestScore.getNumRecords()==0) {
            int currentValue = 100;
            for(int i=0; i < MAXSCORE; i++) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                try {
                    dos.writeInt(currentValue);
                    dos.flush();
                    byte[] data = baos.toByteArray();
                    rsBestScore.addRecord(data,0, data.length);
                    dos.close();
                    baos.close();
                } catch(IOException e) {}
                catch(RecordStoreException rse) {}
                currentValue = currentValue - 10;
            }
        }
    }
    
    public int[] getAllScore() {
        int[] topScore = new int[MAXSCORE];
        try {
            RecordEnumeration re = rsBestScore.enumerateRecords(null, null,true);
            int counter = 0;
            while(re.hasNextElement()) {
                byte[] data = re.nextRecord();
                ByteArrayInputStream bais = new ByteArrayInputStream(data);
                DataInputStream dis = new DataInputStream(bais);
                try {
                     topScore[counter] = dis.readInt();
                     dis.close();
                     bais.close();
                } catch(IOException e) { }
                counter+=1;
                if (counter > (MAXSCORE - 1)) return null;
            }
        } catch (RecordStoreException rse) { }
        
        return topScore;
    }
    
    public void setBestScore(int value) {
        
    }
    
    public void close() throws RecordStoreNotOpenException, RecordStoreException {
        if (rsBestScore !=null) {
            rsBestScore.closeRecordStore(); 
            rsBestScore = null;
        }
    }
}
