package com.webgame.webgame.controller;

import com.webgame.webgame.model.Category;
import com.webgame.webgame.model.Game;
import com.webgame.webgame.service.accountGame.AccountGameService;
import com.webgame.webgame.service.category.CategoryService;
import com.webgame.webgame.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    GameService gameService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AccountGameService accountGameService;


    public boolean checkLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //lấy authentication

        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));

        return isLoggedIn;
    }

    //   /page/?page=1
    @GetMapping("/")
    public String findPaginated(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSale", defaultValue = "0") int pageSale,
            Model model
    ) {

        // số lượng mỗi game mỗi trang
        int size = 8;
        List<Game> gameListTmp = new ArrayList<>();

        List<Object[]> gameListTmp2 = new ArrayList<>();

        // vòng for này mình sẽ duyệt qua từ trang đầu đến
        // trang user đang ấn xem thêm, sau đó lưu vào gameListTmp
        // cuối cùng show toàn bộ danh sách ra
        for (int i = 0; i <= page; i++) {
            Page<Game> gamePage = gameService.getGameList(i, size, "createDate");
            gameListTmp.addAll(gamePage.getContent());
        }

        // cơ chế tương tự như ở trên
        for (int j = 0; j <= pageSale; j++) {
            Page<Object[]> totalAccountGameSold = accountGameService.totalAccountGameSold(j, size);
            gameListTmp2.addAll(totalAccountGameSold.getContent());
        }

        // Truyền dữ liệu vào model
        model.addAttribute("gameList", gameListTmp);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", gameService.getGameList(0, size, "createDate").getTotalPages());

        model.addAttribute("gameListSale", gameListTmp2);
        model.addAttribute("currentPageSale", pageSale);
        model.addAttribute("totalPageSale", accountGameService.totalAccountGameSold(0, size).getTotalPages());

        List<Category> categoryList = categoryService.getAllCategoryList();
        model.addAttribute("categoryList", categoryList);

        boolean isLoggedIn = checkLogin();

        model.addAttribute("checkLogin", isLoggedIn);

        return "home/home";
    }

    // searchInput chính là giá trị của thuộc tính name của thẻ input
    @GetMapping("/search/")
    public String searchGame(
            @RequestParam(value = "searchInput") String searchInput,
            Model model) {

        List<Game> gamePageSearch = gameService.getGameSearchInput(searchInput);

        // Truyền dữ liệu vào model
        model.addAttribute("gameListSearchResult", gamePageSearch);
        model.addAttribute("searchInput", searchInput);

        List<Category> categoryList = categoryService.getAllCategoryList();
        model.addAttribute("categoryList", categoryList);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //lấy authentication

        boolean isLoggedIn = checkLogin();

        model.addAttribute("checkLogin", isLoggedIn);
        return "home/searchResult";
    }

    @GetMapping("/category/{id}")
    public String getGamesByCategory(@PathVariable("id") Long categoryId,
                                     Model model) {
        // Lấy thể loại từ ID
        Category category = categoryService.getCategoryById(categoryId);

        // Lấy danh sách game theo thể loại
        List<Game> gamesListCategory = gameService.getGameListCategory(category);

        // Truyền dữ liệu vào model
        model.addAttribute("gamesListCategory", gamesListCategory);
        model.addAttribute("category", category);

        List<Category> categoryList = categoryService.getAllCategoryList();
        model.addAttribute("categoryList", categoryList);

        boolean isLoggedIn = checkLogin();
        model.addAttribute("checkLogin", isLoggedIn);

        return "home/gameListCategory"; // Trả về view hiển thị danh sách game
    }

}
