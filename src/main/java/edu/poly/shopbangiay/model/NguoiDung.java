package edu.poly.shopbangiay.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class NguoiDung implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition="uniqueidentifier")
    private String id;

    @ManyToOne
    @JoinColumn(name = "IDND", columnDefinition = "uniqueidentifier")
    private ChucVu chucVu;

    @Column(unique = true, nullable = false)
    private String ma;
    @Column(length = 50)
    @Nationalized
    private String ten;
    @Column
    private String anh;
    @Column
    private Date ngaySinh;
    @Column
    private Boolean gioiTinh;
    @Column
    private String email;
    @Column
    private String sdt;
    @Column
    private String matKhau;
    @Column
    private Boolean trangThai;

    @OneToMany(mappedBy = "nguoiDung", fetch = FetchType.LAZY)
    private List<HoaDon> hoaDonList;

}
