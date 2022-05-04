package devproblems;


import com.devproblems.*;
import devproblems.service.service.AuthorService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

//Class BookAuthorServiceImplBase là abstract class được tạo tự động khi gen code cần được phía Server implements
@GrpcService
public class BookAuthorServerServiceB extends BookAuthorServiceBGrpc.BookAuthorServiceBImplBase {

    //responseObserver Server tính tổng yêu cầu Client gửi đến và response lại
    //onNext dùng để trả dữ liệu cho client
    //onCompleted thông báo cho client server đã hoàn  thành nhiệm vụ
    @Autowired
    AuthorService authorService;


    @Override
    public void getAuthorId(AuthorIdRequest request, StreamObserver<Author> responseObserver) {
//        TempDb.getAuthorsFromTempDb()
//                .stream()
//                .filter(author -> author.getAuthorId() == request.getAuthorId())
//                .findFirst()//Trả về phần tử đầu tiên của stream.
//                .ifPresent(responseObserver::onNext);// ifPresent để kiểm tra một đối tượng Optional có rỗng hay không
//        responseObserver.onCompleted();
        devproblems.entity.Author author = authorService.findAllByAuthor_id(request.getAuthorId());
        Author authors = Author.newBuilder()
                .setAuthorId(author.getAuthor_id())
                .setFirstName(author.getFirst_name())
                .setLastName(author.getLast_name())
                .setGender(author.getGender())
                .setBookId(1)
                .build();
        responseObserver.onNext(authors);
        responseObserver.onCompleted();
    }

}
