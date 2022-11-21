package edu.poly.shopbangiay.repository;

import edu.poly.shopbangiay.Ultilities.Hibernate;
import edu.poly.shopbangiay.model.ChiTietHoaDon;
import edu.poly.shopbangiay.model.ChiTietSanPham;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class CTHDRepository {
    Session session = Hibernate.getFACTORY().openSession();
    Transaction transaction;

    public List<ChiTietHoaDon> getList(){
        Query query = session.createQuery("from ChiTietHoaDon ");
        return query.getResultList();
    }

    public List<ChiTietHoaDon> timKiem(String ma){
        Query query = session.createQuery("from ChiTietHoaDon cthd where cthd.hoaDon.ma like: maHD or cthd.chiTietSanPham.sanPham.ma like: maSP");
        query.setParameter("maHD", "%" + ma + "%");
        query.setParameter("maSP", "%" + ma + "%");
        return query.getResultList();
    }

    public Boolean them(ChiTietHoaDon cthd){
        try{
            transaction = session.beginTransaction();

            session.save(cthd);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean sua(ChiTietHoaDon cthd){
        try{
            transaction = session.beginTransaction();

            session.update(cthd);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean xoa(ChiTietHoaDon cthd){
        try{
            transaction = session.beginTransaction();

            session.delete(cthd);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }
}
