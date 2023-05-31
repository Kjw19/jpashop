package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1() {
            Member member = createMember("userA", "서울", "1", "1111");

            Book book = createBook("JPA1 BOOK", 10000, 100);

            Book book2 = createBook("JPA2 BOOK", 20000, 200);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 1);


            createDelivery(member, orderItem1, orderItem2);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            em.persist(member);
            return member;
        }

        public void dbInit2() {
            Member member = createMember("userB", "진주", "2", "2222");

            Book book = createBook("Spring1 BOOK", 20000, 200);

            Book book2 = createBook("Spring2 BOOK", 40000, 300);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 20000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 2);


            createDelivery(member, orderItem1, orderItem2);
        }

        private void createDelivery(Member member, OrderItem orderItem1, OrderItem orderItem2) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            em.persist(book);
            return book;
        }
    }
}
