

package Data;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import io.socket.client.Ack;
import io.socket.client.Socket;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javax.swing.JTable;
import okhttp3.internal.http.UnrepeatableRequestBody;
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
    public DataReader(File file, JTable table) throws IOException{
        accFile = new RandomAccessFile(file, "r");
        this.file = file;
        this.fileSize = accFile.length();
        this.fileName = file.getName();
        this.status = new PanelStatus();
        this.table = table;
    }
    private int fileID;
    private File file;
    private long fileSize;
    private String fileName;
    private RandomAccessFile accFile;
    private PanelStatus status;
    private JTable table;
    public synchronized byte[] readFile() throws IOException{
        long filePointer = accFile.getFilePointer();
        //System.out.println(filePointer + " " + fileName);
        if(filePointer != file.length()){
            int max = 20000000;
            
            long length = filePointer + max >= file.length() ? file.length() - filePointer : max;
            //System.out.println(length + "   " + file.length());
            byte[] data = new byte[(int) length];
            accFile.read(data);
            return data;
        }else {
            return null;
        }
        
    }
    
    public void close() throws IOException{
        accFile.close(); 
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
    
    //// kodu düzenleyebilirsin index col bulunan forla diğerini birleştirerek!!
    public void startSend(SocketIOServer socket, String key, int code, ArrayList<ArrayList<File>> taskList, ArrayList<Integer> taskCodes, ArrayList<File> files, ArrayList<String> parameter
        ,Map<String, String> totalTask, String param, int number) throws JSONException, IOException{
        DataInitFile data = new DataInitFile();
        data.setFileName(fileName);
        data.setFileSize(fileSize);
        int taskNumber = 0;
        int index = 0, col = 0;
        int check = 0;
       
        int[] sendCode = {0};

        index = number-1;
        System.out.println(taskList.get(index).get(0).getName() + "  " + taskList.get(index).get(1).getName() + fileName + " index:" + index);
        if(taskList.get(index).get(0).getName().equals(fileName)){
            code = 1;
            if(taskCodes.get(index) == 1){
                sendCode[0] = 1;
            }
        }else if(taskList.get(index).get(1).getName().equals(fileName)){
            code = 3; 
            sendCode[0] = 1; 
        }
        taskNumber = number;
        data.setParam(param);
        
                
        data.setCode(code);
        data.setTaskNumber(taskNumber);
        System.out.println("code:" + code + "  file:" + fileName + "   " + "sendcode:" + sendCode[0] + " tasknum:" + (index+1));

        socket.getClient(UUID.fromString(key)).sendEvent("server_file", data);
        //server.getClient(client.getClient().getSessionId()).sendEvent("deneme_send_file", filesend);
        
        try{
            Thread.sleep(100);
            sendingFile(socket,socket.getClient(UUID.fromString(key)),sendCode[0]);
        }catch(Exception e){
            System.out.println("client bağlantı gittiiiiiiiiiiiiiii");
            System.out.println("client bağlantı gittiiiiiiiiiiiiiii");
            System.out.println("client bağlantı gittiiiiiiiiiiiiiii");
        }
        
        
    }

    private void sendingFile(SocketIOServer socket, SocketIOClient client, int code) throws IOException, JSONException{
        //data.put("fileID", fileID);
        DataFileSending filesend = new DataFileSending();
        filesend.setFileName("sa");
        DataFileSending data = new DataFileSending();
        data.setFileID(fileID);
        data.setFileName(fileName);
        
        byte[] bytes = readFile();
        System.out.println(bytes);
        
        if(bytes != null){
            String base64Data = Base64.getEncoder().encodeToString(bytes);
            filesend.setData(bytes);
            filesend.setFinish(false);
            data.setFileName(fileName);
            filesend.setFileName(fileName);
            data.setLine(base64Data);
            socket.getClient(client.getSessionId()).sendEvent("sending", data);       
            sendingFile(socket,client,code);
        }else{
            filesend.setFinish(true);
            //server.getClient(sioc.getSessionId()).sendEvent("deneme_sending", filesend);
        }
        System.out.println("Sending");
        //server.getClient(sioc.getSessionId()).sendEvent("sending", data);
             
    }

    public void startSend2(SocketIOServer socket, String key, int code, ArrayList<ArrayList<File>> taskList, ArrayList<Integer> taskCodes, ArrayList<File> files, ArrayList<String> parameter
    ,String param, int number) throws JSONException{
        System.out.println("Start send içine geldi");
        
        DataInitFile data = new DataInitFile();
        data.setFileName(fileName);
        data.setFileSize(fileSize);
        int check = 0, taskNumber = 0;
        int index = 0, col = 0;

        int lastCode = code;
        System.out.println("ilk code:" + code + " " + lastCode);
        int[] sendCode = {0};

        index = number-1;
        if(taskList.get(index).size() > 1) System.out.println(taskList.get(index).get(0).getName() + "  " + taskList.get(index).get(1).getName() + fileName + " index:" + index);
        if(taskList.get(index).get(0).getName().equals(fileName)){
            code = 1;
            if(taskCodes.get(index) == 1){
                sendCode[0] = 1;
            }
        }else if(taskList.get(index).get(1).getName().equals(fileName) && taskList.get(index).size() == 2){
            code = 3; 
            sendCode[0] = 1;
            if(!fileName.contains(".")){
                code = 2;               
            }
        }else if(taskList.get(index).size() == 3){
            if(taskList.get(index).get(2).getName().equals(fileName)){
                code = 2;
                sendCode[0] = 1; 
            }else if(taskList.get(index).get(1).getName().equals(fileName)){
                code = 3;
            }
        }
        taskNumber = number;
        
        data.setTaskNumber(taskNumber);
        data.setParam(param);
                
        data.setCode(code);
        sendCode[0] = lastCode;
        System.out.println("code:" + code + "  file:" + fileName + "   " + "sendcode:" + sendCode[0] + " lastcode:" + lastCode + " tasknum:" + (index+1));
        
        socket.getClient(UUID.fromString(key)).sendEvent("server_file", data);
        
        try{
            Thread.sleep(100);
            sendingFile2(socket,socket.getClient(UUID.fromString(key)),sendCode[0]);
        }catch(Exception e){
            System.out.println("client bağlantı gittiiiiiiiiiiiiiii");
            System.out.println("client bağlantı gittiiiiiiiiiiiiiii");
            System.out.println("client bağlantı gittiiiiiiiiiiiiiii");
        }
             
    }
    private void sendingFile2(SocketIOServer socket, SocketIOClient client, int code) throws IOException, JSONException{
        DataFileSending data = new DataFileSending();
        data.setFileID(fileID);
        data.setFileName(fileName);
        byte[] bytes = readFile();
        
        if(bytes != null){
            String base64Data = Base64.getEncoder().encodeToString(bytes);
            data.setFileName(fileName);
            data.setLine(base64Data);
            socket.getClient(client.getSessionId()).sendEvent("sending", data);       
            sendingFile(socket,client,code);
        }else{
            //server.getClient(sioc.getSessionId()).sendEvent("deneme_sending", filesend);
        }
            
            //eski halinde broadcast ile
               
        //data.setCode(code);
        System.out.println(fileName + " " + code);
        if(code == 1){
            socket.getClient(client.getSessionId()).sendEvent("send_finish_execute", data);   
        }
               
    }
    
}
