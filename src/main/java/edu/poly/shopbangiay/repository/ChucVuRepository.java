package edu.poly.shopbangiay.repository;

import edu.poly.shopbangiay.Ultilities.Hibernate;
import edu.poly.shopbangiay.model.ChatLieu;
import edu.poly.shopbangiay.model.ChucVu;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class ChucVuRepository {


    public List<ChucVu> getList(){
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from ChucVu ");
        return query.getResultList();
    }

    public List<ChucVu> timKiem(String ten){
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from ChucVu where ten like: ten");
        query.setParameter("ten", "%" + ten + "%");
        return query.getResultList();
    }

    public ChucVu getCVByMa(String ma){
        try (Session session = Hibernate.getFACTORY().openSession()){
            Query query = session.createQuery("from ChucVu where ma =: ma");
            query.setParameter("ma", ma);
            return (ChucVu) query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public Boolean them(ChucVu chucVu){
        Transaction transaction = null;
        try(Session session = Hibernate.getFACTORY().openSession()){
            transaction = session.beginTransaction();

            session.save(chucVu);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean sua(ChucVu chucVu){
        Transaction transaction = null;
        try(Session session = Hibernate.getFACTORY().openSession()){
            transaction = session.beginTransaction();

            session.update(chucVu);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean xoa(ChucVu chucVu){
        Transaction transaction = null;
        try(Session session = Hibernate.getFACTORY().openSession()){
            transaction = session.beginTransaction();

            session.delete(chucVu);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }
}
