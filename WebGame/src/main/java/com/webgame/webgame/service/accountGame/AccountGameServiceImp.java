package com.webgame.webgame.service.accountGame;

import com.webgame.webgame.dto.AccountGameDto;
import com.webgame.webgame.dto.gameDto.GameFormDto;
import com.webgame.webgame.dto.gameDto.GameSaleDto;
import com.webgame.webgame.model.AccountGame;
import com.webgame.webgame.model.Game;
import com.webgame.webgame.repository.AccountGameRepository;
import com.webgame.webgame.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class AccountGameServiceImp implements AccountGameService {

    @Autowired
    private AccountGameRepository accountGameRepository;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Page<GameSaleDto> totalAccountGameSold(int page, int size) {
        return accountGameRepository.findTopSellingGames(PageRequest.of(page, size));
    }

    @Override
    public List<AccountGame> listAccountByGameId(Long gameId) {
        return accountGameRepository.accountGameByGameId(gameId);
    }

//    public AccountGame getAccountFromDto(AccountGame accountGame, AccountGameDto accountGameDto, Game game) {
//        accountGame.setUsername(accountGameDto.getUsername());
//        accountGame.setPassword(accountGameDto.getPassword());
//        accountGame.setGame(game); // Liên kết AccountGame với Game đã lấy từ database
//        return accountGame;
//    }
//
//
//    @Override
//    public void saveAccount(AccountGameDto accountGameDto) throws IOException {
//        // Lấy Game từ database dựa trên gameId
//        Optional<Game> optionalGame = gameRepository.findById(accountGameDto.getGameId());
//        if (optionalGame.isEmpty()) {
//            throw new IllegalArgumentException("Game với ID " + accountGameDto.getGameId() + " không tồn tại.");
//        }
//        Game game = optionalGame.get();
//
//        // Tạo AccountGame mới
//        AccountGame accountGame = new AccountGame();
//        accountGame = getAccountFromDto(accountGame, accountGameDto, game);
//
//        // Lưu AccountGame vào database
//        accountGameRepository.save(accountGame);
//    }

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


    @Override
    public void saveAccount(AccountGameDto accountGameDto) {
        if (accountGameDto.getGameId() == null) {
            throw new IllegalArgumentException("Game ID must not be null");
        }

        // Nạp thực thể Game từ repository bằng gameId
        Game game = gameRepository.findById(accountGameDto.getGameId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Game ID: " + accountGameDto.getGameId()));


        // Ánh xạ từ DTO sang thực thể
        AccountGame accountGame = new AccountGame();
        accountGame.setGame(game);
        accountGame.setUsername(accountGameDto.getUsername());
        accountGame.setPassword(accountGameDto.getPassword());

        // Lưu thực thể vào database
        accountGameRepository.save(accountGame);
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
    public void deleteAccount(Long id) {
        this.accountGameRepository.deleteById(id);
    }

//    public Long deleteAccount(Long accountId) {
//        AccountGame accountGame = accountGameRepository.findById(accountId)
//                .orElseThrow(() -> new IllegalArgumentException("Account không tồn tại"));
//        Long gameId = accountGame.getGame().getGameId(); // Lấy gameId để redirect sau khi xóa
//        accountGameRepository.delete(accountGame);
//        return gameId;
//    }

}
