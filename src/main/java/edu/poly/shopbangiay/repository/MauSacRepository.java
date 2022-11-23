package edu.poly.shopbangiay.repository;


import edu.poly.shopbangiay.Ultilities.Hibernate;
import edu.poly.shopbangiay.model.MauSac;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class MauSacRepository {

    public List<MauSac> getList() {
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from MauSac ");
        return query.getResultList();
    }

    public List<MauSac> timKiem(String ten) {
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from MauSac where ten like: ten");
        query.setParameter("ten", "%" + ten + "%");
        return query.getResultList();
    }

    public MauSac getMSByMa(String ma) {
        try (Session session = Hibernate.getFACTORY().openSession()) {
            Query query = session.createQuery("from MauSac where ma =: ma");
            query.setParameter("ma", ma);
            return (MauSac) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean them(MauSac mauSac) {
        Transaction transaction = null;
        try (Session session = Hibernate.getFACTORY().openSession()) {
            transaction = session.beginTransaction();

            session.save(mauSac);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean sua(MauSac mauSac) {
        Transaction transaction = null;
        try (Session session = Hibernate.getFACTORY().openSession()) {
            transaction = session.beginTransaction();

            session.update(mauSac);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean xoa(MauSac mauSac) {
        Transaction transaction = null;
        try (Session session = Hibernate.getFACTORY().openSession()) {
            transaction = session.beginTransaction();

            session.delete(mauSac);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }
}
