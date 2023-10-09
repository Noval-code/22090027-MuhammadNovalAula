/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concertApp;

/**
 *
 * @author muhammad noval aula
 */
public class ticket {
    private int ticketId;
    
    private int concertId;

    private String kodeQr;

    private double hargaTiket;
    
    private String jenisTiket;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public double getHargaTiket() {
        return hargaTiket;
    }

    public void setHargaTiket(int hargaTiket) {
        this.hargaTiket = hargaTiket;
    }
    
   public int getConcertId(){
       return concertId;
   }
   
   public void setConcertId(concert concertId){
       this.concertId = concertId.getConcertId();
   }

   
    public String getKodeQr() {
        return kodeQr;
    }

    public void setKodeQr(String kodeQr) {
        this.kodeQr = kodeQr;
    }

    public String getJenisTiket() {
        return jenisTiket;
    }

    public void setJenisTiket(String jenisTiket) {
        this.jenisTiket = jenisTiket;
    }
}
