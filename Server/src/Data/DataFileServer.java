
package Data;

import java.io.File;


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
    public DataFileServer(int fileID, String fileName, String fileSize, File outPutPath){
        this.fileID = fileID;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.outPutPath = outPutPath;
    }
   
    public DataFileServer(){
        
    }
    private int fileID;
    private String fileName;
    private String fileSize;
    private File outPutPath;
    
}
