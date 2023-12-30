package team.hiddenblue.wealthtrack.service;

import team.hiddenblue.wealthtrack.dto.Result;

public interface GiteeImageService {
    Result uploadImage(String img, int userId);
}
