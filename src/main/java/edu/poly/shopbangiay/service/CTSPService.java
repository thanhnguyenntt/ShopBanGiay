package edu.poly.shopbangiay.service;

import edu.poly.shopbangiay.model.ChiTietSanPham;
import edu.poly.shopbangiay.viewModel.VMCTSP;

import java.util.List;

public interface CTSPService {
    List<VMCTSP> getListVMCTSP();
    List<ChiTietSanPham> getList();
    List<ChiTietSanPham> timKiem(String ten);

    ChiTietSanPham getCTSPByMaSP(String maSP);
    Boolean them(ChiTietSanPham ctsp);
    Boolean sua(ChiTietSanPham ctsp);
    Boolean xoa(ChiTietSanPham ctsp);
}
