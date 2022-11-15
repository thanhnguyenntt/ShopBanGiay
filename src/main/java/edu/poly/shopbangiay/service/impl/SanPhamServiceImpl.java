package edu.poly.shopbangiay.service.impl;

import edu.poly.shopbangiay.model.SanPham;
import edu.poly.shopbangiay.repository.SanPhamRepository;
import edu.poly.shopbangiay.service.SanPhamService;

import java.util.List;

public class SanPhamServiceImpl implements SanPhamService {
    SanPhamRepository sanPhamRepository = new SanPhamRepository();

    @Override
    public List<SanPham> getList() {
        return sanPhamRepository.getList();
    }

    @Override
    public SanPham getOne(String ma) {
        return sanPhamRepository.getOne(ma);
    }

    @Override
    public List<SanPham> timKiem(String ma) {
        return sanPhamRepository.timKiem(ma);
    }

    @Override
    public Boolean them(SanPham sanPham) {
        return sanPhamRepository.them(sanPham);
    }

    @Override
    public Boolean sua(SanPham sanPham) {
        return sanPhamRepository.sua(sanPham);
    }

    @Override
    public Boolean xoa(SanPham sanPham) {
        return sanPhamRepository.xoa(sanPham);
    }
}
