package edu.poly.shopbangiay.service.impl;

import edu.poly.shopbangiay.model.NSX;
import edu.poly.shopbangiay.repository.NSXRepository;
import edu.poly.shopbangiay.service.NSXService;

import java.util.List;

public class NSXServiceImpl implements NSXService {
    NSXRepository nsxRepository = new NSXRepository();

    @Override
    public List<NSX> getList() {
        return nsxRepository.getList();
    }

    @Override
    public NSX getOne(String ma) {
        return null;
    }

    @Override
    public List<NSX> timKiem(String ten) {
        return nsxRepository.timKiem(ten);
    }

    @Override
    public Boolean them(NSX nsx) {
        return nsxRepository.them(nsx);
    }

    @Override
    public Boolean sua(NSX nsx) {
        return sua(nsx);
    }

    @Override
    public Boolean xoa(NSX nsx) {
        return nsxRepository.xoa(nsx);
    }
}
