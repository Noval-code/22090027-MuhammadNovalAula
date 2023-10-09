/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concertApp;

/**
 *
 * @author muhammad noval aula
 */
public class purchase {
      
    private int pembelianId;
    private int ticketId;
    private int jumlahTicket;
    private double totalHarga;
    
    public void setPembelianId(int pembelianId){
        this.pembelianId = pembelianId;
    }
    public int getPembelianId(){
        return pembelianId;
    }
    public void setTicketId(ticket ticket){
        this.ticketId = ticket.getTicketId();
    }
    public int getTicketId(){
        return ticketId;
    }
    public void setJumlahTicket(int jumlahTicket){
        this.jumlahTicket = jumlahTicket;
    }
    public int getJumlahTicket(){
        return jumlahTicket;
    }
    public void setTotalHarga(ticket ticket){
        this.totalHarga = ticket.getHargaTiket() * this.jumlahTicket;
    }
    public double getTotalHarga(){
        return totalHarga;
    }
}
