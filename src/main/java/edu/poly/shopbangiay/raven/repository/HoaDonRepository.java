package edu.poly.shopbangiay.raven.repository;

import edu.poly.shopbangiay.Ultilities.Hibernate;
import edu.poly.shopbangiay.model.HoaDon;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class HoaDonRepository {

    Session session = Hibernate.getFACTORY().openSession();
    Transaction transaction = null;

    public List<HoaDon> getList() {
        Query query = session.createQuery("from HoaDon order by ngayTao desc");
        return query.getResultList();
    }

    public List<HoaDon> timKiem(String ma) {
        Query query = session.createQuery("from HoaDon where ma like: ma order by ngayTao desc");
        query.setParameter("ma", "%" + ma + "%");
        return query.getResultList();
    }

    public List<HoaDon> locTT(Integer tt) {
        Query query = session.createQuery("from HoaDon where tinhTrang=: tt order by ngayTao desc");
        query.setParameter("tt", tt);
        return query.getResultList();
    }

    public HoaDon getHDByMa(String ma) {
        try {
            Query query = session.createQuery("from HoaDon where ma=: ma order by ngayTao desc");
            query.setParameter("ma", ma);
            return (HoaDon) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Double doanhThuNgay(Date date) {
        Query query = session.createQuery("select sum(thanhTien) from HoaDon where ngayTT =: date");
        query.setParameter("date", date);
        return (Double) query.getSingleResult();
    }

//    public static void main(String[] args) {
//        HoaDonRepository hoaDonRepository = new HoaDonRepository();
//        System.out.println(LocalDate.now());
//        System.out.println(hoaDonRepository.doanhThu(Date.valueOf(LocalDate.now().getYear())));
//    }

    public Boolean them(HoaDon hoaDon) {
        try {
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
        try {
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
        try {
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
