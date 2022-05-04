package com.devproblems;


import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
//Class BookAuthorServiceImplBase là abstract class được tạo tự động khi gen code cần được phía Server implements
@GrpcService
public class BookAuthorServerService extends BookAuthorServiceAGrpc.BookAuthorServiceAImplBase {

    //responseObserver Server tính tổng yêu cầu Client gửi đến và response lại
    //onNext dùng để trả dữ liệu cho client
    //onCompleted thông báo cho client server đã hoàn  thành nhiệm vụ

    @Override
    public void getAuthor(Author request, StreamObserver<Author> responseObserver) {
        TempDb.getAuthorsFromTempDb()
                .stream()
                .filter(author -> author.getAuthorId() == request.getAuthorId())
                .findFirst()//Trả về phần tử đầu tiên của stream.
                .ifPresent(responseObserver::onNext);
        responseObserver.onCompleted();// ifPresent để kiểm tra một đối tượng Optional có rỗng hay không
    }

    @Override
    public void getBooksByAuthor(Author request, StreamObserver<Book> responseObserver) {
        TempDb.getBooksFromTempDb()
                .stream()
                .filter(book -> book.getAuthorId() == request.getAuthorId())
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();//thông báo cho Server rằng nó đã gửi hết request
    }

    @Override
    public StreamObserver<Book> getExpensiveBook(StreamObserver<Book> responseObserver) {
        return new StreamObserver<Book>() {
            Book expensiveBook = null;
            float priceTrack = 0;

            @Override
            public void onNext(Book book) {
                if (book.getPrice() > priceTrack) {
                    priceTrack = book.getPrice();
                    expensiveBook = book;
                }
            }
           // onError để thông báo cho clien
            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(expensiveBook);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<Book> getBookByAuthorGender(StreamObserver<Book> responseObserver) {
        return new StreamObserver<Book>() {
            List<Book> bookList = new ArrayList<>();

            @Override
            public void onNext(Book book) {
                TempDb.getBooksFromTempDb()
                        .stream()
                        .filter(booksFromDb -> book.getAuthorId() == booksFromDb.getAuthorId())
                        .forEach(bookList::add);
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                bookList.forEach(responseObserver::onNext);
                responseObserver.onCompleted();
            }
        };
    }

}
