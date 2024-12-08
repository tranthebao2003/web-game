package com.webgame.webgame.controller;

import com.webgame.webgame.dto.gameDto.GameFormDto;
import com.webgame.webgame.model.Category;
import com.webgame.webgame.model.Game;
import com.webgame.webgame.repository.UserRepository;
import com.webgame.webgame.service.category.CategoryService;
import com.webgame.webgame.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

        model.addAttribute("admin", userRepository.findByRole("admin"));

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

        // trả về admin với role có tên là admin
        model.addAttribute("admin", userRepository.findByRole("admin"));
        return "admin/new-game";
    }

    @PostMapping("/saveGame")
//    @ModelAttribute("employee") Employee employee: mình lấy employee từ ModelAttribute
//    rồi gán cho biến employee
    public String saveEmployee(@ModelAttribute("newGame") GameFormDto gameFormDto,
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
    public String deleteEmployee(@PathVariable(value = "id") Long id) {
//        call delete employee method
        gameService.deleteGameById(id);
        return "redirect:/admin";
    }


    // tương tự như save game thì giờ mình làm ngược lại mình sẽ
//    tìm game cần update theo id sau đó gán nó ngược lại cho
//    cho GameFromDto để nhận giữ liệu mới và cuối cùng lưu lại
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
//        get employee from the service by id
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
        model.addAttribute("admin", userRepository.findByRole("admin"));
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

}
