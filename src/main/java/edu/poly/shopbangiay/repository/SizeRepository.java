package edu.poly.shopbangiay.repository;

import edu.poly.shopbangiay.Ultilities.Hibernate;
import edu.poly.shopbangiay.model.Size;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class SizeRepository {

    public List<Size> getList(){
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from Size ");
        return query.getResultList();
    }

    public List<Size> timKiem(Integer soSize){
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from Size where soSize =: soSize");
        query.setParameter("soSize", soSize);
        return query.getResultList();
    }

    public Boolean them(Size size){
        Transaction transaction = null;
        try(Session session = Hibernate.getFACTORY().openSession()){
            transaction = session.beginTransaction();

            session.save(size);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean sua(Size size){
        Transaction transaction = null;
        try(Session session = Hibernate.getFACTORY().openSession()){
            transaction = session.beginTransaction();

            session.update(size);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean xoa(Size size){
        Transaction transaction = null;
        try(Session session = Hibernate.getFACTORY().openSession()){
            transaction = session.beginTransaction();

            session.delete(size);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }
}
