package edu.poly.shopbangiay.service;

import edu.poly.shopbangiay.model.HoaDon;

import java.util.List;

public interface HoaDonService {
    List<HoaDon> getList();
    HoaDon getOne(String ma);
    HoaDon getHDByMa(String ma);
    List<HoaDon> timKiem(String ten);
    Boolean them(HoaDon hoaDon);
    Boolean sua(HoaDon hoaDon);
    Boolean xoa(HoaDon hoaDon);
}
