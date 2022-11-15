package edu.poly.shopbangiay.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class ChiTietHoaDon implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "IDCTSP",columnDefinition="uniqueidentifier")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private ChiTietSanPham chiTietSanPham;

    @Id
    @ManyToOne
    @JoinColumn(name = "IDHD",columnDefinition="uniqueidentifier")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private HoaDon hoaDon;

    @Column
    private Float thanhTien;
    @Column
    private Float tongTien;
    @Column
    private Integer soLuong;
}
