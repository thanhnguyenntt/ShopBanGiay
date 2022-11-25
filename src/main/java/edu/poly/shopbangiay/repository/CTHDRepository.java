package edu.poly.shopbangiay.repository;

import edu.poly.shopbangiay.Ultilities.Hibernate;
import edu.poly.shopbangiay.model.ChiTietHoaDon;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class CTHDRepository {

    public List<ChiTietHoaDon> getList() {
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from ChiTietHoaDon ");
        return query.getResultList();
    }

    public List<ChiTietHoaDon> timKiem(String ma) {
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("from ChiTietHoaDon cthd where cthd.hoaDon.ma like: maHD or cthd.chiTietSanPham.sanPham.ma like: maSP");
        query.setParameter("maHD", "%" + ma + "%");
        query.setParameter("maSP", "%" + ma + "%");
        return query.getResultList();
    }

    public ChiTietHoaDon getCTHD(Integer idHD, Integer idCTSP) {
        ChiTietHoaDon cthd = null;
        try (Session session = Hibernate.getFACTORY().openSession()) {
            Query query = session.createQuery("select cthd from ChiTietHoaDon cthd where cthd.hoaDon.id =: idHD and cthd.chiTietSanPham.id =: idCTSP ");
            query.setParameter("idHD", idHD);
            query.setParameter("idCTSP", idCTSP);
            cthd = (ChiTietHoaDon) query.getSingleResult();
        } catch (Exception ignored) {
        }
        return cthd;
    }

    public List<ChiTietHoaDon> getCTHDByMaHD(String ma) {
        Session session = Hibernate.getFACTORY().openSession();
        Query query = session.createQuery("select cthd from ChiTietHoaDon cthd where cthd.hoaDon.ma = : ma");
        query.setParameter("ma", ma);
        return query.getResultList();
    }


    public Boolean them(ChiTietHoaDon cthd) {
        Transaction transaction = null;
        try (Session session = Hibernate.getFACTORY().openSession()) {
            transaction = session.beginTransaction();

            session.save(cthd);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean sua(ChiTietHoaDon cthd) {
        Transaction transaction = null;
        try (Session session = Hibernate.getFACTORY().openSession()) {
            transaction = session.beginTransaction();

            session.update(cthd);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public Boolean xoa(ChiTietHoaDon cthd) {
        Transaction transaction = null;
        try (Session session = Hibernate.getFACTORY().openSession()) {
            transaction = session.beginTransaction();

            session.delete(cthd);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }
}
