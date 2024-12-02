package com.webgame.webgame.sevice.game;

import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.model.Category;
import com.webgame.webgame.model.Game;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GameService {
    // định nghĩa hàm getGameList nhận 3 param trả về kiểu Page<Game>
    // trả về 1 object page nhận 2 param là chỉ số
    // page và số lượng item của mỗi page
    // sortField: trường mình chọn để sắp xếp (ở đây sắp xếp theo
    // ngày tạo). Game có ngày tạo mới nhấn sẽ đc show lên trên
    // => sắp xếp giảm dần
    Page<Game> getGameList(int page, int size, String sortField);
    List<Game> getGameSearchInput(String searchInput);
    List<Game> getGameListCategory(Category category);
}
