
package Data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.Base64;

/**
 *
 * @author zekican
 */
public class DataWriter {

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
    
    public DataWriter(File file, long fileSize) throws IOException{
        fos = new FileOutputStream(file.getPath(),true);
        this.file = file;
        this.fileSize = fileSize;
    }
    private File file;
    private long fileSize;
    private RandomAccessFile accFile;
    FileOutputStream fos;
    private long currentSize=0;
    public synchronized long writeFile(String line) throws IOException{
        byte[] fileBytes = Base64.getDecoder().decode(line);
        
        
        fos.write(fileBytes);
        currentSize += fileBytes.length;
//        System.out.println("datawriter geldi");
//        accFile.seek(accFile.length());
//        System.out.println("datawriter geldi2");
//        accFile.write(data);
        System.out.println(getMaxFileSize() + " " + getCurrentFileSize());
        if(getMaxFileSize() == getCurrentFileSize()){
            close();
        }
        return currentSize;
    }
    public void close()throws IOException{
        fos.close();
    }
    public String getMaxFileSize(){
        return convertFile(fileSize);
    }
    
    public String getCurrentFileSize() throws IOException{
        return convertFile(currentSize);
    }
    
    public double getPercentage() throws IOException{
        double percentage;
        long filePointer = currentSize;
        percentage = filePointer * 100 / fileSize;
        return percentage;
    }
    private String convertFile(double bytes){
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
}
