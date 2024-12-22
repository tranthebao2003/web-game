package com.webgame.webgame.controller;

import com.webgame.webgame.dto.OrderDetailsAdmin;
import com.webgame.webgame.dto.gameDto.GameFormDto;
import com.webgame.webgame.model.*;
import com.webgame.webgame.repository.OrderRepository;
import com.webgame.webgame.repository.UserRepository;
import com.webgame.webgame.service.admin.ListOrderService;
import com.webgame.webgame.service.admin.ListUserService;
import com.webgame.webgame.service.category.CategoryService;
import com.webgame.webgame.service.game.GameService;
import com.webgame.webgame.service.user.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    GameService gameService;

    @Autowired
    CategoryService categoryService;

    // này đơn giản khỏi cần service dùng thẳng repository
    @Autowired
    UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;


    @GetMapping("/admin")
    public String admin(Model model) {
        return findPaginated(1, "create_date", "dsc", model);
    }

    //    /page/1?sortField=name&sortDir=asc
//   Cụ thể dòng comment trên PathVariable là để lấy 1 còn
//    RequestParam là để lấy giá trị của 2 trường còn lại
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 8;

        Page<Object[]> page = gameService.findGamesAndQuantityCategory(pageNo, pageSize, sortField, sortDir);
        List<Object[]> listGame = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
//       đảo ngược cách sắp xếp: tăng dần thành giảm dần và ngược lại
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listGame", listGame);

        // Diệu Linh viết
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ Authentication

        // Tìm uer từ email
        User user = userRepository.findByEmail(email);

        // check kĩ tránh lỗi, không có cũng được
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        model.addAttribute("admin", user);

        return "admin/admin";
    }

    // show ra form để add game
    @GetMapping("/addGameForm")
    public String showNewGameForm(Model model) {

//        Tạo model để buộc dữ liệu vào form add game
        GameFormDto newGame = new GameFormDto();
        model.addAttribute("newGame", newGame);

        List<Category> categoryList = categoryService.getAllCategoryList();
        model.addAttribute("categoryList", categoryList);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ Authentication

        // Tìm uer từ email
        User user = userRepository.findByEmail(email);

        // check kĩ tránh lỗi, không có cũng được
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        model.addAttribute("admin", user);

        return "admin/new-game";
    }

    @PostMapping("/saveGame")
//    @ModelAttribute("newGame") GameFormDto gameFormDto: mình lấy newGame từ ModelAttribute
//    rồi gán cho biến gameFormDto
    public String saveGame(@ModelAttribute("newGame") GameFormDto gameFormDto,
                               RedirectAttributes redirectAttributes) {
        try {
            gameService.saveGame(gameFormDto); // Lưu game vào database
            redirectAttributes.addFlashAttribute("success", "Game đã được thêm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Thêm game thất bại!");
        }
        // Redirect thường được sử dụng trong các trường hợp POST/REDIRECT/GET
        // để ngăn người dùng gửi lại biểu mẫu khi tải lại trang tránh vấn
        // đề duplicate form
        return "redirect:/addGameForm";
    }

    @GetMapping("/deleteGame/{id}")
    public String deleteGame(@PathVariable(value = "id") Long id) {
//        call delete game method
        gameService.deleteGameById(id);
        return "redirect:/admin";
    }


    // tương tự như save game thì giờ mình làm ngược lại mình sẽ
