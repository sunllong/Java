package com.sunlong.hibernate03.demo;


import com.sunlong.hibernate01.domain.Custom;
import com.sunlong.hibernate03.domain.Customer;
import com.sunlong.hibernate03.domain.LinkMan;
import com.sunlong.hibernate03.utils.HibernateUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import sun.security.ssl.HandshakeInStream;

/**
 * @Auther: SolarL
 * @Date: 2019/7/27
 * @Description: com.sunlong.hibernate03.demo
 * @version: 1.0
 */
public class HibernateDemo01 {

    @Test
    /*
     * ���������û�������ϵ�ˣ����ҽ�����ϵ
     * */
    public void demo01() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //���������ͻ�
        Customer customer1 = new Customer();
        customer1.setCust_name( "customer1" );
        Customer customer2 = new Customer();
        customer2.setCust_name( "customer2" );
        //����������ϵ��
        LinkMan linkMan1 = new LinkMan();
        linkMan1.setLkm_name( "linkMan1" );
        LinkMan linkMan2 = new LinkMan();
        linkMan2.setLkm_name( "linkMan2" );
        LinkMan linkMan3 = new LinkMan();
        linkMan3.setLkm_name( "linkMan3" );

        //���ù�ϵ
        linkMan1.setCustomer( customer1 );
        linkMan2.setCustomer( customer1 );
        linkMan3.setCustomer( customer2 );
        customer1.getLinkMan().add( linkMan1 );
        customer1.getLinkMan().add( linkMan2 );
        customer2.getLinkMan().add( linkMan3 );

        //��������
        session.save( linkMan1 );
        session.save( linkMan2 );
        session.save( linkMan3 );
        session.save( customer1 );
        session.save( customer2 );


        transaction.commit();
    }

    @Test
    //һ�Զ��ϵֻ����һ���Ƿ����
    public void demo02() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name( "����" );

        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name( "����" );
        customer.getLinkMan().add( linkMan );
        linkMan.setCustomer( customer );


        //������ֻ����һ�ߣ��ᱨһ��˲ʱ̬�����쳣���־�̬���������һ��˲ʱ̬����
        session.save( customer );
        // session.save( linkMan );

        transaction.commit();

    }

    @Test
    /*
     * ������������
     * ����ͻ�������ϵ�ˣ������������ǿͻ�������Ҫ��Customer.hbm.xml ��������
     * <set name="linkMan" cascade = "save-update">
     * */
    public void demo03() {

        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name( "����2" );

        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name( "����2" );

        customer.getLinkMan().add( linkMan );
        linkMan.setCustomer( customer );

        session.save( customer );
        transaction.commit();
    }

    @Test
    /*
     * ������������
     * ������ϵ�˼����ͻ�����������������ϵ�˶�����Ҫ��LinkMan.hbm.xml ��������
     * <set name="linkMan" cascade = "save-update">
     * */
    public void demo04() {

        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name( "����3" );

        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name( "����3" );

        customer.getLinkMan().add( linkMan );
        linkMan.setCustomer( customer );

        session.save( linkMan );
        transaction.commit();
    }

    @Test
    //���Զ���ĵ���
    public void demo05() {

        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name( "����" );

        LinkMan linkMan1 = new LinkMan();
        linkMan1.setLkm_name( "���" );
        LinkMan linkMan2 = new LinkMan();
        linkMan2.setLkm_name( "�绨" );
        LinkMan linkMan3 = new LinkMan();
        linkMan3.setLkm_name( "ܽ��" );

        linkMan1.setCustomer( customer );
        customer.getLinkMan().add( linkMan2 );
        customer.getLinkMan().add( linkMan3 );

        //˫���������˼��� cascade
        //���� 4 ��inset ���  ��customer��һ����������set������inverse=��true�������ݿ��Ա��棨cascade���ƣ�������linkMan2��3�����ֵΪnull��inverse���ƣ�
        session.save( linkMan1 );
        //session.save( customer );//���� 3��inset ���
        //session.save( linkMan2 );//���� 1��inset ���

        transaction.commit();
    }

    @Test
    /*����ɾ����
        ɾ��һ�ߵ�ʱ��ͬʱ����һ��������Ҳһ��ɾ����
        ɾ���ͻ�����ɾ����ϵ�ˣ�ɾ���������ǿͻ�����Ҫ��Customer��hbm.xml������
        cascade = "delete"
*/
    public void demo06(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //ɾ���ͻ���ͬʱɾ����ϵ�ˣ������������ã��������Լ�� �޷�ɾ��
        Customer customer = session.get( Customer.class,1L );
        session.delete( customer );

        transaction.commit();

    }


    @Test
    /*
    * ����ɾ��
    * ɾ����ϵ�˼���ɾ���ͻ���ɾ������������ϵ�ˣ���ҪLinkMan.hbm.xml ������
    *  <many-to-one cascade="save-update,delete"
     * */
    public void demo07(){

        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        LinkMan linkMan = session.get( LinkMan.class,3L );
        session.delete( linkMan );

        transaction.commit();
    }

    @Test
    /*
    ˫��ά����ϵ��������SQL

   ��2����ϵ��ԭ����1�ţ��ָ�Ϊ2�ſͻ�
    * */
    public void demo08(){

        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        LinkMan linkMan = session.get( LinkMan.class,2L );

        //������set������ inverse = ��true������update ������Σ���������SQL���
        Customer customer = session.get( Customer.class,1L );
        linkMan.setCustomer( customer );
        customer.getLinkMan().add( linkMan );



        transaction.commit();
    }

}
