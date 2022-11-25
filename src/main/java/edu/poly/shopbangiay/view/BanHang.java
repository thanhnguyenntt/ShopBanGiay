/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package edu.poly.shopbangiay.view;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import edu.poly.shopbangiay.model.ChiTietHoaDon;
import edu.poly.shopbangiay.model.ChiTietSanPham;
import edu.poly.shopbangiay.model.HoaDon;
import edu.poly.shopbangiay.model.KhachHang;
import edu.poly.shopbangiay.service.CTHDService;
import edu.poly.shopbangiay.service.CTSPService;
import edu.poly.shopbangiay.service.HoaDonService;
import edu.poly.shopbangiay.service.KhachHangService;
import edu.poly.shopbangiay.service.impl.CTHDServiceImpl;
import edu.poly.shopbangiay.service.impl.CTSPServiceImpl;
import edu.poly.shopbangiay.service.impl.HoaDonServiceImpl;
import edu.poly.shopbangiay.service.impl.KhachHangServiceImpl;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.image.BufferedImage;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import table.TableCustom;

/**
 *
 * @author Quang
 */
public class BanHang extends javax.swing.JDialog implements Runnable, ThreadFactory{

    /**
     * Creates new form BanHang
     */
    //cam
    private static final long serialVersionUID = 6441489157408381878L;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    private Webcam webcam = null;
    private WebcamPanel panel = null;
    private JTextArea textarea = null;
    //

