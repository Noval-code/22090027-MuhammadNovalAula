/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concertApp;

/**
 *
 * @author muhammad noval aula
 */
public class purchase_history {
    private int historyId;
    private int userId;
    private int pembelianId;
    
    public void setHistoryId(int historyId){
        this.historyId = historyId;
    }
    public int getHistoryId(){
        return historyId;
    }
    
    public void setPembelianId(purchase purchase){
        this.pembelianId = purchase.getPembelianId();
    }
    public int getPembelianId(){
        return pembelianId;
    }
    
}
