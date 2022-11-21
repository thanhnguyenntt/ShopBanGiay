package edu.poly.shopbangiay.repository;

import edu.poly.shopbangiay.Ultilities.Hibernate;
import edu.poly.shopbangiay.model.ChatLieu;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class ChatLieuRepository {
    Session session = Hibernate.getFACTORY().openSession();
    Transaction transaction;

    public List<ChatLieu> getList(){
        Query query = session.createQuery("from ChatLieu ");
        return query.getResultList();
    }

    public List<ChatLieu> timKiem(String ten){
        Query query = session.createQuery("from ChatLieu where ten like: ten");
        query.setParameter("ten", "%" + ten + "%");
        return query.getResultList();
    }

    public Boolean them(ChatLieu chatLieu){
        try{
            transaction = session.beginTransaction();

            session.save(chatLieu);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean sua(ChatLieu chatLieu){
        try{
            transaction = session.beginTransaction();

            session.update(chatLieu);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean xoa(ChatLieu chatLieu){
        try{
            transaction = session.beginTransaction();

            session.delete(chatLieu);

            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }
}

