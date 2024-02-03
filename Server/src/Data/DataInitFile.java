
package Data;

/**
 *
 * @author zekican
 */
public class DataInitFile {

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
    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return code;
    }
    public void setTaskNumber(int taskNumber){
        this.taskNumber = taskNumber;
    }
    public int getTaskNumber(){
        return taskNumber;
    }
    public String getParam(){
        return param;
    }
    public void setParam(String param){
        this.param = param;
    }
    public void setinfinite(int infinite){
        this.infinite = infinite;
        
    }
    public int getinfinite(){
        return infinite;
    }
    public String getclientName(){
        return this.clientName;
    }
    public void setclientName(String clientName){
        this.clientName = clientName;
    }
    public DataInitFile(String fileName, long fileSize, int code, String clientName){
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.code = code;
        this.clientName = clientName;
    }

    public DataInitFile() {
    }
     
    private String fileName;
    private long fileSize;
    private String clientName;
    private int code;
    private int taskNumber;
    private String param;
    private int infinite;
}
