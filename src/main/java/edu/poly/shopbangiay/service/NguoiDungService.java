package edu.poly.shopbangiay.service;

import edu.poly.shopbangiay.model.NguoiDung;

import java.util.List;

public interface NguoiDungService {
    List<NguoiDung> getList();
    NguoiDung getNDByMa(String ma);
    List<NguoiDung> timKiem(String ten);
    Boolean them(NguoiDung nguoiDung);
    Boolean sua(NguoiDung nguoiDung);
    Boolean xoa(NguoiDung nguoiDung);
}