//    tìm game cần update theo id sau đó gán nó ngược lại cho
//    cho GameFromDto để nhận giữ liệu mới và cuối cùng lưu lại
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
//        get game from the service by id
        Game gameExist = gameService.getGameById(id);

        // Chuyển đổi Game thành GameFormDto để dễ xử lý trong form
        GameFormDto gameFormDto = new GameFormDto();
        gameFormDto.setGameName(gameExist.getGameName());
        gameFormDto.setDescription(gameExist.getDescription());
        gameFormDto.setPrice(gameExist.getPrice());

        // nhận danh sách category theo gameId
        List<Category> categoryListGameExist = gameService.findCategoriesByGameId(id);

        // Lấy danh sách ID của các thể loại (dùng for loop)
        List<Long> categoryIds = new ArrayList<>();
        for (Category category : categoryListGameExist) {
            categoryIds.add(category.getCategoryId());
        }
        gameFormDto.setCategoryIds(categoryIds);

        // show ra data cần update cho user
        List<Category> categoryList = categoryService.getAllCategoryList();
        model.addAttribute("categoryListGameExist", categoryListGameExist);
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("gameFormDto", gameFormDto);
        model.addAttribute("gameExistId", id);

        // trả về admin với role có tên là admin
        //model.addAttribute("admin", userRepository.findByRole("admin"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ Authentication

        // Tìm uer từ email
        User user = userRepository.findByEmail(email);

        // check kĩ tránh lỗi, không có cũng được
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        model.addAttribute("admin", user);

        return "admin/update-game";
    }

    @PostMapping("/updateGame/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id,
                                    @ModelAttribute("gameFormDto") GameFormDto gameFormDto,
                                    RedirectAttributes redirectAttributes) {
        try {
            gameService.updateGame(id, gameFormDto);
            redirectAttributes.addFlashAttribute("success", "Game đã được cập nhật thành công !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cập nhật game thất bại !");
        }
        return "redirect:/admin";
    }

    // Toàn bộ phần dưới là của Diệu Linh Viết
    @Autowired
    private ListUserService listUserService;

    @GetMapping("listUsers")
    public String listUsers(Model model) {
        List<User> users = listUserService.getAllUsers();
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ Authentication

        // Tìm uer từ email
        User user = userRepository.findByEmail(email);

        // check kĩ tránh lỗi, không có cũng được
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        model.addAttribute("admin", user);
        return "admin/listUser";
    }

    @GetMapping("findUserByRole")
    public String findUserByRole(@RequestParam("role") String role,Model model) {
        List<User> users = listUserService.getUsersByRoleId(role);
        model.addAttribute("users", users);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ Authentication

        // Tìm uer từ email
        User user = userRepository.findByEmail(email);

        // check kĩ tránh lỗi, không có cũng được
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        model.addAttribute("admin", user);
        return "admin/listUser";
    }


    @Autowired
    private UserServiceImp userServiceImp;
    @GetMapping("findUserById")
    public String findUserById(@RequestParam("id") Long id,Model model) {
        User users = userServiceImp.getUserById(id);
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ Authentication

        // Tìm uer từ email
        User user = userRepository.findByEmail(email);

        // check kĩ tránh lỗi, không có cũng được
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        model.addAttribute("admin", user);
        return "admin/listUser";
    }

    @GetMapping("addAdmin")
    public String addAdmin(@RequestParam("id") Long id,Model model) {
        User users = userServiceImp.getUserById(id);
        if (users != null) {
            // Cập nhật role thành "admin"
            users.setRole("admin");

            // Lưu lại thay đổi vào database
            userRepository.save(users);
        }
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ Authentication

        // Tìm uer từ email
        User user = userRepository.findByEmail(email);

        // check kĩ tránh lỗi, không có cũng được
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        model.addAttribute("admin", user);
        return "admin/listUser";
    }

    @GetMapping("listOrders")
    public String listOrders(Model model) {
        // Lấy danh sách đơn hàng từ repository
        List<Orders> orders = orderRepository.findAll();

        // Tạo danh sách DTO chứa thông tin cần thiết
        List<OrderDetailsAdmin> orderDetailsList = new ArrayList<>();
        for (Orders order : orders) {
            for (AccountGame accountGame : order.getAccountGames()) {
                // Tạo DTO để trả về thông tin cần thiết
                OrderDetailsAdmin details = new OrderDetailsAdmin();
                details.setOrderId(order.getOrderId()); // Gán orderId
                details.setUserId(order.getUser().getUserId());
                details.setFullName(order.getUser().getFullName());// Gán userId từ user liên kết
                details.setGameName(accountGame.getGame().getGameName()); // Lấy tên game từ AccountGame
                details.setUsername(accountGame.getUsername()); // Lấy username từ AccountGame
                details.setPassword(accountGame.getPassword()); // Lấy password từ AccountGame
                details.setPrice(accountGame.getGame().getPrice()); // Lấy giá từ Game
                orderDetailsList.add(details);
            }
        }

        // Đưa danh sách vào model để hiển thị trong view
        model.addAttribute("orderDetailsList", orderDetailsList);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ Authentication

        // Tìm uer từ email
        User user = userRepository.findByEmail(email);

        // check kĩ tránh lỗi, không có cũng được
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        model.addAttribute("admin", user);

        return "admin/listOrder"; // Trả về tên view (file Thymeleaf)
    }

    @Autowired
    ListOrderService listOrderService;
    @GetMapping("findOrderByUserId")
    public String findOrderByUserId(@RequestParam("id") Long id,Model model) {

        List<Orders> orders = listOrderService.getOrderByUserId(id);

        List<OrderDetailsAdmin> orderDetailsList = new ArrayList<>();
        for (Orders order : orders) {
            for (AccountGame accountGame : order.getAccountGames()) {

                OrderDetailsAdmin details = new OrderDetailsAdmin();
                details.setOrderId(order.getOrderId()); // Gán orderId
                details.setUserId(order.getUser().getUserId());
                details.setFullName(order.getUser().getFullName());// Gán userId từ user liên kết
                details.setGameName(accountGame.getGame().getGameName()); // Lấy tên game từ AccountGame
                details.setUsername(accountGame.getUsername()); // Lấy username từ AccountGame
                details.setPassword(accountGame.getPassword()); // Lấy password từ AccountGame
                details.setPrice(accountGame.getGame().getPrice()); // Lấy giá từ Game
                orderDetailsList.add(details);
            }
        }


        model.addAttribute("orderDetailsList", orderDetailsList);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ Authentication

        // Tìm uer từ email
        User user = userRepository.findByEmail(email);

        // check kĩ tránh lỗi, không có cũng được
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        model.addAttribute("admin", user);

        return "admin/listOrder"; // Trả về tên view (file Thymeleaf)
    }



}
