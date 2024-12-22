package com.webgame.webgame.service.accountGame;
import com.webgame.webgame.dto.AccountGameDto;
import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.model.Game;
import com.webgame.webgame.repository.AccountGameRepository;
import com.webgame.webgame.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AccountGameServiceImp implements AccountGameService {

    @Autowired
    private AccountGameRepository accountGameRepository;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Page<Object[]> totalAccountGameSold(int page, int size) {
        return accountGameRepository.findTopSellingGames(PageRequest.of(page, size));
    }

    @Override
    public List<AccountGame> listAccountByGameId(Long gameId) {
        return accountGameRepository.accountGameByGameId(gameId);
    }

    public AccountGameDto getAccountById(Long id) {
        AccountGame accountGame = accountGameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account không tồn tại"));
        AccountGameDto accountDto = new AccountGameDto();
        accountDto.setGameId(accountGame.getAccountGameId());
        accountDto.setGameId(accountGame.getGame().getGameId());
        accountDto.setUsername(accountGame.getUsername());
        accountDto.setPassword(accountGame.getPassword());
        return accountDto;
    }


    public void updateAccount(Long id, AccountGameDto accountGameDto) {
        // Kiểm tra xem gameId trong DTO có hợp lệ không
        if (accountGameDto.getGameId() == null) {
            throw new IllegalArgumentException("Game ID must not be null");
        }

        AccountGame accountGame = accountGameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account không tồn tại"));

//         Nạp thực thể Game từ repository bằng gameId
        Game game = gameRepository.findById(accountGameDto.getGameId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Game ID: " + accountGameDto.getGameId()));


        accountGame.setGame(game);
        accountGame.setUsername(accountGameDto.getUsername());
        accountGame.setPassword(accountGameDto.getPassword());

        accountGameRepository.save(accountGame);
    }

    @Override
    public void deleteAccountGameById(Long id) {
        System.out.println("Đang thực hiện xóa tài khoản: " + id);
        accountGameRepository.deleteAccountGamrById(id);
    }


}
