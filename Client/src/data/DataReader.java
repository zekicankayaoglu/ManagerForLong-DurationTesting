

package data;

import io.socket.client.Ack;
import io.socket.client.Socket;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.Date;
import javax.swing.JTable;
import org.json.JSONException;
import org.json.JSONObject;
import swing.PanelStatus;

/**
 *
 * @author zekican
 */
public class DataReader {

    /**
     * @return the status
     */
    public PanelStatus getStatus() {
        return status;
    }

    /**
     * @return the fileID
     */
    public int getFileID() {
        return fileID;
    }

    /**
     * @param fileID the fileID to set
     */
    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return the fileSize
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the accFile
     */
    public RandomAccessFile getAccFile() {
        return accFile;
    }

    /**
     * @param accFile the accFile to set
     */
    public void setAccFile(RandomAccessFile accFile) {
        this.accFile = accFile;
    }
    public DataReader(File file, JTable table, int last, String clientName) throws IOException{
        accFile = new RandomAccessFile(file, "r");
        this.file = file;
        this.fileSize = accFile.length();
        this.fileName = file.getName();
        this.status = new PanelStatus();
        this.table = table;
        this.last = last;
        this.clientName = clientName;
    }
    private int fileID;
    private File file;
    private long fileSize;
    private String fileName;
    private RandomAccessFile accFile;
    private PanelStatus status;
    private JTable table;
    private int last;
    private String clientName;
    public synchronized byte[] readFile() throws IOException{
        long filePointer = accFile.getFilePointer();
        if(filePointer != fileSize){
            int max = 49000;
            
            long length = filePointer + max >= fileSize ? fileSize - filePointer : max;
            System.out.println(length + "   " + fileSize + " l:" + file.length());
            byte[] data = new byte[(int) length];
            System.out.println("okudu "+ data);
            accFile.read(data);
            return data;
        }else {
            return null;
        }
    }
    
    public void close() throws IOException{
        accFile.close(); 
    }
    public void delete() throws IOException{
        System.out.println("delete geldi:" + file.getPath());
        File fileDelete = new File(file.getPath());
        fileDelete.delete();
    }
    public String getFileSizeConverted(){
        double bytes = fileSize;
        String[] fileSizeUnits = {"bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
        String sizeToReturn;
        DecimalFormat df = new DecimalFormat("0.#");
        int index;
        for (index = 0; index < fileSizeUnits.length; index++) {
            if (bytes < 1024) {
                break;
            }
            bytes = bytes / 1024;
        }
        sizeToReturn = df.format(bytes) + " " + fileSizeUnits[index];
        return sizeToReturn;
    }
    
    public double getPercentage() throws IOException{
        double percentage;
        long filePointer = accFile.getFilePointer();
        percentage = filePointer * 100 / fileSize;
        return percentage;
    }
    
    public Object[] toRowTable(int no){
        return new Object[]{this,no,fileName,getFileSizeConverted(),"Next update"};
    }
    public void startSend(Socket socket, int taskNumber, int flag, int infinite) throws JSONException{
        JSONObject data = new JSONObject();
        data.put("fileName",fileName);
        data.put("fileSize", fileSize);
        data.put("taskNumber", taskNumber);
        data.put("infinite", infinite);
        data.put("clientName", clientName);
        System.out.println("dosya : " + fileName + " isInfinite: " + infinite + " flag:" + flag);
        socket.emit("send_file", data, new Ack(){
            @Override
            public void call(Object... os){
                if(os.length > 0){
                    boolean action = (boolean) os[0];
                    if(action){
                        
                        fileID = (int) os[1];
                        //start sending file
                        try{
                            sendingFile(socket,flag,taskNumber);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    private void sendingFile(Socket socket,int flag,int taskNumber) throws IOException, JSONException{
        //DataFileSending data = new DataFileSending();
        JSONObject data = new JSONObject();
        data.put("fileID", fileID);
        data.put("clientName", clientName);
        //data.setFileID(fileID);
        //data.setclientName(clientName);
        byte[] bytes = readFile();
        //System.out.println(bytes.length + ":lenght");
        if(bytes != null){
            String base64Data = Base64.getEncoder().encodeToString(bytes);
            //data.setData(bytes);
            //data.setLine(base64Data);
            //data.setFinish(false);
            //data.setTaskNumber(taskNumber);
            //data.put("data", bytes);
            data.put("line" , base64Data);
            data.put("finish",false);
            data.put("taskNumber", taskNumber);
        }else{
            //data.setFinish(true);
            //data.setTaskNumber(taskNumber);
            data.put("finish",true);
            data.put("taskNumber", taskNumber);
            close(); // close file
            delete();
             
            Date date = new Date();                
            System.out.println("dosya silindi:" + fileName + " " + date.toString());
        }
        
        System.out.println("Sending  last:" + last);
        
        socket.emit("sending", data,new Ack() {
            @Override
            public void call(Object... os) {
                if(os.length > 0){
                    boolean act = (boolean) os[0];
                    if(act){
                        try{
                            //System.out.println("sending içinde" + socket.connected());
                            showStatus((int) getPercentage());
                            sendingFile(socket,flag,taskNumber);
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        if(last == 1){
                            try{
                                Thread.sleep(100);
                                System.out.println("sen new içine girdi");
                                socket.emit("send_new_task","");
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        
      
    }
    public void showStatus(int values){
        status.showStatus(values);
        table.repaint();
    }
}
