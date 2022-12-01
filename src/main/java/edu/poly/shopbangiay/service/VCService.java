package edu.poly.shopbangiay.service;

import edu.poly.shopbangiay.model.KhachHang;
import edu.poly.shopbangiay.model.Voucher;

import java.util.List;

public interface VCService {
    List<Voucher> getList();
    Voucher getVCByMa(String ma);
    List<Voucher> timKiem(String ten);
    Boolean them(Voucher voucher);
    Boolean sua(Voucher voucher);
    Boolean xoa(Voucher voucher);
}
