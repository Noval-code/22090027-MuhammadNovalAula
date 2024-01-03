/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package concertApp.user;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import java.text.ParseException;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author muhammad noval aula
 */
public class Purchase extends javax.swing.JFrame {

    /**
     * Creates new form pembelian
     */
    public Statement st;
    public ResultSet rs;
    Connection con = connection.koneksiDB.BukaKoneksi();
    public double totalPrice;
    public static String IDTicket;
    public static String Ticket;
    public static String TicketType;
    public static String TicketPrice;
    public static String Kuota;
    private static int userId = 1;

    public Purchase() {
        initComponents();
        generatePurchaseID();
        generatePurchaseDate();
        generatePurchaseCode("0");
        ShowPurchase();
        setLocationRelativeTo(null);

    }

    public void generatePurchaseID() {
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM purchase WHERE id_pembelian IN (SELECT MAX(id_pembelian) FROM purchase)");

            // Mendapatkan tanggal, bulan, dan tahun saat ini
            Date purchaseDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String tanggalBulanTahun = dateFormat.format(purchaseDate).replaceAll("-", "");

            // Mendapatkan angka ID user dan meningkatkan counter
            String userID = null;

            // Menggabungkan semua komponen untuk membentuk ID pembelian sebagai integer
            String pembelianID = null;

            if (rs.next()) {
                String lastPurchaseID = rs.getString("id_pembelian");
                int lastPurchaseNumber = Integer.parseInt(lastPurchaseID.substring(8));
                int newPurchaseNumber = lastPurchaseNumber + 1;
                userID = String.format("%03d", newPurchaseNumber);
                pembelianID = tanggalBulanTahun.substring(2, 8) + userID;
            } else {
                userID = String.format("%03d", userId);
                pembelianID = tanggalBulanTahun.substring(2, 8) + userID;
            }

            PurchaseID_Txt.setText(pembelianID);
        } catch (SQLException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    ;

    public static void generatePurchaseCode(String ticketKode) {

        
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateCode = dateFormat.format(currentDate).replaceAll("-", "");

        String lastID = PurchaseID_Txt.getText().substring(8);
        int lastIDNumber = Integer.parseInt(lastID);

        String userID = String.format("%03d", lastIDNumber);;

        // Menggabungkan semua komponen untuk membentuk ID pembelian sebagai integer
        String purchaseCode = userID + ticketKode + dateCode.substring(2, 8);

        PurchaseCode_Txt.setText(purchaseCode);

    }

    public void generatePurchaseDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String PurchaseDate = dateFormat.format(currentDate);

        Date_Txt.setText(PurchaseDate);
        Date_Txt.setEditable(false);
    }

    public static void setIDTicket(String id) {
        IDTicket = id;
    }

    public static void setTicket(String ticket) {
        Ticket = ticket;
    }

    public static void setTicketType(String type) {
        TicketType = type;
    }

    public static void setTicketPrice(String price) {
        TicketPrice = price;
    }

    public static void setKuota(String kuota) {
        Kuota = kuota;
    }

    public static String getIDTicket() {
        return IDTicket;
    }

    public static String getTicket() {
        return Ticket;
    }

    public static String getTicketType() {
        return TicketType;
    }

    public static String getTicketPrice() {
        return TicketPrice;
    }

    public static String getKuota() {
        return Kuota;
    }

