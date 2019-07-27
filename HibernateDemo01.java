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
     * 保存两个用户三个联系人，并且建立关系
     * */
    public void demo01() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //创建两个客户
        Customer customer1 = new Customer();
        customer1.setCust_name( "customer1" );
        Customer customer2 = new Customer();
        customer2.setCust_name( "customer2" );
        //创建三个联系人
        LinkMan linkMan1 = new LinkMan();
        linkMan1.setLkm_name( "linkMan1" );
        LinkMan linkMan2 = new LinkMan();
        linkMan2.setLkm_name( "linkMan2" );
        LinkMan linkMan3 = new LinkMan();
        linkMan3.setLkm_name( "linkMan3" );

        //设置关系
        linkMan1.setCustomer( customer1 );
        linkMan2.setCustomer( customer1 );
        linkMan3.setCustomer( customer2 );
        customer1.getLinkMan().add( linkMan1 );
        customer1.getLinkMan().add( linkMan2 );
        customer2.getLinkMan().add( linkMan3 );

        //保存数据
        session.save( linkMan1 );
        session.save( linkMan2 );
        session.save( linkMan3 );
        session.save( customer1 );
        session.save( customer2 );


        transaction.commit();
    }

    @Test
    //一对多关系只保存一边是否可以
    public void demo02() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name( "孙龙" );

        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name( "书艺" );
        customer.getLinkMan().add( linkMan );
        linkMan.setCustomer( customer );


        //不可以只保存一边，会报一个瞬时态对象异常，持久态对象关联了一个瞬时态对象
        session.save( customer );
        // session.save( linkMan );

        transaction.commit();

    }

    @Test
    /*
     * 级联保存或更新
     * 保存客户级联联系人，操作的主体是客户对象，需要在Customer.hbm.xml 进行配置
     * <set name="linkMan" cascade = "save-update">
     * */
    public void demo03() {

        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name( "孙龙2" );

        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name( "书艺2" );

        customer.getLinkMan().add( linkMan );
        linkMan.setCustomer( customer );

        session.save( customer );
        transaction.commit();
    }

    @Test
    /*
     * 级联保存或更新
     * 保存联系人级联客户，操作的主体是联系人对象，需要在LinkMan.hbm.xml 进行配置
     * <set name="linkMan" cascade = "save-update">
     * */
    public void demo04() {

        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name( "孙龙3" );

        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name( "书艺3" );

        customer.getLinkMan().add( linkMan );
        linkMan.setCustomer( customer );

        session.save( linkMan );
        transaction.commit();
    }

    @Test
    //测试对象的导航
    public void demo05() {

        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name( "导航" );

        LinkMan linkMan1 = new LinkMan();
        linkMan1.setLkm_name( "凤姐" );
        LinkMan linkMan2 = new LinkMan();
        linkMan2.setLkm_name( "如花" );
        LinkMan linkMan3 = new LinkMan();
        linkMan3.setLkm_name( "芙蓉" );

        linkMan1.setCustomer( customer );
        customer.getLinkMan().add( linkMan2 );
        customer.getLinkMan().add( linkMan3 );

        //双方都设置了级联 cascade
        //发送 4 条inset 语句  在customer的一方放弃，在set上配置inverse=”true”，数据可以保存（cascade控制），但是linkMan2和3的外键值为null（inverse控制）
        session.save( linkMan1 );
        //session.save( customer );//发送 3条inset 语句
        //session.save( linkMan2 );//发送 1条inset 语句

        transaction.commit();
    }

    @Test
    /*级联删除：
        删除一边的时候，同时将另一方的数据也一并删除。
        删除客户级联删除联系人，删除的主体是客户，需要在Customer。hbm.xml中配置
        cascade = "delete"
*/
    public void demo06(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //删除客户，同时删除联系人，若不级联配置，由于外键约束 无法删除
        Customer customer = session.get( Customer.class,1L );
        session.delete( customer );

        transaction.commit();

    }


    @Test
    /*
    * 级联删除
    * 删除联系人级联删除客户，删除的主体是联系人，需要LinkMan.hbm.xml 中配置
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
    双向维护关系产生多余SQL

   将2号联系人原来归1号，现改为2号客户
    * */
    public void demo08(){

        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        LinkMan linkMan = session.get( LinkMan.class,2L );

        //若不在set上配置 inverse = “true”，会update 外键两次，产生多余SQL语句
        Customer customer = session.get( Customer.class,1L );
        linkMan.setCustomer( customer );
        customer.getLinkMan().add( linkMan );



        transaction.commit();
    }

}
