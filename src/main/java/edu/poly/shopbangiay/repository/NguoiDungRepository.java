package edu.poly.shopbangiay.repository;

import edu.poly.shopbangiay.Ultilities.Hibernate;
import edu.poly.shopbangiay.model.NguoiDung;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class NguoiDungRepository {



    public List<NguoiDung> getList(){
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from NguoiDung ");
        return query.getResultList();
    }

    public List<NguoiDung> timKiem(String ten){
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from NguoiDung where ten like: ten");
        query.setParameter("ten", "%" + ten + "%");
        return query.getResultList();
    }

    public NguoiDung getNDByMa(String ma){
        try(Session session = Hibernate.getFACTORY().openSession()){
            Query query = session.createQuery("from NguoiDung where ma =: ma");
            query.setParameter("ma", ma);
            return (NguoiDung) query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public Boolean them(NguoiDung nguoiDung){
        Transaction transaction = null;
        try(Session session = Hibernate.getFACTORY().openSession()){
            transaction = session.beginTransaction();

            session.save(nguoiDung);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean sua(NguoiDung nguoiDung){
        Transaction transaction = null;
        try(Session session = Hibernate.getFACTORY().openSession()){
            transaction = session.beginTransaction();

            session.update(nguoiDung);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean xoa(NguoiDung nguoiDung){
        Transaction transaction = null;
        try(Session session = Hibernate.getFACTORY().openSession()){
            transaction = session.beginTransaction();

            session.delete(nguoiDung);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }
}
