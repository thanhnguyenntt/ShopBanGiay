package edu.poly.shopbangiay.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class HoaDon implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition="uniqueidentifier")
    private String id;

    @ManyToOne
    @JoinColumn(name = "IDKH", columnDefinition = "uniqueidentifier")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "IDND", columnDefinition = "uniqueidentifier")
    private NguoiDung nguoiDung;

    @Column(unique = true, nullable = false)
    private String ma;
    @Column
    private Date ngayTao;
    @Column
    private Date ngayTT;
    @Column
    private Boolean tinhTrang;

    @OneToMany(mappedBy = "hoaDon", fetch = FetchType.LAZY)
    private List<ChiTietHoaDon> chiTietHoaDonList;
}
