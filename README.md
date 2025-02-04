Lombok: Thư viện giúp giảm boilerplate code (các đoạn mã lặp lại) trong Java. Với Lombok, bạn có thể tạo getter, setter, constructor, và các phương thức khác bằng cách sử dụng các annotation như @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor, @Builder

Spring Boot DevTools: Bộ công cụ hỗ trợ phát triển, giúp tăng tốc quá trình phát triển ứng dụng Spring Boot. DevTools tự động reload ứng dụng khi có thay đổi code, hỗ trợ LiveReload và cung cấp thêm một số tính năng giúp cải thiện trải nghiệm phát triển.

Spring Web: Cung cấp các thành phần và công cụ để phát triển các ứng dụng web, đặc biệt là các ứng dụng RESTful API. Dependency này bao gồm Spring MVC và các tính năng HTTP, Servlet, v.v., giúp xây dựng API và giao diện web dễ dàng. Bao gồm cả Tomcat.

Spring Data JPA: Framework giúp dễ dàng thao tác với cơ sở dữ liệu qua JPA (Java Persistence API). Nó cung cấp các repository để tương tác với cơ sở dữ liệu mà không cần viết nhiều code SQL, hỗ trợ tự động hoá các thao tác CRUD, đồng thời dễ dàng tích hợp với Hibernate (JPA provider phổ biến).

Thymeleaf: Template engine giúp tạo giao diện web. Thymeleaf hỗ trợ kết hợp với Spring MVC để render HTML với dữ liệu từ controller, giúp xây dựng giao diện động một cách hiệu quả và dễ dàng.

MySQL Driver: Driver JDBC cho MySQL, giúp kết nối ứng dụng Spring Boot với cơ sở dữ liệu MySQL. Dependency này cần thiết để Spring Data JPA có thể làm việc với cơ sở dữ liệu MySQL.

CÁC BƯỚC UPLOAD CODE LÊN GITHUB

B1: Bắt đầu từ nhánh cá nhân sau khi có code mới thì đẩy code từ nhánh cá nhân ở local lên github

	+ git add .
	+ git commit -m 'content changed'
	+ git push origin tenNhanhCaNhan
	
B2: Compare pull request từ nhánh cá nhân qua nhanh dev trên github, sau đó merge confirm.
lúc này nhánh dev trên github đã có code mới.

B3: Từ nhánh các nhân chuyển qua nhánh dev trên local

	+git checkout dev
	
B4: Pull nhánh dev từ github về nhánh dev trên local

	+ git pull origin dev
lúc này nhánh dev local của bạn có code mới của bạn và những người khác

B5: Từ nhánh dev trên local chuyển qua nhánh cá nhân trên local

	+ git checkout tenNhanhCaNhan

B6: Merge code mới từ nhánh dev vào nhánh cá nhân

	+ git merge dev