    public void showTicket() {
        try {
            Ticket_Txt.setText(getTicket());
            TicketType_Txt.setText(getTicketType());
            TicketPrice_Txt.setText(getTicketPrice());
            Kuota_Txt.setText(getTicketPrice());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ShowPurchase() {
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM purchase");

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("ID Pembelian");
            model.addColumn("Kode Pembelian");
            model.addColumn("Nama");
            model.addColumn("Email");
            model.addColumn("Ticket");
            model.addColumn("Jenis Tiket");
            model.addColumn("Tanggal Pembelian");
            model.addColumn("Harga");
            model.addColumn("Jumlah");
            model.addColumn("Total Harga");

            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("id_pembelian"),
                    rs.getString("kode_pembelian"),
                    rs.getString("nama"),
                    rs.getString("email"),
                    rs.getString("tiket"),
                    rs.getString("jenis_tiket"),
                    rs.getString("tanggal"),
                    rs.getString("harga"),
                    rs.getString("jumlah"),
                    rs.getString("total_harga")
                };
                model.addRow(data);
                Purchase_Table.setModel(model);

            }

        } catch (Exception e) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    private void findPurchase() {
        try {
            st = con.createStatement();
            String searchQuery = "SELECT * FROM purchase WHERE id_pembelian LIKE '%" + SearchField_Txt.getText()
                    + "%' OR kode_pembelian LIKE '%" + SearchField_Txt.getText()
                    + "%' OR nama LIKE '%" + SearchField_Txt.getText()
                    + "%' OR email LIKE '%" + SearchField_Txt.getText()
                    + "%' OR tiket LIKE '%" + SearchField_Txt.getText()
                    + "%' OR jenis_tiket LIKE '%" + SearchField_Txt.getText()
                    + "%' OR tanggal LIKE '%" + SearchField_Txt.getText()
                    + "%' OR harga LIKE '%" + SearchField_Txt.getText()
                    + "%' OR total_harga LIKE '%" + SearchField_Txt.getText()
                    + "%' OR status LIKE '%" + SearchField_Txt.getText() + "%'";
            rs = st.executeQuery(searchQuery);

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("ID Pembelian");
            model.addColumn("Kode Pembelian");
            model.addColumn("Nama");
            model.addColumn("Email");
            model.addColumn("Tiket");
            model.addColumn("Jenis Tiket");
            model.addColumn("Tanggal Pembelian");
            model.addColumn("Harga");
            model.addColumn("Jumlah");
            model.addColumn("Total Harga");

            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("id_pembelian"),
                    rs.getString("kode_pembelian"),
                    rs.getString("nama"),
                    rs.getString("email"),
                    rs.getString("tiket"),
                    rs.getString("jenis_tiket"),
                    rs.getString("tanggal"),
                    rs.getString("harga"),
                    rs.getString("jumlah"),
                    rs.getString("total_harga"),};
                model.addRow(data);
                Purchase_Table.setModel(model);

            }

        } catch (Exception e) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void clear() {
        PurchaseID_Txt.setText("");
        PurchaseCode_Txt.setText("");
        Name_Txt.setText("");
        Email_Txt.setText("");
        Ticket_Txt.setText("");
        TicketType_Txt.setText("");
        Kuota_Txt.setText("");
        TicketPrice_Txt.setText("");
        TicketAmount_Txt.setText("");
        TotalPrice_Txt.setText("");

        generatePurchaseID();
        generatePurchaseCode("0");
        generatePurchaseDate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        PurchaseID_Txt = new javax.swing.JTextField();
        Name_Txt = new javax.swing.JTextField();
        Email_Txt = new javax.swing.JTextField();
        Date_Txt = new javax.swing.JTextField();
        TicketPrice_Txt = new javax.swing.JTextField();
        TicketAmount_Txt = new javax.swing.JTextField();
        TotalPrice_Txt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Purchase_Table = new javax.swing.JTable();
        SaveBtn = new javax.swing.JButton();
        DeleteBtn = new javax.swing.JButton();
        UndoBtn = new javax.swing.JButton();
        DownloadQRTicket = new javax.swing.JButton();
        SearchField_Txt = new javax.swing.JTextField();
        SearchBtn = new javax.swing.JButton();
        backToMenuBtn = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        choiceScheduleBtn = new javax.swing.JButton();
        Kuota_Txt = new javax.swing.JTextField();
        Ticket_Txt = new javax.swing.JTextField();
        TicketType_Txt = new javax.swing.JTextField();
        PurchaseCode_Txt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("ID Pembelian         :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Nama                    :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Email                     :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Tiket                      :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Jenis Tiket              :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Tanggal Pembelian   :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Kuota                        :");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Harga                        :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Jumlah                      :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Total Harga              :");

        TicketAmount_Txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TicketAmount_TxtKeyReleased(evt);
            }
        });

        Purchase_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10"
            }
        ));
        Purchase_Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Purchase_TableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Purchase_Table);

        SaveBtn.setBackground(new java.awt.Color(51, 24, 107));
        SaveBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SaveBtn.setForeground(new java.awt.Color(255, 255, 255));
        SaveBtn.setText("simpan");
        SaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveBtnActionPerformed(evt);
            }
        });

        DeleteBtn.setBackground(new java.awt.Color(51, 24, 107));
        DeleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DeleteBtn.setForeground(new java.awt.Color(255, 255, 255));
        DeleteBtn.setText("hapus");
        DeleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteBtnActionPerformed(evt);
            }
        });

        UndoBtn.setBackground(new java.awt.Color(51, 24, 107));
        UndoBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        UndoBtn.setForeground(new java.awt.Color(255, 255, 255));
        UndoBtn.setText("batal");
        UndoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UndoBtnActionPerformed(evt);
            }
        });

        DownloadQRTicket.setBackground(new java.awt.Color(51, 24, 107));
        DownloadQRTicket.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DownloadQRTicket.setForeground(new java.awt.Color(255, 255, 255));
        DownloadQRTicket.setText("unduh qr");
        DownloadQRTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DownloadQRTicketActionPerformed(evt);
            }
        });

        SearchBtn.setBackground(new java.awt.Color(51, 24, 107));
        SearchBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SearchBtn.setForeground(new java.awt.Color(255, 255, 255));
        SearchBtn.setText("cari");
        SearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBtnActionPerformed(evt);
            }
        });

        backToMenuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-arrow-back-60.png"))); // NOI18N
        backToMenuBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backToMenuBtnMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel12.setText("Pembelian Tiket");

        choiceScheduleBtn.setText("pilih");
        choiceScheduleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choiceScheduleBtnActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Kode Pembelian    :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(backToMenuBtn)
                        .addGap(283, 283, 283)
                        .addComponent(jLabel12))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(Ticket_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(choiceScheduleBtn))
                                            .addComponent(TicketType_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(SaveBtn)
                                                .addGap(66, 66, 66)
                                                .addComponent(DeleteBtn)
                                                .addGap(61, 61, 61)
                                                .addComponent(UndoBtn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(DownloadQRTicket))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(PurchaseID_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(Name_Txt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(PurchaseCode_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(Email_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(26, 26, 26)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel6)
                                                            .addComponent(jLabel8))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(Date_Txt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(TicketPrice_Txt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(Kuota_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(TicketAmount_Txt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(TotalPrice_Txt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(SearchField_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)))
                            .addComponent(SearchBtn))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(backToMenuBtn)
                    .addComponent(jLabel12))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(PurchaseID_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Date_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(Kuota_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PurchaseCode_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(TicketPrice_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Name_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(TicketAmount_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Email_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(TotalPrice_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choiceScheduleBtn)
                    .addComponent(Ticket_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TicketType_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SaveBtn)
                    .addComponent(DeleteBtn)
                    .addComponent(UndoBtn)
                    .addComponent(DownloadQRTicket))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchField_Txt, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchBtn))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(155, 155, 155))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveBtnActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:

            String currentDate = Date_Txt.getText();

           
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = inputDateFormat.parse(currentDate);

            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = outputDateFormat.format(date);

            st = con.createStatement();
            String cekData = "SELECT * FROM purchase WHERE id_pembelian = '" + PurchaseID_Txt.getText() + "'";
            rs = st.executeQuery(cekData);

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "data sudah ada");

            } else {
                String addData = "INSERT INTO purchase VALUES ('" + PurchaseID_Txt.getText()
                        + "','" + PurchaseCode_Txt.getText()
                        + "','" + Name_Txt.getText()
                        + "','" + Email_Txt.getText()
                        + "','" + Ticket_Txt.getText()
                        + "','" + TicketType_Txt.getText()
                        + "','" + formattedDate
                        + "','" + TicketPrice_Txt.getText()
                        + "','" + TicketAmount_Txt.getText()
                        + "','" + TotalPrice_Txt.getText()
                        + "','" + "aktif"
                        + "')";
                st.executeUpdate(addData);

                String updateKuotaQuery = "UPDATE tickets SET kuota = kuota - " + TicketAmount_Txt.getText()
                        + " WHERE id = '" + getIDTicket() + "'";
                st.executeUpdate(updateKuotaQuery);

                userId++;
                generatePurchaseID();
                generatePurchaseCode("0");
                JOptionPane.showMessageDialog(null, "berhasil melakukan pembelian");
                clear();
                ShowPurchase();

            }
        } catch (SQLException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_SaveBtnActionPerformed

    private void DownloadQRTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DownloadQRTicketActionPerformed
        // TODO add your handling code here:
        try {
            ByteArrayOutputStream out = QRCode.from(PurchaseCode_Txt.getText())
                    .to(ImageType.PNG).stream();
            String qr_name = PurchaseCode_Txt.getText();
            String path = "C:\\Users\\muhammad noval aula\\Downloads\\barcode\\";

            FileOutputStream fous = new FileOutputStream(new File(path + (qr_name + ".png")));
            fous.write(out.toByteArray());
            fous.flush();
            JOptionPane.showMessageDialog(null, "anda berhasil membuat qr");
        } catch (Exception e) {
            System.out.println(e);
        }

    }//GEN-LAST:event_DownloadQRTicketActionPerformed

    private void SearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBtnActionPerformed
        // TODO add your handling code here:
        findPurchase();
    }//GEN-LAST:event_SearchBtnActionPerformed

    private void choiceScheduleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choiceScheduleBtnActionPerformed
        // TODO add your handling code here:
        new ModalTicket().setVisible(true);
    }//GEN-LAST:event_choiceScheduleBtnActionPerformed

    private void TicketAmount_TxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TicketAmount_TxtKeyReleased
        // TODO add your handling code here:
        String inputAmount = TicketAmount_Txt.getText();

        if (!inputAmount.isEmpty()) {
            try {
                int Amount = Integer.parseInt(inputAmount);
                double Price = Double.parseDouble(TicketPrice_Txt.getText());
                totalPrice = Amount * Price;
                TotalPrice_Txt.setText(String.valueOf(totalPrice));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }//GEN-LAST:event_TicketAmount_TxtKeyReleased

    private void UndoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UndoBtnActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_UndoBtnActionPerformed

    private void backToMenuBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backToMenuBtnMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new HomeUser().setVisible(true);
    }//GEN-LAST:event_backToMenuBtnMouseClicked

    private void Purchase_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Purchase_TableMouseClicked
        // TODO add your handling code here:
        PurchaseID_Txt.setText(Purchase_Table.getValueAt(Purchase_Table.getSelectedRow(), 0).toString());
        PurchaseCode_Txt.setText(Purchase_Table.getValueAt(Purchase_Table.getSelectedRow(), 1).toString());
        Name_Txt.setText(Purchase_Table.getValueAt(Purchase_Table.getSelectedRow(), 2).toString());
        Email_Txt.setText(Purchase_Table.getValueAt(Purchase_Table.getSelectedRow(), 3).toString());
        Ticket_Txt.setText(Purchase_Table.getValueAt(Purchase_Table.getSelectedRow(), 4).toString());
        TicketType_Txt.setText(Purchase_Table.getValueAt(Purchase_Table.getSelectedRow(), 5).toString());
        Date_Txt.setText(Purchase_Table.getValueAt(Purchase_Table.getSelectedRow(), 6).toString());

        TicketPrice_Txt.setText(Purchase_Table.getValueAt(Purchase_Table.getSelectedRow(), 7).toString());
        TicketAmount_Txt.setText(Purchase_Table.getValueAt(Purchase_Table.getSelectedRow(), 8).toString());
        TotalPrice_Txt.setText(Purchase_Table.getValueAt(Purchase_Table.getSelectedRow(), 9).toString());
    }//GEN-LAST:event_Purchase_TableMouseClicked

    private void DeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBtnActionPerformed
        // TODO add your handling code here:
        if (PurchaseID_Txt.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Silahkan pilih data yang akan di hapus");
        } else {
            int jawab = JOptionPane.showConfirmDialog(null, "Data ini akan dihapus, lanjutkan?", "konfirmasi", JOptionPane.YES_NO_OPTION);
            if (jawab == 0) {
                try {
                    st = con.createStatement();
                    String sql = "DELETE FROM purchase WHERE id_pembelian = '" + PurchaseID_Txt.getText() + "'";
                    st.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "data berhail di hapus");
                    
                    String updateKuotaQuery = "UPDATE tickets SET kuota = kuota + " + TicketAmount_Txt.getText()
                            + " WHERE id = '" + getIDTicket() + "'";
                    st.executeUpdate(updateKuotaQuery);
                    ShowPurchase();
                    clear();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }

        }
    }//GEN-LAST:event_DeleteBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Purchase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Purchase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Purchase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Purchase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Purchase().setVisible(true);
            }

        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Date_Txt;
    private javax.swing.JButton DeleteBtn;
    private javax.swing.JButton DownloadQRTicket;
    public javax.swing.JTextField Email_Txt;
    public static javax.swing.JTextField Kuota_Txt;
    private javax.swing.JTextField Name_Txt;
    public static javax.swing.JTextField PurchaseCode_Txt;
    public static javax.swing.JTextField PurchaseID_Txt;
    private javax.swing.JTable Purchase_Table;
    private javax.swing.JButton SaveBtn;
    private javax.swing.JButton SearchBtn;
    private javax.swing.JTextField SearchField_Txt;
    private javax.swing.JTextField TicketAmount_Txt;
    public static javax.swing.JTextField TicketPrice_Txt;
    public static javax.swing.JTextField TicketType_Txt;
    public static javax.swing.JTextField Ticket_Txt;
    private javax.swing.JTextField TotalPrice_Txt;
    private javax.swing.JButton UndoBtn;
    private javax.swing.JLabel backToMenuBtn;
    private javax.swing.JButton choiceScheduleBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
