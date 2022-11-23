package edu.poly.shopbangiay.repository;

import edu.poly.shopbangiay.Ultilities.Hibernate;
import edu.poly.shopbangiay.model.ChiTietSanPham;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class CTSPRepository {


    public List<ChiTietSanPham> getList(){
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from ChiTietSanPham ");
        return query.getResultList();
    }

    public List<ChiTietSanPham> timKiem(String ten){
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from ChiTietSanPham ctsp where ctsp.sanPham.ten like: ten");
        query.setParameter("ten", "%" + ten + "%");
        return query.getResultList();
    }

    public ChiTietSanPham getCTSPByMaSP(String maSP){
        try (Session session = Hibernate.getFACTORY().openSession();){
            Query query = session.createQuery("from ChiTietSanPham ctsp where ctsp.sanPham.ma =: ma ");
            query.setParameter("ma", maSP);
            return (ChiTietSanPham) query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public Boolean them(ChiTietSanPham ctsp){
        Transaction transaction = null;
        try(Session session = Hibernate.getFACTORY().openSession()){
            transaction = session.beginTransaction();

            session.saveOrUpdate(ctsp);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean sua(ChiTietSanPham ctsp){
        Transaction transaction = null;
        try(Session session = Hibernate.getFACTORY().openSession()){
            transaction = session.beginTransaction();

            session.update(ctsp);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean xoa(ChiTietSanPham ctsp){
        Transaction transaction = null;
        try(Session session = Hibernate.getFACTORY().openSession()){
            transaction = session.beginTransaction();

            session.delete(ctsp);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }
}
