package com.devproblems.service;

import com.devproblems.*;
import com.google.protobuf.Descriptors;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class BookAuthorClientService {
    @GrpcClient("grpc-devproblems-serviceA")
    BookAuthorServiceAGrpc.BookAuthorServiceABlockingStub synchrousClientA;

    @GrpcClient("grpc-devproblems-serviceB")
    BookAuthorServiceBGrpc.BookAuthorServiceBBlockingStub synchrousClientB;

    //BookAuthorServiceStub là class được tạo tự động khi gen code mà phía client application sử dụng để gửi yêu cầu đến server
    @GrpcClient("grpc-devproblems-serviceA")
    BookAuthorServiceAGrpc.BookAuthorServiceAStub asynchrousClientA;

    public Map<Descriptors.FieldDescriptor, Object> getAuthorId(int authorId) {
        AuthorIdRequest authorRequest = AuthorIdRequest.newBuilder().setAuthorId(authorId).build();
        Author authorResponse = synchrousClientB.getAuthorId(authorRequest);
        return authorResponse.getAllFields();
    }

    public Map<Descriptors.FieldDescriptor, Object> getAuthor(int authorId) {
        Author authorRequest = Author.newBuilder().setAuthorId(authorId).build();
        Author authorResponse = synchrousClientA.getAuthor(authorRequest);
        return authorResponse.getAllFields();
    }

    public List<Map<Descriptors.FieldDescriptor, Object>> getBooksByAuthor(int authorId) throws InterruptedException {
        //CountDownLatch chuyên dùng để đếm vì các luồng đang được xử lý song song
        final CountDownLatch countDownLatch = new CountDownLatch(1);//cho count ban đầu=1
        Author authorRequest = Author.newBuilder().setAuthorId(authorId).build();
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();
        asynchrousClientA.getBooksByAuthor(authorRequest, new StreamObserver<Book>() {
            @Override
            public void onNext(Book book) {
                response.add(book.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                //để giảm dần giá trị của count
                countDownLatch.countDown();
            }
        });
        //phương thức await() sẽ đợt tất cả các tiến trình xử lý của CountDownLatch hoàn thành
        //phương thức await() => đảm bảo thứ tự thực hiện các luồng,
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();//Collections.emptyList()=> Collections không bao giờ để nó bị null
    }

    public Map<String, Map<Descriptors.FieldDescriptor, Object>> getExpesiveBook() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Map<String, Map<Descriptors.FieldDescriptor, Object>> response = new HashMap<>();
        StreamObserver<Book> responseObserver = asynchrousClientA.getExpensiveBook(new StreamObserver<Book>() {
            @Override
            public void onNext(Book book) {
                response.put("ExpensiveBook", book.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        TempDb.getBooksFromTempDb().forEach(responseObserver::onNext);
        responseObserver.onCompleted();
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyMap();//khởi tạo một map trống
    }

    public List<Map<Descriptors.FieldDescriptor, Object>> getBooksByAuthorGender(String gender) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();
        StreamObserver<Book> responseObserver = asynchrousClientA.getBookByAuthorGender(new StreamObserver<Book>() {
            @Override
            public void onNext(Book book) {
                response.add(book.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });

        TempDb.getAuthorsFromTempDb()
                .stream()
                .filter(author -> author.getGender().equalsIgnoreCase(gender))
                .forEach(author -> responseObserver.onNext(Book.newBuilder().setAuthorId(author.getAuthorId()).build()));
        responseObserver.onCompleted();
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();//khởi tạo một danh sách trống
    }
}
