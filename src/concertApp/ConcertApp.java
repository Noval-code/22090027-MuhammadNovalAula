/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concertApp;

import java.util.ArrayList;

/**
 *
 * @author muhammad noval aula
 */
public class ConcertApp {
    
    public static void main(String[] args) {
        
        ArrayList<concert> listConcert = new ArrayList<>();
        
        concert naraya = new concert();
        naraya.setConcertId(123);
        naraya.setEventName("naraya");
        naraya.setLocation("manunggal");
       
        listConcert.add(naraya);
        
        for(concert concertItem : listConcert){
              System.out.println("id concert : " + concertItem.getConcertId() +
                            "\nnama konser : " + concertItem.getEventName() +
                            "\nlokasi konser : "+ concertItem.getLocation());
        }
        
        ticket tiket = new ticket();
        tiket.setConcertId(naraya);
        tiket.setTicketId(444);
        tiket.setHargaTiket(100000);
        
        System.out.println(  "id konser : " + tiket.getConcertId() +
                              "\nid tiket : "+ tiket.getTicketId() +
                               "\nharga tiket : " + tiket.getHargaTiket());
    }
}
