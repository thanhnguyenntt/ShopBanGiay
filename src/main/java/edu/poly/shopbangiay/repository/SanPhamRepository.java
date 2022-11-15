package edu.poly.shopbangiay.repository;

import edu.poly.shopbangiay.Ultilities.Hibernate;
import edu.poly.shopbangiay.model.SanPham;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class SanPhamRepository {
    Session session = Hibernate.getFACTORY().openSession();
    Transaction transaction;

    public List<SanPham> getList(){
        Query query = session.createQuery("from SanPham ");
        return query.getResultList();
    }

    public SanPham getOne(String ma){
            Query query = session.createQuery("from SanPham where ma =: ma");
            query.setParameter("ma", ma);
            return (SanPham) query.getSingleResult();
    }

    public Boolean them(SanPham sanPham){
        try {
            transaction = session.beginTransaction();

            session.save(sanPham);

            transaction.commit();
            return true;
        }catch (Exception ex){
            transaction.rollback();
            return false;
        }
    }

    public Boolean sua(SanPham sanPham){
        try {
            transaction = session.beginTransaction();

            session.update(sanPham);

            transaction.commit();
            return true;
        }catch (Exception ex){
            transaction.rollback();
            return false;
        }
    }

    public Boolean xoa(SanPham sanPham){
        try {
            transaction = session.beginTransaction();

            session.delete(sanPham);

            transaction.commit();
            return true;
        }catch (Exception ex){
            transaction.rollback();
            return false;
        }
    }

}
