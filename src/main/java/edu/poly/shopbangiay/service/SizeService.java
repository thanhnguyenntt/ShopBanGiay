package edu.poly.shopbangiay.service;

import edu.poly.shopbangiay.model.Size;

import java.util.List;

public interface SizeService {
    List<Size> getList();
    Size getOne(String ma);
    List<Size> timKiem(String ten);
    Boolean them(Size size);
    Boolean sua(Size size);
    Boolean xoa(Size size);
}
