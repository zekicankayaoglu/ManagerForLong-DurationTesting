
package Data;
import com.corundumstudio.socketio.SocketIOClient;
import java.awt.Component;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JTable;
import swing.PanelStatus;
/**
 *
 * @author zekican
 */
public class DataClient {

    /**
     * @return the status
     */
    public PanelStatus getStatus() {
        return status;
    }

    /**
     * @return the client
     */
    public SocketIOClient getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(SocketIOClient client) {
        this.client = client;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    public DataClient(SocketIOClient client, String name, JTable table){
        this.client = client;
        this.name = name;
        this.status = new PanelStatus();
        this.table = table;
    }
    private SocketIOClient client;
    private String name;
    // key int is fileID
    // hash to store multiple transfer
    private final HashMap<Integer,DataWriter> list = new HashMap();
    private PanelStatus status;
    private JTable table;
    
    public void addWrite(DataWriter data, int fileID){
        list.put(fileID, data);
        System.out.println("id geldi addWrite:" + fileID);
        status.addItem(fileID, data.getFile().getName(), data.getMaxFileSize());
        autoRowHeight(table, 3);
        System.out.println("addWrite kontrolü" + list.containsKey(fileID));
    }
    public void writeFile(String line, int fileID) throws IOException{
        System.out.println("write file gelen id: " + fileID + " " + list.containsKey(fileID));
        list.get(fileID).writeFile(line);
        status.updateStatus(fileID, (int) list.get(fileID).getPercentage());
        table.repaint();
    }
    public void closeWriter(int fileID) throws IOException{
        System.out.println("close gelen file id" + fileID);
        list.get(fileID).close();
    }
    public Object[] toRowTable(int row){
        return new Object[]{this,row,name};
    }
    private void autoRowHeight(JTable table, int... cols) {
        for (int row = 0; row < table.getRowCount(); row++) {
            int rowHeight = table.getRowHeight();
            for (int col : cols) {
                Component comp = table.prepareRenderer(table.getCellRenderer(row, col), row, col);
                if (comp.getPreferredSize().height > rowHeight) {
                    rowHeight = comp.getPreferredSize().height;
                }
            }
            table.setRowHeight(row, rowHeight);
        }
    }
    public DataFileServer getDataFileServer(int fileID){
        DataWriter data = list.get(fileID);
        return new DataFileServer(fileID,data.getFile().getName(),data.getMaxFileSize(),data.getFile());
    }
}
