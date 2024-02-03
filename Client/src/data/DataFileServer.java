
package data;

import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author zekican
 */
public class DataFileServer {

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
    public String getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the outPutPath
     */
    public File getOutPutPath() {
        return outPutPath;
    }

    /**
     * @param outPutPath the outPutPath to set
     */
    public void setOutPutPath(File outPutPath) {
        this.outPutPath = outPutPath;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    public DataFileServer(int fileID, String fileName, String fileSize, String outPutPath, boolean status){
        this.fileID = fileID;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.outPutPath = this.outPutPath;
        this.status = status;
    }
    public DataFileServer(JSONObject json)throws JSONException{
        fileID = json.getInt("fileID");
        fileName = json.getString("fileName");
        fileSize = json.getString("fileSize");
    }
    private int fileID;
    private String fileName;
    private String fileSize;
    private File outPutPath;
    private boolean status;
    
    public Object[] toTableRow(int row) {
        return new Object[]{this, row, fileName, fileSize, "Next Update"};
    }
}
