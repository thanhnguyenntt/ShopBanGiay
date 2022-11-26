package edu.poly.shopbangiay.repository;

import edu.poly.shopbangiay.Ultilities.Hibernate;
import edu.poly.shopbangiay.model.HoaDon;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class HoaDonRepository {

    Session session = Hibernate.getFACTORY().openSession();
    Transaction transaction = null;

    public List<HoaDon> getList() {
        Query query = session.createQuery("from HoaDon ");
        return query.getResultList();
    }

    public List<HoaDon> timKiem(String ma) {
        Query query = session.createQuery("from HoaDon where ma like: ma");
        query.setParameter("ma", "%" + ma + "%");
        return query.getResultList();
    }

    public HoaDon getHDByMa(String ma){
        try{
            Query query = session.createQuery("from HoaDon where ma=: ma");
            query.setParameter("ma", ma);
            return (HoaDon) query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public Boolean them(HoaDon hoaDon) {
        try{
            transaction = session.beginTransaction();

            session.save(hoaDon);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean sua(HoaDon hoaDon) {
        try{
            transaction = session.beginTransaction();

            session.update(hoaDon);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean xoa(HoaDon hoaDon) {
        try{
            transaction = session.beginTransaction();

            session.delete(hoaDon);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }
}