    private DefaultTableModel defaultTableModel;
    private DefaultComboBoxModel defaultComboBoxModel;
    private CTSPService ctspService = new CTSPServiceImpl();
    private HoaDonService hoaDonService = new HoaDonServiceImpl();
    private CTHDService cthdService = new CTHDServiceImpl();
    private KhachHangService khachHangService = new KhachHangServiceImpl();

    
    public BanHang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       
        initWebcam(Cam);
        TableCustom.apply(jScrollPane1, TableCustom.TableType.MULTI_LINE);
        TableCustom.apply(jScrollPane2, TableCustom.TableType.MULTI_LINE);
        TableCustom.apply(jScrollPane3, TableCustom.TableType.MULTI_LINE);
        loadSP(ctspService.getList());
        loadHD(hoaDonService.getList());
        loadCBXKH(khachHangService.getList());
    }

    
    public void loadSP(List<ChiTietSanPham> list) {
        defaultTableModel = (DefaultTableModel) tblSP.getModel();
        defaultTableModel.setRowCount(0);
        int stt = 1;
        for (ChiTietSanPham ctsp : list) {
            defaultTableModel.addRow(new Object[]{
                stt++,
                ctsp.getSanPham().getMa(),
                ctsp.getSanPham().getTen(),
                ctsp.getSize().getSoSize(),
                ctsp.getLoai().getTen(),
                ctsp.getNsx().getTen(),
                ctsp.getSoLuong(),
                ctsp.getGiaBan()
            });
        }
    }

    public void loadGH(List<ChiTietHoaDon> list) {
        defaultTableModel = (DefaultTableModel) tblGH.getModel();
        defaultTableModel.setRowCount(0);
        int stt = 1;
        for (ChiTietHoaDon cthd : list) {
            defaultTableModel.addRow(new Object[]{
                stt++,
                cthd.getChiTietSanPham().getSanPham().getMa(),
                cthd.getChiTietSanPham().getSanPham().getTen(),
                cthd.getSoLuong(),
                cthd.getDonGia(),
                cthd.tongTien()
            });
        }
    }

    public void loadHD(List<HoaDon> list) {
        defaultTableModel = (DefaultTableModel) tblHD.getModel();
        defaultTableModel.setRowCount(0);
        int stt = 1;
        for (HoaDon hd : list) {
            defaultTableModel.addRow(new Object[]{
                stt++,
                hd.getMa(),
                null,
                hd.getNgayTao(),
                hd.getTinhTrang() == true ? "Chưa thanh toán" : "Đã thanh toán"
            });
        }
    }

    public void loadCBXKH(List<KhachHang> list) {
        defaultComboBoxModel = (DefaultComboBoxModel) cbxKH.getModel();
        defaultComboBoxModel.removeAllElements();

        for (KhachHang kh : list) {
            defaultComboBoxModel.addElement(kh);
        }
    }

    public String genMaHD() {
        for (int i = 0; i < hoaDonService.getList().size() + 1; i++) {
            String ma = "HD" + i;
            if (hoaDonService.getHDByMa(ma) == null) {
                return ma;
            }
        }
        return null;
    }

    public Boolean checkTT() {
        int row = tblHD.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "vui long click lai hoa don");
            return false;
        }
        HoaDon hoaDon = hoaDonService.getList().get(row);
        if (hoaDon.getTinhTrang() == false) {
            JOptionPane.showMessageDialog(this, "hoa don da thanh toan");
            return false;
        }
        return true;
    }

    public Double tongTien() {
        double sum = 0.0;
        for (int i = 0; i < tblGH.getRowCount(); i++) {
            sum += Float.parseFloat(tblGH.getValueAt(i, 5).toString());
        }
        return sum;
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
        jPanel2 = new javax.swing.JPanel();
        tableScrollButton3 = new table.TableScrollButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        txtTimHD = new textfield.TextField();
        btnThemHD = new edu.poly.shopbangiay.raven.button.Button();
        jPanel3 = new javax.swing.JPanel();
        btnHuyHD = new edu.poly.shopbangiay.raven.button.Button();
        btnThanhToan = new edu.poly.shopbangiay.raven.button.Button();
        cbxKH = new combo_suggestion.ComboBoxSuggestion();
        txtMaHD = new textfield.TextField();
        txtTongTien = new textfield.TextField();
        txtGiamGia = new textfield.TextField();
        btnThemVC = new edu.poly.shopbangiay.raven.button.Button();
        txtThanhTien = new textfield.TextField();
        txtKhachTT = new textfield.TextField();
        txtTienThua = new textfield.TextField();
        jLabel2 = new javax.swing.JLabel();
        btnThemKH = new edu.poly.shopbangiay.raven.button.Button();
        jPanel4 = new javax.swing.JPanel();
        tableScrollButton2 = new table.TableScrollButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGH = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        txtTimSP = new textfield.TextField();
        tableScrollButton1 = new table.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        Cam = new javax.swing.JPanel();
        btnOnCam = new edu.poly.shopbangiay.raven.button.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 102), 2, true));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã", "Người tạo", "Ngày tạo", "Trạng thái"
            }
        ));
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHD);

        tableScrollButton3.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        txtTimHD.setLabelText("Tìm hóa đơn");
        txtTimHD.setLineColor(new java.awt.Color(153, 153, 153));

        btnThemHD.setBackground(new java.awt.Color(204, 255, 255));
        btnThemHD.setText("Tạo hóa đơn mới");
        btnThemHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableScrollButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimHD, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(tableScrollButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnThemHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thanh toán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnHuyHD.setBackground(new java.awt.Color(255, 51, 153));
        btnHuyHD.setText("Hủy hóa đơn");
        btnHuyHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        btnThanhToan.setBackground(new java.awt.Color(153, 255, 153));
        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        cbxKH.setToolTipText("");

        txtMaHD.setEditable(false);
        txtMaHD.setBackground(new java.awt.Color(255, 255, 255));
        txtMaHD.setLabelText("Mã hóa đơn");

        txtTongTien.setEditable(false);
        txtTongTien.setBackground(new java.awt.Color(255, 255, 255));
        txtTongTien.setLabelText("Tổng tiền");

        txtGiamGia.setLabelText("Giảm giá");

        btnThemVC.setText("+");

        txtThanhTien.setEditable(false);
        txtThanhTien.setBackground(new java.awt.Color(255, 255, 255));
        txtThanhTien.setLabelText("Thành tiền");

        txtKhachTT.setLabelText("Khách thanh toán");
        txtKhachTT.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtKhachTTCaretUpdate(evt);
            }
        });

        txtTienThua.setEditable(false);
        txtTienThua.setBackground(new java.awt.Color(255, 255, 255));
        txtTienThua.setLabelText("Tiền thừa cho khách");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Khách hàng:");

        btnThemKH.setText("+");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtThanhTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(txtKhachTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(txtTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThemVC, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnHuyHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cbxKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxKH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemVC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtKhachTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHuyHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tblGH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã", "Tên", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        tblGH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGHMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGH);

        tableScrollButton2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableScrollButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableScrollButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtTimSP.setLabelText("Tìm kiếm");

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã", "Tên", "Loại", "NSX", "Size", "Số lượng tồn", "Đơn giá"
            }
        ));
        tblSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSP);

        tableScrollButton1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimSP, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 906, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(txtTimSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Cam.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Webcam", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        Cam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnOnCam.setText("Mở Camera");
        btnOnCam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnCamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Cam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOnCam, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Cam, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOnCam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        // TODO add your handling code here:
        int row = tblHD.getSelectedRow();
        txtMaHD.setText(tblHD.getValueAt(row, 1).toString());

        loadGH((List<ChiTietHoaDon>) cthdService.getCTHDByMaHD(txtMaHD.getText()));
        txtTongTien.setText(tongTien().toString());
    }//GEN-LAST:event_tblHDMouseClicked

    private void btnThemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHDActionPerformed
        // TODO add your handling code here:
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMa(genMaHD());
        hoaDon.setNgayTao(Date.valueOf(LocalDate.now()));
        hoaDon.setTinhTrang(true);

        hoaDonService.them(hoaDon);
        loadHD(hoaDonService.getList());
    }//GEN-LAST:event_btnThemHDActionPerformed

    private void txtKhachTTCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtKhachTTCaretUpdate
        // TODO add your handling code here:
        if (!txtKhachTT.getText().equals("")) {
            txtTienThua.setText(String.valueOf((Integer.parseInt(txtKhachTT.getText())) - Float.parseFloat(txtTongTien.getText())));
        }
    }//GEN-LAST:event_txtKhachTTCaretUpdate

    private void tblGHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGHMouseClicked
        // TODO add your handling code here:
        int rowGH = tblGH.getSelectedRow();
        if (rowGH == -1) {
            JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm");
            return;
        }
        String input = JOptionPane.showInputDialog("Nhập lại số lượng sản phẩm");
        try {
            Integer.parseInt(input);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Nhập lại");
            return;
        }

        if (Integer.parseInt(input) < 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
            return;
        }

        ChiTietHoaDon cthd = cthdService.getList().get(rowGH);
        if (Integer.parseInt(input) == cthd.getSoLuong()) {
            return;
        }
        int soLuongSP = cthd.getSoLuong();

        ChiTietSanPham ctsp = ctspService.getCTSPByMaSP(tblGH.getValueAt(rowGH, 1).toString());
        if (ctsp.getSoLuong() == 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng");
            return;
        }
        ctsp.setSoLuong(ctsp.getSoLuong() + soLuongSP);

        if (Integer.parseInt(input) == 0) {
            cthdService.xoa(cthd);
            ctsp.setSoLuong(ctsp.getSoLuong() + Integer.parseInt(input));
            ctspService.sua(ctsp);

        }
        if (Integer.parseInt(input) > 0) {
            cthd.setSoLuong(Integer.parseInt(input));
            cthdService.sua(cthd);
            if (Integer.parseInt(input) < cthd.getSoLuong()) {
                ctsp.setSoLuong(ctsp.getSoLuong() + Integer.parseInt(input));
            } else {
                ctsp.setSoLuong(ctsp.getSoLuong() - Integer.parseInt(input));
            }
            ctspService.sua(ctsp);

        }
        loadSP(ctspService.getList());
        loadGH(cthdService.getCTHDByMaHD(txtMaHD.getText()));
    }//GEN-LAST:event_tblGHMouseClicked

    private void tblSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPMouseClicked
        // TODO add your handling code here:
        if (txtMaHD.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chọn hóa đơn trước");
            return;
        } else {
            HoaDon hoaDon = hoaDonService.getHDByMa(txtMaHD.getText());
            if (checkTT()) {
                int rowSP = tblSP.getSelectedRow();
                ChiTietSanPham ctsp = ctspService.getList().get(rowSP);
                if (ctsp.getSoLuong() == 0) {
                    JOptionPane.showMessageDialog(this, "Hết hàng");
                    return;
                } else {
                    String input = JOptionPane.showInputDialog("Nhập số lượng sản phẩm: ");
                    try {
                        Integer.parseInt(input);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Nhập lại số lượng");
                        return;
                    }
                    if (Integer.parseInt(input) > ctsp.getSoLuong()) {
                        JOptionPane.showMessageDialog(this, "Không đủ, số lượng còn lại: " + ctsp.getSoLuong());
                        return;
                    }
                    if (Integer.parseInt(input) <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng nhập vào phải lớn hơn 0");
                        return;
                    } else {
                        if (cthdService.getCTHD(hoaDon.getId(), ctsp.getId()) == null) {
                            ChiTietHoaDon cthd = new ChiTietHoaDon();
                            cthd.setHoaDon(hoaDonService.getHDByMa(txtMaHD.getText()));
                            cthd.setChiTietSanPham(ctspService.getList().get(rowSP));

                            cthd.setSoLuong(Integer.parseInt(input));
                            cthd.setDonGia(Float.parseFloat(tblSP.getValueAt(rowSP, 7).toString()));

                            cthdService.them(cthd);
                        } else {
                            ChiTietHoaDon cthd = cthdService.getCTHD(hoaDon.getId(), ctsp.getId());
                            cthd.setSoLuong(cthd.getSoLuong() + Integer.parseInt(input));
                            cthdService.sua(cthd);
                        }

                        loadGH(cthdService.getCTHDByMaHD(txtMaHD.getText()));

                        ctsp.setSoLuong(ctsp.getSoLuong() - Integer.parseInt(input));
                        ctspService.sua(ctsp);
                    }
                }
            }
        }

        loadSP(ctspService.getList());

        txtTongTien.setText(tongTien().toString());
    }//GEN-LAST:event_tblSPMouseClicked

    private void btnOnCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnCamActionPerformed
        // TODO add your handling code here:
        initWebcam(Cam);
    }//GEN-LAST:event_btnOnCamActionPerformed

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
                if ("FlatLaf Light".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BanHang dialog = new BanHang(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cam;
    private edu.poly.shopbangiay.raven.button.Button btnHuyHD;
    private edu.poly.shopbangiay.raven.button.Button btnOnCam;
    private edu.poly.shopbangiay.raven.button.Button btnThanhToan;
    private edu.poly.shopbangiay.raven.button.Button btnThemHD;
    private edu.poly.shopbangiay.raven.button.Button btnThemKH;
    private edu.poly.shopbangiay.raven.button.Button btnThemVC;
    private combo_suggestion.ComboBoxSuggestion cbxKH;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private table.TableScrollButton tableScrollButton1;
    private table.TableScrollButton tableScrollButton2;
    private table.TableScrollButton tableScrollButton3;
    private javax.swing.JTable tblGH;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblSP;
    private textfield.TextField txtGiamGia;
    private textfield.TextField txtKhachTT;
    private textfield.TextField txtMaHD;
    private textfield.TextField txtThanhTien;
    private textfield.TextField txtTienThua;
    private textfield.TextField txtTimHD;
    private textfield.TextField txtTimSP;
    private textfield.TextField txtTongTien;
    // End of variables declaration//GEN-END:variables

    public void initWebcam(JPanel panelShow) {
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0); //0 is default webcam
        webcam.setViewSize(size);
        
        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);
        panel.setMirrored(true);
        panelShow.add(panel, new AbsoluteConstraints(0, 0, panelShow.getWidth(), panelShow.getHeight()));

        executor.execute(this);
    }

    @Override
    public void run() {

        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {

                if ((image = webcam.getImage()) == null) {
                    continue;
                }

                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {
                    // fall thru, it means there is no QR code in image
                }
            }

            if (result != null) {
//                textarea.setText(result.getText());
                System.out.println(result);
            }

        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "example-runner");
        t.setDaemon(true);
        return t;
    }
}
