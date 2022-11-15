package edu.poly.shopbangiay.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
public class ChiTietSanPham implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition="uniqueidentifier")
    private String id;

    @ManyToOne
    @JoinColumn(name = "IDSP", columnDefinition = "uniqueidentifier")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "IDNSX", columnDefinition = "uniqueidentifier")
    private NSX nsx;

    @ManyToOne
    @JoinColumn(name = "IDMS", columnDefinition = "uniqueidentifier")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "IDCL", columnDefinition = "uniqueidentifier")
    private ChatLieu chatLieu;

    @ManyToOne
    @JoinColumn(name = "IDSize", columnDefinition = "uniqueidentifier")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "IDLoai", columnDefinition = "uniqueidentifier")
    private Loai loai;

    @Column
    private Integer soLuong;
    @Column
    private Float giaNhap;
    @Column
    private Float giaBan;
    @Column
    private String code;
    @Column
    private String hinhAnh;

    @OneToMany(mappedBy = "chiTietSanPham", fetch = FetchType.LAZY)
    private List<ChiTietHoaDon> chiTietHoaDonList;
}
