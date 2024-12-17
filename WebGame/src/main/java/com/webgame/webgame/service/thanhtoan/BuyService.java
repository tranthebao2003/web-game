package com.webgame.webgame.service.thanhtoan;

import com.webgame.webgame.model.*;
import com.webgame.webgame.repository.*;
import com.webgame.webgame.service.accountGame.AccountGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BuyService {
    @Autowired
    AccountGameRepository accountGameRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CartGameRepository cartGameRepository;

    public String buyInCart(Long userId, List<Long> idGames,BigDecimal tonggia) {
        if (idGames == null || idGames.isEmpty()) {
            return "Không có game nào được chọn";
        }

        // Tìm người dùng theo userId
        User user = userRepository.findUserById(userId);

        // Tạo một đơn hàng mới
        Orders order = new Orders();
        order.setUser(user);
        order.setPayAt(new Date());
        order.setSumPrice(tonggia); // Tổng tiền
        ordersRepository.save(order);


//        Lặp qua từng id game trong danh sách id game đã được chọn, (id game được chọn đó được gởi từ client vào)
        for (Long id : idGames) {
            AccountGame accountGame = accountGameRepository.timListAccountchuaban(id).get(0);
            accountGame.setStatus(true);
            accountGame.setOrder(order);
            accountGameRepository.save(accountGame);
            // Xóa game khỏi giỏ hàng theo gameId
            cartGameRepository.deleteGameIncart(id,userId);
        }

        return "Đã ghi vào cơ sở dữ liệu đơn hàng và xóa trong giỏ hàng";
    }



//    cái này dùng kiễu dữ liệu map để mà trả về kiểu như json (trả về nhiều giá trị cùng lúc) trả về tổng gía của những game và selectedgame
    public Map<String, Object> xacNhanDonHang(List<Long> idGames) {
        Map<String, Object> result = new HashMap<>();
        if (idGames == null || idGames.isEmpty()) {
            result.put("message", "Không có game nào được chọn");
            return result;
        }
        BigDecimal tongia = BigDecimal.ZERO;
        List<Game> selectedGames = new ArrayList<>();
        for (Long id : idGames) {
            Game game = gameRepository.findGameById(id);
            selectedGames.add(game);
            tongia = tongia.add(game.getPrice());
        }

        result.put("tongia", tongia);
        result.put("selectedGames", selectedGames);
        return result;
    }

}
