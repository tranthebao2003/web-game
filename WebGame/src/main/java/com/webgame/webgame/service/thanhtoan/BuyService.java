package com.webgame.webgame.service.thanhtoan;

import com.webgame.webgame.model.*;
import com.webgame.webgame.repository.*;
import com.webgame.webgame.service.accountGame.AccountGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

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

    public String buyNow(Long gameId, Long userId) {
        try {
            // Tìm người dùng
            User user = userRepository.findUserById(userId);

            // Tìm danh sach tai khoản game chưa bán
            List<AccountGame> listAccountGame = accountGameRepository.timListAccountchuaban(gameId);
            if (listAccountGame == null) {
                return "Game này đã hết trong kho";
            }
            AccountGame accountGame =listAccountGame.get(0);


            // Tạo đơn hàng
            Orders order = new Orders();
            order.setUser(user);
            order.setPayAt(new Date());
            order.setSumPrice(accountGame.getGame().getPrice());

            // Cập nhật trạng thái của tài khoản game
            accountGame.setStatus(true);
            accountGame.setOrder(order);

            // Lưu đơn hàng và tài khoản game đã cập nhật
            ordersRepository.save(order);
            accountGameRepository.save(accountGame);

            // Trả về thông báo thành công
            return "Đã ghi vào csdl";
        } catch (ResponseStatusException e) {
            // Xử lý lỗi từ ResponseStatusException và trả về thông báo lỗi
            return "Error: " + e.getReason();
        } catch (Exception e) {
            // Xử lý các lỗi không mong muốn khác và trả về thông báo lỗi chung
            return "An unexpected error occurred: " + e.getMessage();
        }
    }

    public String buyInCart(Long userId){

        User user = userRepository.findUserById(userId);

//        lấy danh sách đối tượng cartgame có userid=??
        List<CartGame> listGameInCart =cartGameRepository.findGamesInCartByUserId(userId);

        Orders order = new Orders();
        order.setUser(user);
        order.setPayAt(new Date());
        order.setSumPrice(cartGameRepository.calculateTotalPriceCartByUserId(userId));
        ordersRepository.save(order);
//        cho lập qua từng đối tượng game trong giỏ  hàng xong rồi ta từ đó lấy ra dược cái account thứ nhất trong sách đối tượng game đó
        for (CartGame cartGame : listGameInCart) {
            AccountGame accountGame=accountGameRepository.timListAccountchuaban(cartGame.getGame().getGameId()).get(0);
            accountGame.setStatus(true);
            accountGame.setOrder(order);
            accountGameRepository.save(accountGame);
        }

        cartGameRepository.deleteAllGamesInCartByUserId(userId);
        return "Đã ghi vào cơ sở dữ order và xóa trong giỏ hàng" ;
    }

}
