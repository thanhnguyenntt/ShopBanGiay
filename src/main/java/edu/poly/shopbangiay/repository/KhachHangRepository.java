package edu.poly.shopbangiay.repository;

import edu.poly.shopbangiay.Ultilities.Hibernate;
import edu.poly.shopbangiay.model.KhachHang;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class KhachHangRepository {


    public List<KhachHang> getList() {
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from KhachHang ");
        return query.getResultList();
    }

    public List<KhachHang> timKiem(String ten) {
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from KhachHang where ten like: ten");
        query.setParameter("ten", "%" + ten + "%");
        return query.getResultList();
    }

    public Boolean them(KhachHang khachHang) {
        Transaction transaction = null;
        try (Session session = Hibernate.getFACTORY().openSession()) {
            transaction = session.beginTransaction();

            session.save(khachHang);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean sua(KhachHang khachHang) {
        Transaction transaction = null;
        try (Session session = Hibernate.getFACTORY().openSession()) {
            transaction = session.beginTransaction();

            session.update(khachHang);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean xoa(KhachHang khachHang) {
        Transaction transaction = null;
        try (Session session = Hibernate.getFACTORY().openSession()) {
            transaction = session.beginTransaction();

            session.delete(khachHang);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }
}
