/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

/**
 *
 * @author zekican
 */
public class DataFileSending {

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
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * @return the finish
     */
    public boolean isFinish() {
        return finish;
    }

    /**
     * @param finish the finish to set
     */
    public void setFinish(boolean finish) {
        this.finish = finish;
    }
    public void setLine(String line){
        this.line = line;
    }
    public void setFileName(String fileName){
        this.fileName = fileName;
    }
    public String getFileName(){
        return this.fileName;
    }
    public String getLine(){
        return this.line;
    }
    public int getCode(){
        return this.code;
    }
    public void setCode(int code){
        this.code = code;
    }
    public void setTaskNumber(int taskNumber){
        this.taskNumber = taskNumber;
    }
    public int getTask(){
        return this.taskNumber;
    }
    public String getclientName(){
        return this.clientName;
    }
    public void setclientName(String clientName){
        this.clientName = clientName;
    }
    public DataFileSending(int fileID, byte[] data, boolean finish, int code, int taskNumber, String clientName){
        this.fileID = fileID;
        this.data = data;
        this.finish = finish;
        this.code = code;
        this.taskNumber = taskNumber;
        this.clientName = clientName;
    }

    public DataFileSending() {
    }
    private String fileName;
    private String clientName;
    private String line;
    private int fileID;
    private byte[] data;
    private boolean finish;
    private int code;
    private int taskNumber;
    
    
}
